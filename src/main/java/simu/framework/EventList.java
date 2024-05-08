package simu.framework;

import java.util.PriorityQueue;

/**
 * The EventList class manages a list of events in a priority queue, ordered by event time.
 */
public class EventList {
	private PriorityQueue<Event> events = new PriorityQueue<>();

	/**
	 * Returns the time of the next event to be processed.
	 *
	 * @return The time of the next event or {@link Double#POSITIVE_INFINITY} if the list is empty.
	 */
	public double getNextTime() {
		Event nextEvent = events.peek();
		return nextEvent != null ? nextEvent.getTime() : Double.POSITIVE_INFINITY;
	}

	/**
	 * Removes and returns the next event to be processed.
	 *
	 * @return The next event or null if the list is empty.
	 */
	public Event remove() {
		return events.poll();
	}

	/**
	 * Adds a new event to the event list.
	 *
	 * @param event The event to be added.
	 */
	public void add(Event event) {
		events.add(event);
	}

	/**
	 * Checks if the event list is empty.
	 *
	 * @return True if the event list is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return events.isEmpty();
	}

	/**
	 * Returns the number of events in the event list.
	 *
	 * @return The size of the event list.
	 */
	public int size() {
		return events.size();
	}
}
