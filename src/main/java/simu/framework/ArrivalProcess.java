package simu.framework;

import eduni.distributions.ContinuousGenerator;
import simu.model.EventType;

/**
 * This class represents the arrival process in the simulation.
 * It provides methods to generate the next event in the process.
 */
public class ArrivalProcess {
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private int customerCount = 0;
	private final int maxCustomers;

	/**
	 * Constructs a new ArrivalProcess with the given generator, event list, event type, and maximum number of customers.
	 * @param generator The generator for the arrival process.
	 * @param eventList The event list for the arrival process.
	 * @param eventType The event type for the arrival process.
	 * @param maxCustomers The maximum number of customers in the arrival process.
	 */
	public ArrivalProcess(ContinuousGenerator generator, EventList eventList, EventType eventType, int maxCustomers) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.maxCustomers = maxCustomers;
	}

	/**
	 * Generates the next event in the arrival process.
	 */
	public void generateNext() {
		if (customerCount < maxCustomers) {
			eventList.add(new Event(eventType, generator.sample()));
			customerCount++;
		}
	}
}