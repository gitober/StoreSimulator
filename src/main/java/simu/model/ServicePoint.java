package simu.model;

import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class ServicePoint {
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private Queue<Customer> queue;
	private boolean reserved;
	private int queueLength;
	private QueueHistoryDao queueStatusDao;
	private String name;
	private Clock clock;

	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType eventType, String name) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.queue = new LinkedList<>();
		this.queueLength = 0;
		this.queueStatusDao = new QueueHistoryDao();
		this.name = name;
		this.clock = Clock.getInstance();
	}

	public void addQueue(Customer customer) {
		queue.add(customer);
		queueLength++;
		if (!reserved) {
			beginService(clock.getTime());
		}
		logQueueStatus(customer, clock.getTime());
	}

	public void beginService(double simulationTime) {
		if (!queue.isEmpty()) {
			reserved = true;
			double delay = generator.sample();
			eventList.add(new Event(eventType, simulationTime + delay));
		}
		logQueueStatus(queue.peek(), simulationTime);
	}

	public Customer removeQueue(double simulationTime) {
		reserved = false;
		Customer customer = queue.poll();
		if (customer != null) {
			queueLength--;
		}
		logQueueStatus(customer, simulationTime);
		return customer;
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

	public String getName() {
		return name;
	}


	private void logQueueStatus(Customer customer, double simulationTime) {
		if (customer != null) {
			LocalDateTime timestamp = LocalDateTime.now();
			int customerId = customer.getId();
			double delay = generator.sample(); // Assuming delay is the waiting time

			QueueHistory queueStatus = new QueueHistory(0, customerId, name, timestamp, timestamp, delay);
			queueStatusDao.persist(queueStatus);
		}
	}
}
