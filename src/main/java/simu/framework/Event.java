package simu.framework;

/**
 * The Event class represents a simulation event with a specific type and time.
 * Events can be compared based on their scheduled time.
 */
public class Event implements Comparable<Event> {
	private IEventType type;
	private double time;

	/**
	 * Constructs an Event object.
	 *
	 * @param type The type of the event.
	 * @param time The time at which the event should occur.
	 */
	public Event(IEventType type, double time) {
		this.type = type;
		this.time = time;
	}

	/**
	 * Sets the type of the event.
	 *
	 * @param type The event type to be set.
	 */
	public void setType(IEventType type) {
		this.type = type;
	}

	/**
	 * Returns the type of the event.
	 *
	 * @return The event type.
	 */
	public IEventType getType() {
		return type;
	}

	/**
	 * Sets the time at which the event should occur.
	 *
	 * @param time The time to be set.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Returns the time at which the event should occur.
	 *
	 * @return The event time.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Compares this event to another event based on the time.
	 *
	 * @param arg The event to compare to.
	 * @return -1 if this event's time is less than the other's, 1 if greater, and 0 if equal.
	 */
	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}
}
