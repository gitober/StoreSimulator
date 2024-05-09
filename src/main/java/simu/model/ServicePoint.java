package simu.model;

import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Event;
import simu.framework.EventList;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a service point in the simulation where customers queue and receive services.
 * Manages the customer queue and triggers service events based on the arrival and departure of customers.
 */
public class ServicePoint {

	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private Queue<Customer> queue;
	private boolean reserved;
	private int queueLength;
	private QueueHistoryDao queueStatusDao;
	private String name;

	/**
	 * Constructs a new service point with a specified random generator, event list, event type, and name.
	 *
	 * @param generator The random generator to generate service times.
	 * @param eventList The event list to which service events will be added.
	 * @param eventType The type of event representing this service point.
	 * @param name      The name of the service point.
	 */
	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType eventType, String name) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.queue = new LinkedList<>();
		this.queueLength = 0;
		this.queueStatusDao = new QueueHistoryDao();
		this.name = name;
	}

	/**
	 * Adds a customer to the service point's queue.
	 * If the service point is not reserved (i.e., not serving another customer), it starts serving immediately.
	 *
	 * @param customer The customer to be added to the queue.
	 */
	public void addQueue(Customer customer) {
		queue.add(customer);
		queueLength++;
		if (!reserved) {
			beginService();
		}
		logQueueStatus(customer);
	}

	/**
	 * Removes a customer from the service point's queue and returns them.
	 * Marks the service point as not reserved after removal.
	 *
	 * @return The customer removed from the queue, or {@code null} if the queue is empty.
	 */
	public Customer removeQueue() {
		reserved = false;
		Customer customer = queue.poll();
		if (customer != null) {
			queueLength--;
		}
		logQueueStatus(customer);
		return customer;
	}

	/**
	 * Begins serving the next customer in the queue by creating a service event with a delay based on the generator.
	 * Marks the service point as reserved.
	 */
	public void beginService() {
		if (!queue.isEmpty()) {
			reserved = true;
			double delay = generator.sample();
			eventList.add(new Event(eventType, delay));
		}
		logQueueStatus(queue.peek());
	}

	/**
	 * Checks if there are customers in the queue.
	 *
	 * @return {@code true} if there are customers in the queue, {@code false} otherwise.
	 */
	public boolean isOnQueue() {
		return !queue.isEmpty();
	}

	/**
	 * Checks if the service point is currently reserved (i.e., serving a customer).
	 *
	 * @return {@code true} if reserved, {@code false} otherwise.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * Returns the current length of the customer queue.
	 *
	 * @return The queue length.
	 */
	public int getQueueLength() {
		return queueLength;
	}

	/**
	 * Logs the current status of the queue to the database for historical analysis.
	 *
	 * @param customer The customer whose status to log, or {@code null} if no customer is at the head of the queue.
	 */
	private void logQueueStatus(Customer customer) {
		if (customer != null) {
			LocalDateTime timestamp = LocalDateTime.now();
			int customerId = customer.getId();
			double delay = generator.sample(); // Assuming delay is the waiting time

			QueueHistory queueStatus = new QueueHistory(0, customerId, name, timestamp, timestamp, delay);
			queueStatusDao.persist(queueStatus);
		}
	}

	/**
	 * Returns the name of the service point.
	 *
	 * @return The name of the service point.
	 */
	public String getName() {
		return this.name;
	}
}
