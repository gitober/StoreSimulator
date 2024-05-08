/**
 * Package containing the user interface components and visualization tools.
 */
package view;

import simu.model.ArrivalPattern;

/**
 * Defines the contract for the simulator user interface (UI).
 * Specifies the methods that must be implemented to manage the simulation parameters and results.
 */
public interface ISimulatorUI {

	/**
	 * Returns the current simulation time.
	 *
	 * @return The current simulation time.
	 */
	double getTime();

	/**
	 * Returns the delay between events for the simulation.
	 * This value controls the speed of the simulation.
	 *
	 * @return The delay in milliseconds.
	 */
	long getDelay();

	/**
	 * Returns the total number of customers for the simulation.
	 *
	 * @return The number of customers.
	 */
	int getCustomerAmount();

	/**
	 * Sets the ending time of the simulation.
	 *
	 * @param time The ending time to be displayed in the UI.
	 */
	void setEndingTime(double time);

	/**
	 * Returns the visualization component associated with the simulation.
	 *
	 * @return The visualization component.
	 */
	IVisualisation getVisualisation();

	/**
	 * Appends a results string to the UI output.
	 * Typically used to log the results or messages from the simulation.
	 *
	 * @param text The results text to be appended.
	 */
	void appendResults(String text);

	/**
	 * Sets the delay between events in the simulation.
	 *
	 * @param delay The delay in milliseconds.
	 */
	void setDelay(long delay);

	/**
	 * Sets the arrival pattern of customers in the simulation.
	 *
	 * @param arrivalPattern The desired arrival pattern.
	 */
	void setArrivalPattern(ArrivalPattern arrivalPattern);
}
