package simu.framework;

public class Event {
	private IEventType type;
	private double time;

	public Event(IEventType type, double time) {
		this.type = type;
		this.time = time;
	}

	public void setType(IEventType type) {
		this.type = type;
	}

	public IEventType getType() {
		return type;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}
}
