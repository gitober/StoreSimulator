package controller;

import javafx.application.Platform;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.IVisualisation;
import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import view.Visualisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The Controller class serves as the intermediary between the view and the model,
 * handling communication and control flow for the simulation.
 */
public class Controller implements IControllerVtoM, IControllerMtoV {
	private MyEngine engine;
	private ISimulatorUI ui;
	private IVisualisation visualisation;
	private ArrivalPattern desiredPattern;
	private boolean isSimulationStarted = false;
	private QueueHistoryDao queueHistoryDao;
	private int w;
	private int h;

	/**
	 * Constructs a Controller object with the specified UI and database table name.
	 *
	 * @param ui        The user interface for the simulation.
	 * @param tableName The name of the table to be used for storing queue history data.
	 */
	public Controller(ISimulatorUI ui, String tableName) {
		this.ui = ui;
		this.w = w; // Add this line
		this.h = h;
		this.visualisation = ui.getVisualisation();
		this.queueHistoryDao = new QueueHistoryDao(tableName);
	}

	/**
	 * Visualises a customer at the specified service point.
	 *
	 * @param servicePoint The service point where the customer should be visualised.
	 */
	@Override
	public void visualiseCustomer(int servicePoint) {
		final int ARRIVAL = 5;
		if (servicePoint == ARRIVAL) {
			if (ui != null) {
				ui.getVisualisation().visualiseCustomer(ARRIVAL);
			} else {
				System.out.println("Error: UI is not initialized.");
			}
		}
	}

	/**
	 * Starts the simulation and sets up the engine with parameters from the user interface.
	 */
	@Override
	public void startSimulation() {
		isSimulationStarted = true;

		int maxCustomers;
		try {
			maxCustomers = ui.getCustomerAmount();
		} catch (NumberFormatException e) {
			maxCustomers = 150;
			System.out.println("Invalid input. Setting max customers to 150 by default.");
		}

		engine = new MyEngine(this, maxCustomers);
		engine.setSimulationTime(ui.getTime());
		engine.setDelay(ui.getDelay());

		if (desiredPattern != null) {
			engine.setArrivalPattern(desiredPattern);
			System.out.println("Arrival pattern set to: " + describePattern(desiredPattern));
		}

		if (visualisation != null) {
			visualisation.clearDisplay();
		} else {
			System.out.println("Error: Visualisation is not initialized.");
		}

		new Thread(engine).start();
	}

	/**
	 * Decreases the simulation speed by increasing the delay.
	 */
	@Override
	public void decreaseSpeed() {
		if (ui != null) {
			long currentDelay = ui.getDelay();
			setSimulationSpeed((long) (currentDelay * 1.10));
		} else {
			System.out.println("Error: UI is not initialized.");
		}
	}

	/**
	 * Increases the simulation speed by reducing the delay.
	 */
	@Override
	public void increaseSpeed() {
		if (engine != null) {
			setSimulationSpeed((long) (engine.getDelay() * 0.90));
		}
	}

	/**
	 * Displays the end time of the simulation on the user interface.
	 *
	 * @param time The time to be displayed as the end time.
	 */
	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));
	}

	/**
	 * Sets the simulation speed by adjusting the delay.
	 *
	 * @param delay The delay to be set for the simulation speed.
	 */
	@Override
	public void setSimulationSpeed(long delay) {
		if (engine != null) {
			engine.setDelay(delay);
			ui.setDelay(delay);
		}
	}

	/**
	 * Sets the arrival pattern for customer simulation.
	 *
	 * @param pattern The desired pattern of customer arrivals.
	 */
	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		desiredPattern = pattern;
		if (engine != null) {
			engine.setArrivalPattern(pattern);
		}
		// Avoid redundant call
		System.out.println("Arrival pattern set to: " + describePattern(pattern));
	}

	/**
	 * Returns the currently desired arrival pattern.
	 *
	 * @return The desired arrival pattern.
	 */
	public ArrivalPattern getDesiredPattern() {
		return desiredPattern;
	}

	/**
	 * Describes the given arrival pattern in a readable format.
	 *
	 * @param pattern The pattern to describe.
	 * @return A string description of the pattern.
	 */
	private String describePattern(ArrivalPattern pattern) {
		switch (pattern) {
			case MORNINGRUSH:
				return "Morning Rush (High frequency of customers)";
			case MIDDAYLULL:
				return "Midday Lull (Low frequency of customers)";
			case AFTERNOONRUSH:
				return "Afternoon Rush (High frequency of customers)";
			default:
				return "Unknown pattern";
		}
	}

	/**
	 * Sets the arrival pattern to "Morning Rush".
	 */
	public void setMorningRush() {
		setArrivalPattern(ArrivalPattern.MORNINGRUSH);
	}

	/**
	 * Sets the arrival pattern to "Midday Lull".
	 */
	public void setMiddayLull() {
		setArrivalPattern(ArrivalPattern.MIDDAYLULL);
	}

	/**
	 * Sets the arrival pattern to "Afternoon Rush".
	 */
	public void setAfternoonRush() {
		setArrivalPattern(ArrivalPattern.AFTERNOONRUSH);
	}

	/**
	 * Returns whether the simulation has started.
	 *
	 * @return True if the simulation has started, false otherwise.
	 */
	public boolean getIsSimulationStarted() {
		return isSimulationStarted;
	}

	/**
	 * Sets the engine to be used by the controller.
	 *
	 * @param engine The engine to be set.
	 */
	public void setEngine(MyEngine engine) {
		this.engine = engine;
	}

	/**
	 * Records the history of a customer's queue experience in the database.
	 *
	 * @param customerId       The ID of the customer.
	 * @param servicePointName The name of the service point.
	 * @param arrivalTime      The arrival time at the service point.
	 * @param departureTime    The departure time from the service point.
	 * @param queueTime        The time spent in the queue.
	 */
	private void recordQueueHistory(int customerId, String servicePointName, LocalDateTime arrivalTime, LocalDateTime departureTime, double queueTime) {
		QueueHistory history = new QueueHistory(0, customerId, servicePointName, arrivalTime, departureTime, queueTime);
		queueHistoryDao.persist(history);
	}

	/**
	 * Returns the name of the service point given its identifier.
	 *
	 * @param servicePoint The identifier of the service point.
	 * @return The name of the service point.
	 */
	private String getServicePointName(int servicePoint) {
		return "Service Point " + servicePoint;
	}
}
