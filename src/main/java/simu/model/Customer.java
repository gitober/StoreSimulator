package simu.model;

import simu.framework.Clock;
import simu.framework.Trace;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The Customer class represents a customer in the simulation who visits various service points.
 * It keeps track of the customer's service points, times, and generates a summary of their experience.
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
	private String firstName, lastName;

	/**
	 * Constructs a Customer object with a unique identifier and initializes their service points.
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
		Trace.out(Trace.Level.INFO, "• The simulation for Customer #" + id + " has started at the store at " + formatTime(arrivalTime));
	}

	/**
	 * Returns the customer's unique identifier.
	 *
	 * @return The customer's unique identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the customer's unique identifier.
	 *
	 * @param id The identifier to be set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the identifier of the customer's current service point.
	 *
	 * @return The identifier of the current service point, or -1 if no points are left.
	 */
	public int getCurrentServicePoint() {
		return servicePoints.isEmpty() ? -1 : servicePoints.get(0);
	}

	/**
	 * Returns the customer's queue status based on the given queue length.
	 *
	 * @param queueLength The length of the queue.
	 * @return A string representing the customer's queue status.
	 */
	public String getQueueStatus(int queueLength) {
		if (queueLength == 0) {
			return "• Customer #" + id + " has no queue";
		} else {
			return "• Customer #" + id + " is in queue";
		}
	}

	/**
	 * Returns the identifier of the next service point and removes it from the list.
	 *
	 * @return The identifier of the next service point, or -1 if no points are left.
	 */
	public int getNextServicePoint() {
		return servicePoints.isEmpty() ? -1 : servicePoints.remove(0);
	}

	/**
	 * Returns the time spent at a specific service point.
	 *
	 * @param servicePoint The identifier of the service point.
	 * @return The time spent at the service point, or 0 if no time is recorded.
	 */
	public double getServiceTime(int servicePoint) {
		return serviceTimes.getOrDefault(servicePoint, 0.0);
	}

	/**
	 * Adds the time spent at a specific service point to the record.
	 *
	 * @param servicePoint The identifier of the service point.
	 * @param timeSpent    The time spent at the service point.
	 */
	public void addServiceTime(int servicePoint, double timeSpent) {
		serviceTimes.put(servicePoint, timeSpent);
	}

	/**
	 * Returns the time at which the customer left the store.
	 *
	 * @return The customer's removal time.
	 */
	public double getRemovalTime() {
		return removalTime;
	}

	/**
	 * Sets the time at which the customer left the store.
	 *
	 * @param removalTime The removal time to be set.
	 */
	public void setRemovalTime(double removalTime) {
		this.removalTime = removalTime;
	}

	/**
	 * Returns the time at which the customer entered the store.
	 *
	 * @return The customer's arrival time.
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Sets the time at which the customer entered the store.
	 *
	 * @param arrivalTime The arrival time to be set.
	 */
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Returns the name of the service point given its identifier.
	 *
	 * @param servicePoint The identifier of the service point.
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
	 * Formats the given time in minutes to a readable time string.
	 *
	 * @param timeInMinutes The time in minutes to be formatted.
	 * @return A string representing the formatted time.
	 */
	private String formatTime(double timeInMinutes) {
		LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());
		LocalTime updatedTime = currentTime.plusMinutes((long) timeInMinutes);
		return updatedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	/**
	 * Logs the customer's arrival at a service point and provides their queue status.
	 *
	 * @param servicePoint The identifier of the service point.
	 * @param queueLength  The length of the queue at the service point.
	 */
	public void queueing(int servicePoint, int queueLength) {
		Trace.out(Trace.Level.INFO, "• Customer #" + id + " has arrived at " + getServicePointName(servicePoint));
		// Call getQueueStatus method here to determine the queue status
		String queueStatus = getQueueStatus(queueLength);
		Trace.out(Trace.Level.INFO, queueStatus);
	}

	/**
	 * Logs the time the customer spent in the queue at a specific service point and their departure.
	 *
	 * @param servicePoint  The identifier of the service point.
	 * @param timeInQueue   The time the customer spent in the queue.
	 * @return The non-negative time spent in the queue.
	 */
	public double queueTime(int servicePoint, double timeInQueue) {
		double nonNegativeTime = Math.max(0, timeInQueue);
		if (nonNegativeTime > 0) {
			Trace.out(Trace.Level.INFO, "• Customer #" + id + " spent " + df.format(nonNegativeTime) + " minutes in the queue at " + getServicePointName(servicePoint));
		} else {
			Trace.out(Trace.Level.INFO, "• Customer #" + id + " spent no time in the queue at " + getServicePointName(servicePoint));
		}
		Trace.out(Trace.Level.INFO, "• Customer #" + id + " has left " + getServicePointName(servicePoint));
		return nonNegativeTime;
	}

	/**
	 * Records a summary of the customer's visit to the store.
	 */
	public void recordSummary() {
		summary.append("\n• Summary for Customer #").append(id).append(":\n\n");
		double previousTime = arrivalTime;
		for (Map.Entry<Integer, Double> entry : serviceTimes.entrySet()) {
			int servicePoint = entry.getKey();
			double timeSpent = Math.max(0, entry.getValue());
			double arrival = previousTime;
			double removed = arrival + timeSpent;
			summary.append("• Customer #").append(id)
					.append(" arrived at ").append(getServicePointName(servicePoint))
					.append(" at ").append(formatTime(arrival))
					.append(", left at ").append(formatTime(removed))
					.append(", and spent ").append(df.format(timeSpent)).append(" minutes on the queue.\n\n");
			previousTime = removed;
		}

		removalTime = previousTime;
		summary.append("• Customer #").append(id)
				.append(" has exited the store at ").append(formatTime(removalTime)).append("\n\n");
		double totalQueueTime = removalTime - arrivalTime;
		summary.append("• Customer #").append(id)
				.append(" spent a total of ").append(df.format(totalQueueTime)).append(" minutes in the queues. The simulation has ended.\n\n");
		sum += totalQueueTime;
		double averageQueueTime = sum / (double) nextId;
		summary.append("• The average time customers have spent in the queues so far is: ")
				.append(df.format(averageQueueTime)).append(" minutes\n");
	}

	/**
	 * Returns a summary of the customer's visit to the store.
	 *
	 * @return A string representing the customer's visit summary.
	 */
	public String getSummary() {
		return summary.toString();
	}
}
