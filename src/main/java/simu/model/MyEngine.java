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

public class MyEngine extends Engine {
	private Connection connection;
	private ArrivalProcess arrivalProcess;
	private ServicePoint[] servicePoints;
	private int maxCustomers;
	private List<Customer> customers = new ArrayList<>();
	private List<Event> events;

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

	public List<Event> getEventList() {
		return this.events;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public ServicePoint[] getServicePoints() {
		return this.servicePoints;
	}

	public ArrivalProcess getArrivalProcess() {
		return this.arrivalProcess;
	}

	@Override
	public void initialization() {
		for (int i = 1; i <= maxCustomers; i++) {
			double arrivalTime = Clock.getInstance().getTime() + new ArrivalTimeGenerator(new Distributions()).generateArrivalTime(i);
			Event arrivalEvent = new Event(EventType.ARRIVAL, arrivalTime);
			eventList.add(arrivalEvent);
			events.add(arrivalEvent);
		}
	}

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
					customers.add(customer); // Keep track of all customers
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

					// Only calculate the queue time if the customer was actually in a queue
					if (queueLength > 0) {
						queueTime = currentTime - customer.getArrivalTime();
					} else {
						queueTime = 0.0;
					}
					serviceTime = currentTime;
					queueTime = customer.queueTime(currentServicePoint, queueTime);
					customer.addServiceTime(currentServicePoint, queueTime);

					// Record the customer's departure from the current service point
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
						customer.recordSummary(); // Record the summary instead of printing
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


	private void recordServicePoint(int customerId, String servicePointName, double arrivalTime, double departureTime, double queueTime, Connection connection) {
		try {
			System.out.printf("Recording: Customer #%d, Service Point: %s, Arrival: %.2f, Departure: %.2f, Queue Time: %.2f minutes\n",
					customerId, servicePointName, arrivalTime, departureTime, queueTime);

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO customer_queue_history (customer_id, service_point_name, arrival_time, departure_time, queue_time) VALUES (?, ?, ?, ?, ?)"
			);
			statement.setInt(1, customerId);
			statement.setString(2, servicePointName);
			statement.setTimestamp(3, new Timestamp((long) arrivalTime * 1000)); // Convert to milliseconds
			statement.setTimestamp(4, new Timestamp((long) departureTime * 1000)); // Convert to milliseconds
			statement.setDouble(5, queueTime);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	@Override
	protected void results() {
		controller.showEndTime(Clock.getInstance().getTime());
		for (Customer customer : customers) {
			Trace.out(Trace.Level.INFO, customer.getSummary());
		}
	}

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