package simu.framework;

import controller.IControllerMtoV;
import simu.model.MyEngine;
import simu.model.ServicePoint;

import java.text.DecimalFormat;

/**
 * The Engine class represents the abstract base class for all engines that drive the simulation.
 * It provides the core framework for running events, managing time, and controlling service points.
 */
public abstract class Engine extends Thread implements IEngine {
	private double simulationTime = 0;
	private long delay = 0;
	private Clock clock;

	protected EventList eventList;
	protected ServicePoint[] servicePoints;
	protected IControllerMtoV controller;

	/**
	 * Constructs an Engine with the specified controller.
	 *
	 * @param controller The controller to communicate with the view.
	 */
	public Engine(IControllerMtoV controller) {
		this.controller = controller;
		clock = Clock.getInstance();
		eventList = new EventList();
	}

	/**
	 * Sets the total simulation time.
	 *
	 * @param time The time to set for the total simulation duration.
	 */
	@Override
	public void setSimulationTime(double time) {
		simulationTime = time;
	}

	/**
	 * Sets the delay between simulation events.
	 *
	 * @param time The delay to set in milliseconds.
	 */
	@Override
	public void setDelay(long time) {
		this.delay = time;
	}

	/**
	 * Returns the current delay between simulation events.
	 *
	 * @return The current delay in milliseconds.
	 */
	@Override
	public long getDelay() {
		return delay;
	}

	/**
	 * The main simulation loop. It initializes the simulation, processes events,
	 * manages the clock, and finally outputs results.
	 */
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
	 * Processes all 'B' events (events that are due for execution).
	 */
	public void runBEvents() {
		while (!eventList.isEmpty() && eventList.getNextTime() == clock.getTime()) {
			runEvent(eventList.remove());
		}
	}

	/**
	 * Attempts to process 'C' events (conditional events based on service point availability).
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

	/**
	 * Returns the current simulation time based on the event list.
	 *
	 * @return The current simulation time.
	 */
	private double currentTime() {
		return eventList.getNextTime();
	}

	/**
	 * Checks if the simulation should continue running.
	 *
	 * @return True if the simulation should continue, false otherwise.
	 */
	public boolean simulate() {
		DecimalFormat df = new DecimalFormat("#0.00");
		double currentTime = clock.getTime();
		// Trace.out(Trace.Level.INFO, "Current Time: " + df.format(currentTime) + " seconds");
		return currentTime < simulationTime && eventList.getNextTime() < Double.POSITIVE_INFINITY;
	}

	/**
	 * Pauses the simulation for the current delay duration.
	 */
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
	 * Sets the event list for managing simulation events.
	 *
	 * @param eventList The event list to be set.
	 */
	public void setEventList(EventList eventList) {
		this.eventList = eventList;
	}

	/**
	 * Sets the array of service points to be managed during the simulation.
	 *
	 * @param servicePoints An array of service points.
	 */
	public void setServicePoints(ServicePoint[] servicePoints) {
		this.servicePoints = servicePoints;
	}

	/**
	 * A concrete implementation of the Engine class using the MyEngine model.
	 */
	public static class ConcreteEngine extends MyEngine {
		/**
		 * Constructs a ConcreteEngine object.
		 *
		 * @param controller   The controller to communicate with the view.
		 * @param maxCustomers The maximum number of customers for the simulation.
		 */
		public ConcreteEngine(IControllerMtoV controller, int maxCustomers) {
			super(controller, maxCustomers);
		}
	}

	/**
	 * Abstract method for initializing the simulation setup.
	 */
	protected abstract void initialization();

	/**
	 * Abstract method for processing a single event.
	 *
	 * @param t The event to be processed.
	 */
	public abstract void runEvent(Event t);

	/**
	 * Abstract method for outputting the results of the simulation.
	 */
	protected abstract void results();
}
