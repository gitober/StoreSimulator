package simu.framework;

import controller.IControllerMtoV;
import simu.model.ServicePoint;

import java.text.DecimalFormat;

public abstract class Engine extends Thread implements IEngine {
	private double simulationTime = 0;
	private long delay = 0;
	private Clock clock;

	protected EventList eventList;
	protected ServicePoint[] servicePoints;
	protected IControllerMtoV controller;

	public Engine(IControllerMtoV controller) {
		this.controller = controller;
		clock = Clock.getInstance();
		eventList = new EventList();
	}

	@Override
	public void setSimulationTime(double time) {
		simulationTime = time;
	}

	@Override
	public void setDelay(long time) {
		this.delay = time;
	}

	@Override
	public long getDelay() {
		return delay;
	}

	@Override
	public void run() {
		initialization();

		while (simulate()) {
			delay();
			clock.setTime(currentTime());
			runBEvents();
			tryCEvents();
		}

		results();
	}

	private void runBEvents() {
		while (!eventList.isEmpty() && eventList.getNextTime() == clock.getTime()) {
			runEvent(eventList.remove());
		}
	}

	private void tryCEvents() {
		if (servicePoints == null) {
			return;
		}

		for (ServicePoint p : servicePoints) {
			if (!p.isReserved() && p.isOnQueue()) {
				p.beginService();
			}
		}
	}

	private double currentTime() {
		return eventList.getNextTime();
	}

	private boolean simulate() {
		DecimalFormat df = new DecimalFormat("#0.00");
		double currentTime = clock.getTime();
		// Trace.out(Trace.Level.INFO, "Current Time: " + df.format(currentTime) + " seconds");
		return currentTime < simulationTime && eventList.getNextTime() < Double.POSITIVE_INFINITY;
	}

	private void delay() {
		DecimalFormat df = new DecimalFormat("#0.00");
		// Trace.out(Trace.Level.INFO, "Delay: " + df.format(delay) + " seconds");
		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void initialization();

	protected abstract void runEvent(Event t);

	protected abstract void results();
}