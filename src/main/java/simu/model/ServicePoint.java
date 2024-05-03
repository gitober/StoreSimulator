package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;

import java.util.LinkedList;

/**
 * ServicePoint class represents a service point in the store simulator.
 * It manages the queue of customers and schedules service completion events.
 */
public class ServicePoint {
	private LinkedList<Customer> queue = new LinkedList<>(); // LinkedList used for the queue
	private ContinuousGenerator serviceTimeGenerator; // Generates service times based on a distribution
	private EventList eventList; // Event list for scheduling events
	private EventType eventTypeScheduled; // Type of event to schedule on service completion
	// QueueStrategy strategy; // Could be added to manage different queueing strategies
	private boolean reserved = false; // Flag to check if the service point is currently reserved

	/**
	 * Constructs a ServicePoint with specified parameters.
	 * @param generator the continuous generator for service time distribution
	 * @param eventList the event list where completed service events are added
	 * @param eventType the type of event to schedule on service completion
	 */
	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType eventType) {
		this.serviceTimeGenerator = generator;
		this.eventList = eventList;
		this.eventTypeScheduled = eventType;
	}

	/**
	 * Adds a customer to the queue.
	 * @param customer the customer to add
	 */
	public void addQueue(Customer customer) {
		queue.add(customer);
	}

	/**
	 * Removes a customer from the queue, indicating they have been serviced.
	 * @return the customer who was serviced
	 */
	public Customer removeQueue() {
		reserved = false;
		return queue.poll();
	}

	/**
	 * Begins service for the next customer in the queue, if any.
	 */
	public void beginService() {
		if (!queue.isEmpty() && !reserved) {
			reserved = true;
			double serviceTime = serviceTimeGenerator.sample();
			// Schedule the completion of the service
			eventList.add(new Event(eventTypeScheduled, Clock.getInstance().getTime() + serviceTime));
		}
	}

	/**
	 * Checks if the service point is reserved.
	 * @return true if reserved, otherwise false
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * Checks if there are any customers in the queue.
	 * @return true if the queue is not empty, otherwise false
	 */
	public boolean isOnQueue() {
		return !queue.isEmpty();
	}
}
