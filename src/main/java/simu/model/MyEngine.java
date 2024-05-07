package simu.model;

import controller.IControllerMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.*;

import java.util.ArrayList;
import java.util.List;

public class MyEngine extends Engine {
	private ArrivalProcess arrivalProcess;
	private ServicePoint[] servicePoints;
	private int maxCustomers;
	private List<Customer> customers = new ArrayList<>();
	private List<Event> events;

	public MyEngine(IControllerMtoV controller, int maxCustomers) {
		super(controller);
		this.maxCustomers = maxCustomers;

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
			double arrivalTime = Clock.getInstance().getTime() + new Normal(3, 1).sample();
			Event arrivalEvent = new Event(EventType.ARRIVAL, arrivalTime);
			eventList.add(arrivalEvent);
			events.add(arrivalEvent);
		}
	}

	@Override
	public void runEvent(Event event) {
		Customer customer;
		int currentServicePoint;
		double queueTime;
		double serviceTime;
		int nextServicePoint;

		switch ((EventType) event.getType()) {
			case ARRIVAL:
				if (customers.size() < maxCustomers) {
					customer = new Customer();
					customers.add(customer); // Keep track of all customers
					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
						queueTime = Clock.getInstance().getTime();
						customer.setArrivalTime(queueTime);
						customer.queueing(nextServicePoint, servicePoints[nextServicePoint - 1].getQueueLength());
						servicePoints[nextServicePoint - 1].addQueue(customer);
						arrivalProcess.generateNext();
						controller.visualiseCustomer(nextServicePoint);
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
					queueTime = Clock.getInstance().getTime() - customer.getArrivalTime();
					serviceTime = Clock.getInstance().getTime();
					queueTime = customer.queueTime(currentServicePoint, queueTime);
					customer.addServiceTime(currentServicePoint, queueTime);
					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
						customer.setArrivalTime(serviceTime);
						customer.queueing(nextServicePoint, servicePoints[nextServicePoint - 1].getQueueLength());
						servicePoints[nextServicePoint - 1].addQueue(customer);
						controller.visualiseCustomer(nextServicePoint);
					} else {
						customer.setRemovalTime(serviceTime);
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