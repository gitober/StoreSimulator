package simu.model;

import simu.framework.Clock;
import simu.framework.Trace;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class represents a customer in the simulation.
 * It provides methods to get and set the arrival and removal times, get the next service point, get and add service times, get the queue status, queueing, queue time, record summary, and get the summary.
 */
public class Customer {
	private double arrivalTime;
	private double removalTime;
	private int id;
	private List<Integer> servicePoints;
	private Map<Integer, Double> serviceTimes;
	private static int nextId = 1;
	private static long sum = 0;
	private static DecimalFormat df = new DecimalFormat("0.00");
	private StringBuilder summary = new StringBuilder();

	/**
	 * Constructs a new Customer and initializes the arrival time, id, service points, and service times.
	 */
	public Customer() {
		id = nextId++;
		arrivalTime = Clock.getInstance().getTime();

		// Generate a list of service points in random order except for the last point
		List<Integer> points = Arrays.asList(1, 2, 3);
		Collections.shuffle(points);
		servicePoints = new ArrayList<>(points);
		servicePoints.add(4); // Cashier is always the last stop

		serviceTimes = new LinkedHashMap<>();
		Trace.out(Trace.Level.INFO, "Customer #" + id + " has arrived at the store at " + formatTime(arrivalTime));
	}

	/**
	 * Returns the queue status of the customer.
	 * @param queueLength The length of the queue.
	 * @return The queue status of the customer.
	 */
	public String getQueueStatus(int queueLength) {
		if (queueLength == 0) {
			return "Customer #" + id + " has no queue";
		} else {
			return "Customer #" + id + " is in queue";
		}
	}

	/**
	 * Returns the next service point for the customer.
	 * @return The next service point for the customer.
	 */
	public int getNextServicePoint() {
		return servicePoints.isEmpty() ? -1 : servicePoints.remove(0);
	}

	/**
	 * Returns the service time at a service point for the customer.
	 * @param servicePoint The service point.
	 * @return The service time at the service point for the customer.
	 */
	public double getServiceTime(int servicePoint) {
		return serviceTimes.getOrDefault(servicePoint, 0.0);
	}

	/**
	 * Adds a service time at a service point for the customer.
	 * @param servicePoint The service point.
	 * @param timeSpent The time spent at the service point.
	 */
	public void addServiceTime(int servicePoint, double timeSpent) {
		serviceTimes.put(servicePoint, timeSpent);
	}

	/**
	 * Returns the removal time of the customer.
	 * @return The removal time of the customer.
	 */
	public double getRemovalTime() {
		return removalTime;
	}

	/**
	 * Sets the removal time of the customer.
	 * @param removalTime The removal time to set.
	 */
	public void setRemovalTime(double removalTime) {
		this.removalTime = removalTime;
	}

	/**
	 * Returns the arrival time of the customer.
	 * @return The arrival time of the customer.
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Returns the name of the service point.
	 * @param servicePoint The service point.
	 * @return The name of the service point.
	 */
	private String getServicePointName(int servicePoint) {
		switch (servicePoint) {
			case 1:
				return "Customer Service Desk";
			case 2:
				return "Deli Counter";
			case 3:
				return "Vegetable Section";
			case 4:
				return "Cashier";
			default:
				return "Unknown Service Point";
		}
	}

	/**
	 * Formats the time in minutes to a string representation.
	 * @param timeInMinutes The time in minutes.
	 * @return The formatted time.
	 */
	private String formatTime(double timeInMinutes) {
		LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());
		LocalTime updatedTime = currentTime.plusMinutes((long) timeInMinutes);
		return updatedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	/**
	 * Logs the arrival of the customer at a service point and the queue status.
	 * @param servicePoint The service point.
	 * @param queueLength The length of the queue.
	 */
	public void queueing(int servicePoint, int queueLength) {
		Trace.out(Trace.Level.INFO, "Customer #" + id + " has arrived at " + getServicePointName(servicePoint));
		// Call getQueueStatus method here to determine the queue status
		String queueStatus = getQueueStatus(queueLength);
		Trace.out(Trace.Level.INFO, queueStatus);
	}

	/**
	 * Returns the queue time at a service point for the customer.
	 * @param servicePoint The service point.
	 * @param timeInQueue The time in queue at the service point.
	 * @return The queue time at the service point for the customer.
	 */
	public double queueTime(int servicePoint, double timeInQueue) {
		double nonNegativeTime = Math.max(0, timeInQueue);
		Trace.out(Trace.Level.INFO, "Customer #" + id + " spent " + df.format(nonNegativeTime) + " minutes in the queue at " + getServicePointName(servicePoint));
		Trace.out(Trace.Level.INFO, "Customer #" + id + " has left " + getServicePointName(servicePoint));
		return nonNegativeTime;
	}

	/**
	 * Records the summary of the customer's journey through the store.
	 */
	public void recordSummary() {
		summary.append("\nSummary for Customer #").append(id).append(":\n\n");
		double previousTime = arrivalTime;
		for (Map.Entry<Integer, Double> entry : serviceTimes.entrySet()) {
			int servicePoint = entry.getKey();
			double timeSpent = Math.max(0, entry.getValue());
			double arrival = previousTime;
			double removed = arrival + timeSpent;
			summary.append("Customer #").append(id)
					.append(" arrived at ").append(getServicePointName(servicePoint))
					.append(" at ").append(formatTime(arrival))
					.append(", left at ").append(formatTime(removed))
					.append(", and spent ").append(df.format(timeSpent)).append(" minutes on the queue.\n\n");
			previousTime = removed;
		}

		removalTime = previousTime;
		summary.append("Customer #").append(id)
				.append(" has exited the store at ").append(formatTime(removalTime)).append("\n\n");
		double totalQueueTime = removalTime - arrivalTime;
		summary.append("Customer #").append(id)
				.append(" spent a total of ").append(df.format(totalQueueTime)).append(" minutes in the queues.\n\n");
		sum += totalQueueTime;
		double averageQueueTime = sum / (double) nextId;
		summary.append("The average time customers have spent in the queues so far is: ")
				.append(df.format(averageQueueTime)).append(" minutes\n");
	}

	/**
	 * Returns the summary of the customer's journey through the store.
	 * @return The summary of the customer's journey through the store.
	 */
	public String getSummary() {
		return summary.toString();
	}

	/**
	 * Sets the arrival time of the customer.
	 * @param arrivalTime The arrival time to set.
	 */
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}