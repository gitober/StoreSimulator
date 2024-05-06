package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Event;
import simu.framework.EventList;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a service point in the simulation.
 * It provides methods to add and remove customers from the queue, begin service, check if the service point is on queue or reserved, get the queue length, and get the queue status.
 */
public class ServicePoint {

	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private Queue<Customer> queue;
	private boolean reserved;
	private int queueLength; // Variable to track queue length

	/**
	 * Constructs a new ServicePoint with the given generator, event list, and event type.
	 * @param generator The generator for the service point.
	 * @param eventList The event list for the service point.
	 * @param eventType The event type for the service point.
	 */
	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType eventType) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.queue = new LinkedList<>();
		this.queueLength = 0; // Initialize queue length to 0
	}

	/**
	 * Adds a customer to the queue of the service point.
	 * @param customer The customer to add.
	 */
	public void addQueue(Customer customer) {
		queue.add(customer);
		queueLength++; // Increment queue length when customer joins the queue
		if (!reserved) {
			beginService();
		}
	}

	/**
	 * Removes a customer from the queue of the service point.
	 * @return The customer that was removed.
	 */
	public Customer removeQueue() {
		reserved = false;
		Customer customer = queue.poll();
		if (customer != null) {
			queueLength--; // Decrement queue length when customer leaves the queue
		}
		return customer;
	}

	/**
	 * Begins the service at the service point.
	 */
	public void beginService() {
		if (!queue.isEmpty()) {
			reserved = true;
			double delay = generator.sample();
			eventList.add(new Event(eventType, delay));
			// Log queue status when service begins
			System.out.println(getQueueStatus());
		}
	}

	/**
	 * Checks if the service point is on queue.
	 * @return True if the service point is on queue, false otherwise.
	 */
	public boolean isOnQueue() {
		return !queue.isEmpty();
	}

	/**
	 * Checks if the service point is reserved.
	 * @return True if the service point is reserved, false otherwise.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * Returns the length of the queue at the service point.
	 * @return The length of the queue.
	 */
	public int getQueueLength() {
		return queueLength;
	}

	/**
	 * Returns the status of the queue at the service point.
	 * @return The status of the queue.
	 */
	public String getQueueStatus() {
		if (getQueueLength() > 0) {
			return "There are " + getQueueLength() + " customers in the queue.";
		} else {
			return "There are no customers in the queue.";
		}
	}
}