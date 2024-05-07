/**
 * The IControllerVtoM interface represents methods for communication from the view to the controller.
 */
package controller;

import simu.model.ArrivalPattern;

public interface IControllerVtoM {
	/**
	 * Starts the simulation.
	 */
	void startSimulation();

	/**
	 * Increases the simulation speed.
	 */
	void increaseSpeed();

	/**
	 * Decreases the simulation speed.
	 */
	void decreaseSpeed();

	/**
	 * Sets the arrival pattern for the simulation.
	 *
	 * @param pattern The arrival pattern to set.
	 */
	void setArrivalPattern(ArrivalPattern pattern);

	/**
	 * Sets the simulation speed.
	 *
	 * @param delay The delay to set for the simulation.
	 */
	void setSimulationSpeed(long delay);
}
