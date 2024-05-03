package controller;

import javafx.application.Platform;
import simu.framework.IEngine;
import simu.model.MyEngine;
import view.ISimulatorUI;

/**
 * Controller class manages the interaction between the UI and the simulation engine.
 * It controls the simulation flow and updates the UI based on simulation events.
 */
public class Controller implements IControllerVtoM, IControllerMtoV {
	private IEngine engine;
	private ISimulatorUI ui;

	/**
	 * Constructs a Controller with a reference to the simulator UI.
	 * @param ui the simulator UI interface
	 */
	public Controller(ISimulatorUI ui) {
		this.ui = ui;
	}

	/**
	 * Starts the simulation by creating a new engine instance and initializing it with UI parameters.
	 */
	@Override
	public void startSimulation() {
		engine = new MyEngine(this);  // Create a new Engine thread for every simulation
		engine.setSimulationTime(ui.getTime());  // Set simulation end time from UI
		engine.setDelay(ui.getDelay());          // Set initial delay from UI
		ui.getVisualisation().clearDisplay();    // Clear visual display before starting
		((Thread) engine).start();               // Start the engine thread
	}

	/**
	 * Decreases the simulation speed by increasing the delay between events.
	 */
	@Override
	public void decreaseSpeed() {
		engine.setDelay((long) (engine.getDelay() * 1.10));  // Increase delay by 10%
	}

	/**
	 * Increases the simulation speed by decreasing the delay between events.
	 */
	@Override
	public void increaseSpeed() {
		engine.setDelay((long) (engine.getDelay() * 0.9));   // Decrease delay by 10%
	}

	/**
	 * Passes the simulation end time to the UI to display.
	 * @param time the end time of the simulation
	 */
	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));  // Update UI on the JavaFX thread
	}

	/**
	 * Triggers the UI to visualize a new customer.
	 */
	@Override
	public void visualiseCustomer() {
		Platform.runLater(() -> ui.getVisualisation().newCustomer());  // Add new customer on the JavaFX thread
	}
}
