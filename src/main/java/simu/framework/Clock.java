package simu.framework;

/**
 * The Clock class represents the simulation clock.
 * It keeps track of the current simulation time.
 */
public class Clock {
	private double time;
	private static Clock instance;

	// Private constructor to prevent instantiation from outside the class
	private Clock() {
		time = 0;
	}

	/**
	 * Returns the singleton instance of the Clock.
	 *
	 * @return The singleton instance of the Clock.
	 */
	public static Clock getInstance() {
		if (instance == null) {
			instance = new Clock();
		}
		return instance;
	}

	/**
	 * Sets the current simulation time.
	 *
	 * @param time The time to be set.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Retrieves the current simulation time.
	 *
	 * @return The current simulation time.
	 */
	public double getTime() {
		return time;
	}
}
