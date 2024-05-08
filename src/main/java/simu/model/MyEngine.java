/**
 * Package containing simulation models and related components.
 */
package simu.model;

import simu.framework.ArrivalTimeGenerator;
import db.connections.datasource.MariaDbConnection;

import controller.IControllerMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Distributions;
import simu.framework.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulation engine class that orchestrates the entire simulation flow.
 * It controls the creation of customers, manages their service at different service points,
 * and records data about customer flow through service points into a database.
 */
public class MyEngine extends Engine {
	private Connection connection;
	private ArrivalProcess arrivalProcess;
	private ServicePoint[] servicePoints;
	private int maxCustomers;
	private List<Customer> customers = new ArrayList<>();
	private List<Event> events;

	/**
	 * Constructs a new simulation engine.
	 *
	 * @param controller   The controller for visualization updates.
	 * @param maxCustomers The maximum number of customers for the simulation.
	 */
	public MyEngine(IControllerMtoV controller, int maxCustomers) {
		super(controller);
		this.maxCustomers = maxCustomers;
		this.connection = MariaDbConnection.getConnection();

		servicePoints = new ServicePoint[4];
		servicePoints[0] = new ServicePoint(new Normal(5, 2), eventList, EventType.SERVICE_DESK, "Service Desk");
		servicePoints[1] = new ServicePoint(new Normal(8, 3), eventList, EventType.DELI_COUNTER, "Deli Counter");
		servicePoints[2] = new ServicePoint(new Normal(3, 1), eventList, EventType.VEGETABLE_SECTION, "Vegetable Section");
		servicePoints[3] = new ServicePoint(new Normal(4, 2), eventList, EventType.CASHIER, "Cashier");
		arrivalProcess = new ArrivalProcess(new Negexp(10, 5), eventList, EventType.ARRIVAL, maxCustomers);

		events = new ArrayList<>();
	}

	/**
	 * Returns the list of recorded events.
	 *
	 * @return A list of events.
	 */
	public List<Event> getEventList() {
		return this.events;
	}

	/**
	 * Returns the list of customers processed in the simulation.
	 *
	 * @return A list of customers.
	 */
	public List<Customer> getCustomers() {
		return this.customers;
	}

	/**
	 * Returns the array of service points in the simulation.
	 *
	 * @return An array of service points.
	 */
	public ServicePoint[] getServicePoints() {
		return this.servicePoints;
	}

	/**
	 * Returns the arrival process object used in the simulation.
	 *
	 * @return The arrival process.
	 */
	public ArrivalProcess getArrivalProcess() {
		return this.arrivalProcess;
	}

	/**
	 * Initializes the simulation with a set of initial arrival events.
	 */
	@Override
	public void initialization() {
		for (int i = 1; i <= maxCustomers; i++) {
			double arrivalTime = Clock.getInstance().getTime() + new ArrivalTimeGenerator(new Distributions()).generateArrivalTime(i);
			Event arrivalEvent = new Event(EventType.ARRIVAL, arrivalTime);
			eventList.add(arrivalEvent);
			events.add(arrivalEvent);
		}
	}

