package simu.framework;

public class Clock {
	private double time;
	private static volatile Clock instance;  // Volatile keyword ensures that changes to `instance` are visible to all threads.

	private Clock() {
		time = 0;
	}

	// Double-checked locking for thread-safe singleton instance initialization.
	public static Clock getInstance() {
		if (instance == null) {
			synchronized (Clock.class) {
				if (instance == null) {
					instance = new Clock();
				}
			}
		}
		return instance;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	// Method to reset the clock, useful for restarting the simulation
	public void reset() {
		time = 0;
	}
}
