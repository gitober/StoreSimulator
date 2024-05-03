package controller;

/**
 * Interface defining methods that the UI uses to control the simulation engine.
 */
public interface IControllerVtoM {
	/**
	 * Initiates the simulation process.
	 */
	void startSimulation();

	/**
	 * Increases the simulation speed by decreasing the delay between events.
	 */
	void increaseSpeed();

	/**
	 * Decreases the simulation speed by increasing the delay between events.
	 */
	void decreaseSpeed();
}
