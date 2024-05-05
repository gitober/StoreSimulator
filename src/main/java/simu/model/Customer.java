package simu.model;

import simu.framework.Clock;
import simu.framework.Trace;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


	public String getQueueStatus(int queueLength) {
		if (queueLength == 0) {
			return "Customer #" + id + " has no queue";
		} else {
			return "Customer #" + id + " is in queue";
		}
	}

	public int getNextServicePoint() {
		return servicePoints.isEmpty() ? -1 : servicePoints.remove(0);
	}

	public double getServiceTime(int servicePoint) {
		return serviceTimes.getOrDefault(servicePoint, 0.0);
	}

	public void addServiceTime(int servicePoint, double timeSpent) {
		serviceTimes.put(servicePoint, timeSpent);
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

	private String formatTime(double timeInMinutes) {
		LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());
		LocalTime updatedTime = currentTime.plusMinutes((long) timeInMinutes);
		return updatedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	public void queueing(int servicePoint, int queueLength) {
		Trace.out(Trace.Level.INFO, "Customer #" + id + " has arrived at " + getServicePointName(servicePoint));
		// Call getQueueStatus method here to determine the queue status
		String queueStatus = getQueueStatus(queueLength);
		Trace.out(Trace.Level.INFO, queueStatus);
	}

	public double queueTime(int servicePoint, double timeInQueue) {
		double nonNegativeTime = Math.max(0, timeInQueue);
		Trace.out(Trace.Level.INFO, "Customer #" + id + " spent " + df.format(nonNegativeTime) + " minutes in the queue at " + getServicePointName(servicePoint));
		Trace.out(Trace.Level.INFO, "Customer #" + id + " has left " + getServicePointName(servicePoint));
		return nonNegativeTime;
	}

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


	public String getSummary() {
		return summary.toString();
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}