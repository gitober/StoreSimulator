package controller;

import javafx.application.Platform;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.IVisualisation;

/**
 * This class represents the controller in the MVC pattern.
 * It communicates between the model (MyEngine) and the view (ISimulatorUI and IVisualisation).
 * It implements two interfaces: IControllerVtoM (View to Model) and IControllerMtoV (Model to View).
 */
public class Controller implements IControllerVtoM, IControllerMtoV {
	private MyEngine engine;
	private ISimulatorUI ui;
	private IVisualisation visualisation;
	private ArrivalPattern desiredPattern;
	private boolean isSimulationStarted = false;

	/**
	 * Constructor for the Controller class.
	 * @param ui The user interface for the simulation.
	 */
	public Controller(ISimulatorUI ui) {
		this.ui = ui;
		this.visualisation = ui.getVisualisation();
	}

	/**
	 * Visualizes a new customer at a service point.
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
	 * Decreases the speed of the simulation.
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
	 * Increases the speed of the simulation.
	 */
	@Override
	public void increaseSpeed() {
		if (engine != null) {
			setSimulationSpeed((long) (engine.getDelay() * 0.90));
		}
	}

	/**
	 * Shows the end time of the simulation.
	 * @param time The end time of the simulation.
	 */
	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));
	}

	/**
	 * Sets the speed of the simulation.
	 * @param delay The delay time between each step of the simulation.
	 */
	@Override
	public void setSimulationSpeed(long delay) {
		if (engine != null) {
			engine.setDelay(delay);
			ui.setDelay(delay);
		}
	}

	/**
	 * Sets the arrival pattern of the customers.
	 * @param pattern The arrival pattern of the customers.
	 */
	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		desiredPattern = pattern;
		if (engine != null) {
			engine.setArrivalPattern(pattern);
		}
		// Optionally update the UI as well
		if (ui != null) {
			ui.setArrivalPattern(pattern);
		}
		System.out.println("Arrival pattern set to: " + describePattern(pattern));
	}

	/**
	 * Describes the arrival pattern.
	 * @param pattern The arrival pattern.
	 * @return A string description of the arrival pattern.
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
	 * Checks if the simulation has started.
	 * @return True if the simulation has started, false otherwise.
	 */
	public boolean getIsSimulationStarted() {
		return isSimulationStarted;
	}

	/**
	 * Sets the engine for the simulation.
	 * @param engine The engine for the simulation.
	 */
	public void setEngine(MyEngine engine) {
		this.engine = engine;
	}
}
