package view;

import simu.model.ArrivalPattern;

/**
 * This interface represents the user interface for the simulator.
 * It provides methods to get and set the time, delay, customer amount, ending time, visualisation, results, and arrival pattern.
 */
public interface ISimulatorUI {

	/**
	 * Returns the current time of the simulator.
	 * @return The current time of the simulator.
	 */
	double getTime();

	/**
	 * Returns the delay of the simulator.
	 * @return The delay of the simulator.
	 */
	long getDelay();

	/**
	 * Returns the amount of customers in the simulator.
	 * @return The amount of customers in the simulator.
	 */
	int getCustomerAmount();

	/**
	 * Sets the ending time of the simulator.
	 * @param time The ending time to set.
	 */
	void setEndingTime(double time);

	/**
	 * Returns the visualisation of the simulator.
	 * @return The visualisation of the simulator.
	 */
	IVisualisation getVisualisation();

	/**
	 * Appends the given text to the results of the simulator.
	 * @param text The text to append to the results.
	 */
	void appendResults(String text);

	/**
	 * Sets the delay of the simulator.
	 * @param delay The delay to set.
	 */
	void setDelay(long delay);

	/**
	 * Sets the arrival pattern of the simulator.
	 * @param arrivalPattern The arrival pattern to set.
	 */
	void setArrivalPattern(ArrivalPattern arrivalPattern);
}