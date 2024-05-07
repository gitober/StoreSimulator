package simu.framework;

import simu.model.ArrivalPattern;

/**
 * The IEngine interface represents an engine for simulation.
 * It defines methods to set simulation time, delay, and arrival pattern.
 */
public interface IEngine extends Runnable {

	/**
	 * Sets the simulation time.
	 *
	 * @param time The simulation time to be set.
	 */
	void setSimulationTime(double time);

	/**
	 * Sets the delay for the engine.
	 *
	 * @param delay The delay to be set.
	 */
	void setDelay(long delay);

	/**
	 * Retrieves the delay for the engine.
	 *
	 * @return The delay for the engine.
	 */
	long getDelay();

	/**
	 * Sets the arrival pattern for the engine.
	 *
	 * @param pattern The arrival pattern to be set.
	 */
	void setArrivalPattern(ArrivalPattern pattern);
}
