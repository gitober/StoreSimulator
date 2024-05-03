package simu.framework;

import controller.IControllerMtoV;
import simu.model.ServicePoint;
import java.util.PriorityQueue;
import java.util.Comparator;

public abstract class Engine extends Thread implements IEngine {
	private double simulationTime = 0;    // time when the simulation will be stopped
	private long delay = 0;
	private Clock clock;                  // Simplifies code for clock access
	protected EventList eventList;
	private PriorityQueue<Event> priorityEventList;  // Priority queue for events based on their priority
	protected ServicePoint[] servicePoints;
	protected IControllerMtoV controller;

	public Engine(IControllerMtoV controller) {
		this.controller = controller;
		this.clock = Clock.getInstance();
		this.eventList = new EventList();
		this.priorityEventList = new PriorityQueue<>(Comparator.comparing(Event::getTime));
	}

	@Override
	public void setSimulationTime(double time) {
		this.simulationTime = time;
	}

	@Override
	public void setDelay(long time) {
		this.delay = time;
	}

	@Override
	public long getDelay() {
		return this.delay;
	}

	@Override
	public void run() {
		initialization(); // Creating the first event, etc.

		while (simulate()){
			delay();
			clock.setTime(currentTime());
			runPriorityEvents();
			tryCEvents();
		}

		results();
	}

	private void runPriorityEvents() {
		while (!priorityEventList.isEmpty() && priorityEventList.peek().getTime() <= clock.getTime()) {
			runEvent(priorityEventList.poll());
		}
	}

	private void tryCEvents() {
		for (ServicePoint p: servicePoints){
			if (!p.isReserved() && p.isOnQueue()){
				p.beginService();
			}
		}
	}

	private double currentTime() {
		return priorityEventList.isEmpty() ? Double.MAX_VALUE : priorityEventList.peek().getTime();
	}

	private boolean simulate() {
		Trace.out(Trace.Level.INFO, "Time is: " + clock.getTime());
		return clock.getTime() < simulationTime;
	}

	private void delay() {
		Trace.out(Trace.Level.INFO, "Delay " + delay);
		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();  // Properly handle the interrupt
		}
	}

	protected abstract void initialization();
	protected abstract void runEvent(Event event);
	protected abstract void results();
}
