/**
 * The Controller class manages the interaction between the user interface and the simulation engine.
 */
package controller;

import javafx.application.Platform;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.IVisualisation;
import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller implements IControllerVtoM, IControllerMtoV {
	private MyEngine engine;
	private ISimulatorUI ui;
	private IVisualisation visualisation;
	private ArrivalPattern desiredPattern;
	private boolean isSimulationStarted = false;
	private QueueHistoryDao queueHistoryDao;

	/**
	 * Constructs a Controller object.
	 *
	 * @param ui         The user interface for the simulator.
	 * @param tableName  The name of the table for storing queue history.
	 */
	public Controller(ISimulatorUI ui, String tableName) {
		this.ui = ui;
		this.visualisation = ui.getVisualisation();
		this.queueHistoryDao = new QueueHistoryDao(tableName);
	}

	/**
	 * Visualizes the arrival of a customer at a service point.
	 *
	 * @param servicePoint The service point where the customer arrives.
	 */
	@Override
	public void visualiseCustomer(int servicePoint) {
		if (visualisation != null) {
			Platform.runLater(() -> visualisation.newCustomer(servicePoint));
		} else {
			System.out.println("Error: Visualisation is not initialized.");
		}
	}

	/**
	 * Starts the simulation.
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
	 * Decreases the simulation speed.
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
	 * Increases the simulation speed.
	 */
	@Override
	public void increaseSpeed() {
		if (engine != null) {
			setSimulationSpeed((long) (engine.getDelay() * 0.90));
		}
	}

	/**
	 * Shows the end time of the simulation.
	 *
	 * @param time The end time of the simulation.
	 */
	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));
	}

	/**
	 * Sets the simulation speed.
	 *
	 * @param delay The delay to set for the simulation.
	 */
	@Override
	public void setSimulationSpeed(long delay) {
		if (engine != null) {
			engine.setDelay(delay);
			ui.setDelay(delay);
		}
	}

	/**
	 * Sets the arrival pattern for the simulation.
	 *
	 * @param pattern The arrival pattern to set.
	 */
	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		desiredPattern = pattern;
		if (engine != null) {
			engine.setArrivalPattern(pattern);
		}
		if (ui != null) {
			ui.setArrivalPattern(pattern);
		}
		System.out.println("Arrival pattern set to: " + describePattern(pattern));
	}

	/**
	 * Describes an arrival pattern.
	 *
	 * @param pattern The arrival pattern to describe.
	 * @return A description of the arrival pattern.
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
	 * Sets the arrival pattern to Morning Rush.
	 */
	public void setMorningRush() {
		setArrivalPattern(ArrivalPattern.MORNINGRUSH);
	}

	/**
	 * Sets the arrival pattern to Midday Lull.
	 */
	public void setMiddayLull() {
		setArrivalPattern(ArrivalPattern.MIDDAYLULL);
	}

	/**
	 * Sets the arrival pattern to Afternoon Rush.
	 */
	public void setAfternoonRush() {
		setArrivalPattern(ArrivalPattern.AFTERNOONRUSH);
	}

	/**
	 * Checks if the simulation is started.
	 *
	 * @return True if the simulation is started, otherwise false.
	 */
	public boolean getIsSimulationStarted() {
		return isSimulationStarted;
	}

	/**
	 * Sets the engine for the simulation.
	 *
	 * @param engine The engine to set.
	 */
	public void setEngine(MyEngine engine) {
		this.engine = engine;
	}

	/**
	 * Records the history of a customer queue.
	 *
	 * @param customerId    The ID of the customer.
	 * @param servicePointName The name of the service point.
	 * @param arrivalTime   The time of arrival.
	 * @param departureTime The time of departure.
	 * @param queueTime     The time spent in the queue.
	 */
	private void recordQueueHistory(int customerId, String servicePointName, LocalDateTime arrivalTime, LocalDateTime departureTime, double queueTime) {
		QueueHistory history = new QueueHistory(0, customerId, servicePointName, arrivalTime, departureTime, queueTime);
		queueHistoryDao.persist(history);
	}

	/**
	 * Gets the name of a service point based on its number.
	 *
	 * @param servicePoint The number of the service point.
	 * @return The name of the service point.
	 */
	private String getServicePointName(int servicePoint) {
		return "Service Point " + servicePoint;
	}

	private void recordServicePoint(String servicePointName, Connection connection) {
		// Insert the service point name into the service_points table
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO service_points (service_point_name) VALUES (?)");
			statement.setString(1, servicePointName);
			statement.executeUpdate();
		} catch (SQLException e) {
			// Handle any SQL exceptions
			e.printStackTrace();
		}
	}
}