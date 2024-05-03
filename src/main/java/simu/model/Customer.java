package simu.model;

import simu.framework.Clock;
import simu.framework.Trace;

public class Customer {
	private final double arrivalTime; // Make arrivalTime final if it should not change after construction
	private double removalTime;
	private final int id; // Make ID final since it should not change
	private static int nextId = 1; // Rename to nextId to clarify purpose
	private static long sumDurations = 0; // Rename for clarity
	private static final Object lock = new Object(); // Lock for thread-safe increments

	public Customer() {
		synchronized(lock) { // Synchronize to ensure thread safety
			id = nextId++;
		}
		arrivalTime = Clock.getInstance().getTime();
		Trace.out(Trace.Level.INFO, "New customer #" + id + " arrived at " + arrivalTime);
	}

	public double getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(double removalTime) {
		this.removalTime = removalTime;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public int getId() {
		return id;
	}

	public void reportResults() {
		Trace.out(Trace.Level.INFO, "\nCustomer " + id + " ready! ");
		Trace.out(Trace.Level.INFO, "Customer " + id + " arrived: " + arrivalTime);
		Trace.out(Trace.Level.INFO, "Customer " + id + " removed: " + removalTime);
		Trace.out(Trace.Level.INFO, "Customer " + id + " stayed: " + (removalTime - arrivalTime));

		synchronized(lock) {
			sumDurations += (removalTime - arrivalTime);
		}
		double mean = sumDurations / id;
		Trace.out(Trace.Level.INFO, "Current mean of the customer service times: " + mean);
	}
}
