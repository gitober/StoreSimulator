package simu.framework;

import java.util.PriorityQueue;

public class EventList {
	private PriorityQueue<Event> events = new PriorityQueue<>();

	public double getNextTime() {
		Event nextEvent = events.peek();
		return nextEvent != null ? nextEvent.getTime() : Double.POSITIVE_INFINITY;
	}

	public Event remove() {
		return events.poll();
	}

	public void add(Event event) {
		events.add(event);
	}

	public boolean isEmpty() {
		return events.isEmpty();
	}
}