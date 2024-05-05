package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Event;
import simu.framework.EventList;

import java.util.LinkedList;
import java.util.Queue;

public class ServicePoint {

	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private Queue<Customer> queue;
	private boolean reserved;
	private int queueLength; // Variable to track queue length

	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType eventType) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.queue = new LinkedList<>();
		this.queueLength = 0; // Initialize queue length to 0
	}

	public void addQueue(Customer customer) {
		queue.add(customer);
		queueLength++; // Increment queue length when customer joins the queue
		if (!reserved) {
			beginService();
		}
	}

	public Customer removeQueue() {
		reserved = false;
		Customer customer = queue.poll();
		if (customer != null) {
			queueLength--; // Decrement queue length when customer leaves the queue
		}
		return customer;
	}

	public void beginService() {
		if (!queue.isEmpty()) {
			reserved = true;
			double delay = generator.sample();
			eventList.add(new Event(eventType, delay));
			// Log queue status when service begins
			System.out.println(getQueueStatus());
		}
	}

	public boolean isOnQueue() {
		return !queue.isEmpty();
	}

	public boolean isReserved() {
		return reserved;
	}

	public int getQueueLength() {
		return queueLength;
	}

	public String getQueueStatus() {
		if (getQueueLength() > 0) {
			return "There are " + getQueueLength() + " customers in the queue.";
		} else {
			return "There are no customers in the queue.";
		}
	}
}