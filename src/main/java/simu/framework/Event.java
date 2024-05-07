package simu.framework;

/**
 * The Event class represents an event in the simulation.
 * It encapsulates information such as the event type and the time at which it occurs.
 */
public class Event implements Comparable<Event> {
	private IEventType type;
	private double time;

	/**
	 * Constructs an Event object with the specified event type and time.
	 *
	 * @param type The type of the event.
	 * @param time The time at which the event occurs.
	 */
	public Event(IEventType type, double time) {
		this.type = type;
		this.time = time;
	}

	/**
	 * Sets the type of the event.
	 *
	 * @param type The type of the event to be set.
	 */
	public void setType(IEventType type) {
		this.type = type;
	}

	/**
	 * Retrieves the type of the event.
	 *
	 * @return The type of the event.
	 */
	public IEventType getType() {
		return type;
	}

	/**
	 * Sets the time at which the event occurs.
	 *
	 * @param time The time of the event to be set.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Retrieves the time at which the event occurs.
	 *
	 * @return The time of the event.
	 */
	public double getTime() {
		return time;
	}

	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}
}
