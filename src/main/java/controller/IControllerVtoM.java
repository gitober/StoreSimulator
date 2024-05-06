package controller;

import simu.model.ArrivalPattern;

/**
 * This interface represents the view to model controller in the MVC pattern.
 * It provides methods to start the simulation, increase and decrease its speed,
 * set the arrival pattern of customers, and set the simulation speed.
 */
public interface IControllerVtoM {

	/**
	 * Starts the simulation.
	 */
	void startSimulation();

	/**
	 * Increases the speed of the simulation.
	 */
	void increaseSpeed();

	/**
	 * Decreases the speed of the simulation.
	 */
	void decreaseSpeed();

	/**
	 * Sets the arrival pattern of the customers.
	 * @param pattern The arrival pattern of the customers.
	 */
	void setArrivalPattern(ArrivalPattern pattern);

	/**
	 * Sets the speed of the simulation.
	 * @param delay The delay time between each step of the simulation.
	 */
	void setSimulationSpeed(long delay);
}