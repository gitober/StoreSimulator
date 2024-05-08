package simu.framework;

import simu.model.ArrivalPattern;

/**
 * The IEngine interface defines the contract for a simulation engine.
 * It extends {@link Runnable} to allow the engine to be run in a separate thread.
 */
public interface IEngine extends Runnable {
	/**
	 * Sets the total simulation time.
	 *
	 * @param time The time to set for the total simulation duration.
	 */
	void setSimulationTime(double time);

	/**
	 * Sets the delay between simulation events.
	 *
	 * @param delay The delay to set in milliseconds.
	 */
	void setDelay(long delay);

	/**
	 * Returns the current delay between simulation events.
	 *
	 * @return The current delay in milliseconds.
	 */
	long getDelay();

	/**
	 * Sets the arrival pattern for customer simulation.
	 *
	 * @param pattern The desired pattern of customer arrivals.
	 */
	void setArrivalPattern(ArrivalPattern pattern);
}
