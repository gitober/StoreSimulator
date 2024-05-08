package simu.framework;

/**
 * The Clock class serves as a singleton clock that keeps track of the current simulation time.
 */
public class Clock {
	private double time;
	private static Clock instance;

	/**
	 * Private constructor to prevent instantiation from other classes.
	 * Initializes the time to zero.
	 */
	private Clock() {
		time = 0;
	}

	/**
	 * Returns the singleton instance of the Clock.
	 * If the instance is null, it initializes a new Clock object.
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
	 * @param time The time to be set as the current simulation time.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Returns the current simulation time.
	 *
	 * @return The current simulation time.
	 */
	public double getTime() {
		return time;
	}
}
