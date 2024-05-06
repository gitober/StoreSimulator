package simu.model;

import controller.IControllerMtoV;
import eduni.distributions.Distributions;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the engine of the simulation.
 * It extends the Engine class and provides methods to get the event list, customers, service points, and arrival process, initialize the simulation, run an event, show the results, and set the arrival pattern.
 */
public class MyEngine extends Engine {
	private ArrivalProcess arrivalProcess;
	private ServicePoint[] servicePoints;
	private int maxCustomers;
	private List<Customer> customers = new ArrayList<>();
	private List<Event> events;

	/**
	 * Constructs a new MyEngine with the given controller and maximum number of customers.
	 * @param controller The controller for the simulation.
	 * @param maxCustomers The maximum number of customers in the simulation.
	 */
	public MyEngine(IControllerMtoV controller, int maxCustomers) {
		super(controller);
		this.maxCustomers = maxCustomers;

		servicePoints = new ServicePoint[4];
		servicePoints[0] = new ServicePoint(new Normal(5, 2), eventList, EventType.SERVICE_DESK);
		servicePoints[1] = new ServicePoint(new Normal(8, 3), eventList, EventType.DELI_COUNTER);
		servicePoints[2] = new ServicePoint(new Normal(3, 1), eventList, EventType.VEGETABLE_SECTION);
		servicePoints[3] = new ServicePoint(new Normal(4, 2), eventList, EventType.CASHIER);
		arrivalProcess = new ArrivalProcess(new Negexp(10, 5), eventList, EventType.ARRIVAL, maxCustomers);

		events = new ArrayList<>();
	}

	/**
	 * Returns the event list of the simulation.
	 * @return The event list of the simulation.
	 */
	public List<Event> getEventList() {
		return this.events;
	}

	/**
	 * Returns the customers of the simulation.
	 * @return The customers of the simulation.
	 */
	public List<Customer> getCustomers() {
		return this.customers;
	}

	/**
	 * Returns the service points of the simulation.
	 * @return The service points of the simulation.
	 */
	public ServicePoint[] getServicePoints() {
		return this.servicePoints;
	}

	/**
	 * Returns the arrival process of the simulation.
	 * @return The arrival process of the simulation.
	 */
	public ArrivalProcess getArrivalProcess() {
		return this.arrivalProcess;
	}

	/**
	 * Initializes the simulation by generating the arrival times for the customers.
	 */
	@Override
	public void initialization() {
		Distributions distributions = new Distributions();
		ArrivalTimeGenerator arrivalTimeGenerator = new ArrivalTimeGenerator(distributions);
		double lambda = 0.1;

		for (int i = 1; i <= maxCustomers; i++) {
			double arrivalTime = arrivalTimeGenerator.generateArrivalTime(lambda);
			Event arrivalEvent = new Event(EventType.ARRIVAL, arrivalTime);
			eventList.add(arrivalEvent);
			events.add(arrivalEvent);
		}
	}

	/**
	 * Runs an event in the simulation.
	 * @param event The event to run.
	 */
	@Override
	public void runEvent(Event event) {
		// ... rest of the code ...
	}

	/**
	 * Shows the results of the simulation.
	 */
	@Override
	protected void results() {
		controller.showEndTime(Clock.getInstance().getTime());
		for (Customer customer : customers) {
			Trace.out(Trace.Level.INFO, customer.getSummary());
		}
	}

	/**
	 * Sets the arrival pattern of the simulation.
	 * @param pattern The arrival pattern to set.
	 */
	public void setArrivalPattern(ArrivalPattern pattern) {
		// ... rest of the code ...
	}
}