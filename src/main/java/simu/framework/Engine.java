package simu.framework;

import controller.IControllerMtoV;
import simu.model.MyEngine;
import simu.model.ServicePoint;

import java.text.DecimalFormat;

/**
 * The Engine class represents the simulation engine.
 * It provides functionality to run the simulation, handle events, and manage time.
 */
public abstract class Engine extends Thread implements IEngine {
	private double simulationTime = 0;
	private long delay = 0;
	private Clock clock;

	protected EventList eventList;
	protected ServicePoint[] servicePoints;
	protected IControllerMtoV controller;

	/**
	 * Constructs an Engine object with the specified controller.
	 *
	 * @param controller The controller for the simulation.
	 */
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

	/**
	 * Runs the events that are scheduled to occur at the current simulation time.
	 */
	public void runBEvents() {
		while (!eventList.isEmpty() && eventList.getNextTime() == clock.getTime()) {
			runEvent(eventList.remove());
		}
	}

	/**
	 * Tries to initiate service at service points if they are not reserved and have customers waiting.
	 */
	public void tryCEvents() {
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

	/**
	 * Determines whether the simulation should continue based on the current time and event list.
	 *
	 * @return true if the simulation should continue, false otherwise.
	 */
	public boolean simulate() {
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

	/**
	 * Sets the event list for the simulation engine.
	 *
	 * @param eventList The event list to be set.
	 */
	public void setEventList(EventList eventList) {
		this.eventList = eventList;
	}

	/**
	 * Sets the service points for the simulation engine.
	 *
	 * @param servicePoints The array of service points to be set.
	 */
	public void setServicePoints(ServicePoint[] servicePoints) {
		this.servicePoints = servicePoints;
	}

	/**
	 * The ConcreteEngine class extends MyEngine and represents a concrete implementation of the simulation engine.
	 */
	public static class ConcreteEngine extends MyEngine {
		/**
		 * Constructs a ConcreteEngine object with the specified controller and maximum number of customers.
		 *
		 * @param controller   The controller for the simulation.
		 * @param maxCustomers The maximum number of customers allowed in the simulation.
		 */
		public ConcreteEngine(IControllerMtoV controller, int maxCustomers) {
			super(controller, maxCustomers);
		}
	}

	/**
	 * Initializes the simulation engine.
	 */
	protected abstract void initialization();

	/**
	 * Runs a specific event in the simulation.
	 *
	 * @param t The event to be run.
	 */
	public abstract void runEvent(Event t);

	/**
	 * Handles the results of the simulation.
	 */
	protected abstract void results();
}