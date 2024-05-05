package simu.framework;

import eduni.distributions.ContinuousGenerator;
import simu.model.EventType;

public class ArrivalProcess {
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType eventType;
	private int customerCount = 0;
	private final int maxCustomers;

	public ArrivalProcess(ContinuousGenerator generator, EventList eventList, EventType eventType, int maxCustomers) {
		this.generator = generator;
		this.eventList = eventList;
		this.eventType = eventType;
		this.maxCustomers = maxCustomers;
	}

	public void generateNext() {
		if (customerCount < maxCustomers) {
			eventList.add(new Event(eventType, generator.sample()));
			customerCount++;
		}
	}
}