	/**
	 * Processes an event and manages customer flow through service points.
	 *
	 * @param event The event to be processed.
	 */
	@Override
	public void runEvent(Event event) {
		Customer customer;
		int currentServicePoint;
		double queueTime = 0.0;
		double serviceTime = 0.0;
		double departureTime;
		int nextServicePoint = -1;
		Connection connection = MariaDbConnection.getConnection();

		switch ((EventType) event.getType()) {
			case ARRIVAL:
				if (customers.size() < maxCustomers) {
					customer = new Customer();
					customers.add(customer);
					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
						double arrivalTime = Clock.getInstance().getTime();
						customer.setArrivalTime(arrivalTime);
						customer.queueing(nextServicePoint, servicePoints[nextServicePoint - 1].getQueueLength());
						servicePoints[nextServicePoint - 1].addQueue(customer);
						arrivalProcess.generateNext();
						controller.visualiseCustomer(nextServicePoint);
						recordServicePoint(customer.getId(), servicePoints[nextServicePoint - 1].getName(), arrivalTime, arrivalTime, 0.0, connection);
					}
				}
				break;

			case SERVICE_DESK:
			case DELI_COUNTER:
			case VEGETABLE_SECTION:
			case CASHIER:
				currentServicePoint = ((EventType) event.getType()).ordinal();
				customer = servicePoints[currentServicePoint - 1].removeQueue();
				if (customer != null) {
					double currentTime = Clock.getInstance().getTime();
					int queueLength = servicePoints[currentServicePoint - 1].getQueueLength();

					if (queueLength > 0) {
						queueTime = currentTime - customer.getArrivalTime();
					} else {
						queueTime = 0.0;
					}
					serviceTime = currentTime;
					queueTime = customer.queueTime(currentServicePoint, queueTime);
					customer.addServiceTime(currentServicePoint, queueTime);

					departureTime = Clock.getInstance().getTime();
					recordServicePoint(customer.getId(), servicePoints[currentServicePoint - 1].getName(), customer.getArrivalTime(), departureTime, queueTime, connection);

					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
						customer.setArrivalTime(departureTime);
						customer.queueing(nextServicePoint, servicePoints[nextServicePoint - 1].getQueueLength());
						servicePoints[nextServicePoint - 1].addQueue(customer);
						controller.visualiseCustomer(nextServicePoint);
					} else {
						customer.setRemovalTime(departureTime);
						customer.recordSummary();
					}
				}
				break;
		}

		for (ServicePoint sp : servicePoints) {
			if (sp.isOnQueue() && !sp.isReserved()) {
				sp.beginService();
			}
		}
	}

	/**
	 * Records a customer's service point data in the database.
	 *
	 * @param customerId       The customer ID.
	 * @param servicePointName The name of the service point.
	 * @param arrivalTime      The time the customer arrived at the service point.
	 * @param departureTime    The time the customer departed from the service point.
	 * @param queueTime        The time the customer spent in the queue.
	 * @param connection       The database connection.
	 */
	private void recordServicePoint(int customerId, String servicePointName, double arrivalTime, double departureTime, double queueTime, Connection connection) {
		try {
			System.out.printf("Recording: Customer #%d, Service Point: %s, Arrival: %.2f, Departure: %.2f, Queue Time: %.2f minutes\n",
					customerId, servicePointName, arrivalTime, departureTime, queueTime);

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO customer_queue_history (customer_id, service_point_name, arrival_time, departure_time, queue_time) VALUES (?, ?, ?, ?, ?)"
			);
			statement.setInt(1, customerId);
			statement.setString(2, servicePointName);
			statement.setTimestamp(3, new Timestamp((long) arrivalTime * 1000));
			statement.setTimestamp(4, new Timestamp((long) departureTime * 1000));
			statement.setDouble(5, queueTime);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the end time of the simulation and outputs a summary of all customers.
	 */
	@Override
	protected void results() {
		controller.showEndTime(Clock.getInstance().getTime());
		for (Customer customer : customers) {
			Trace.out(Trace.Level.INFO, customer.getSummary());
		}
	}

	/**
	 * Sets the arrival pattern of customers in the simulation.
	 *
	 * @param pattern The desired arrival pattern.
	 */
	public void setArrivalPattern(ArrivalPattern pattern) {
		String message;
		switch (pattern) {
			case MORNINGRUSH:
				arrivalProcess = new ArrivalProcess(new Normal(3, 1), eventList, EventType.ARRIVAL, maxCustomers);
				message = "Setting arrival pattern: Morning Rush (frequent arrivals).";
				break;
			case MIDDAYLULL:
				arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.ARRIVAL, maxCustomers);
				message = "Setting arrival pattern: Midday Lull (less frequent arrivals).";
				break;
			case AFTERNOONRUSH:
				arrivalProcess = new ArrivalProcess(new Normal(3, 1), eventList, EventType.ARRIVAL, maxCustomers);
				message = "Setting arrival pattern: Afternoon Rush (frequent arrivals).";
				break;
			default:
				message = "Unknown arrival pattern.";
				break;
		}
		System.out.println(message);
	}
}
