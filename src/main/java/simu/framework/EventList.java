package simu.framework;

import java.util.PriorityQueue;

/**
 * The EventList class represents a list of events in the simulation.
 * It maintains events in a priority queue based on their occurrence time.
 */
public class EventList {
	private PriorityQueue<Event> events = new PriorityQueue<>();

	/**
	 * Retrieves the time of the next event in the list.
	 *
	 * @return The time of the next event, or Double.POSITIVE_INFINITY if the list is empty.
	 */
	public double getNextTime() {
		Event nextEvent = events.peek();
		return nextEvent != null ? nextEvent.getTime() : Double.POSITIVE_INFINITY;
	}

	/**
	 * Removes and retrieves the next event from the list.
	 *
	 * @return The next event in the list.
	 */
	public Event remove() {
		return events.poll();
	}

	/**
	 * Adds an event to the list.
	 *
	 * @param event The event to be added to the list.
	 */
	public void add(Event event) {
		events.add(event);
	}

	/**
	 * Checks if the event list is empty.
	 *
	 * @return true if the event list is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return events.isEmpty();
	}

	/**
	 * Retrieves the number of events in the list.
	 *
	 * @return The number of events in the list.
	 */
	public int size() {
		return events.size();
	}
}
