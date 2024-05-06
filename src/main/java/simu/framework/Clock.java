package simu.framework;

/**
 * This class represents a clock in the simulation.
 * It is a singleton class, meaning there can only be one instance of this class in the application.
 * It provides methods to get the instance of the clock, set the time, and get the time.
 */
public class Clock {
	private double time;
	private static Clock instance;

	/**
	 * Private constructor to prevent instantiation from outside the class.
	 * Initializes the time to 0.
	 */
	private Clock(){
		time = 0;
	}

	/**
	 * Returns the instance of the Clock.
	 * If the instance does not exist, it is created.
	 * @return The instance of the Clock.
	 */
	public static Clock getInstance(){
		if (instance == null){
			instance = new Clock();
		}
		return instance;
	}

	/**
	 * Sets the time of the Clock.
	 * @param time The time to set.
	 */
	public void setTime(double time){
		this.time = time;
	}

	/**
	 * Returns the time of the Clock.
	 * @return The time of the Clock.
	 */
	public double getTime(){
		return time;
	}
}