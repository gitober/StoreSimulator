package simu.framework;

import eduni.distributions.ContinuousGenerator;
import simu.model.EventType;

/**
 * The ArrivalProcess class is responsible for generating arrival events
 * based on a given continuous distribution generator.
 */
public class ArrivalProcess {
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private int customerCount = 0;
	private final int maxCustomers;

	/**
	 * Constructs an ArrivalProcess object.
	 *
	 * @param generator    The generator used to create random arrival times.
	 * @param eventList    The event list where the arrival events will be added.
	 * @param eventType    The type of event representing customer arrival.
	 * @param maxCustomers The maximum number of customers to be generated.
	 */
	public ArrivalProcess(ContinuousGenerator generator, EventList eventList, EventType eventType, int maxCustomers) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.maxCustomers = maxCustomers;
	}

	/**
	 * Generates the next arrival event if the customer count is less than the maximum.
	 */
	public void generateNext() {
		if (customerCount < maxCustomers) {
			eventList.add(new Event(eventType, generator.sample()));
			customerCount++;
		}
	}
}
