package simu.model;

import controller.IControllerMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.*;

import java.text.DecimalFormat;
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

		arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.ARRIVAL, maxCustomers);
		events = new ArrayList<>();
	}

	@Override
	public void initialization() {
		for (int i = 1; i <= maxCustomers; i++) {
			double arrivalTime = Clock.getInstance().getTime() + new Negexp(10).sample();
			Event arrivalEvent = new Event(EventType.ARRIVAL, arrivalTime);
			eventList.add(arrivalEvent);
			events.add(arrivalEvent);
		}
	}

	@Override
	public void runEvent(Event event) {
		double simulationTime = Clock.getInstance().getTime();
		Customer customer;
		int currentServicePoint;
		double queueTime;
		double serviceTime;
		int nextServicePoint;

		switch ((EventType) event.getType()) {
			case ARRIVAL:
				if (customers.size() < maxCustomers) {
					customer = new Customer();
					customer.setArrivalTime(simulationTime);
					customers.add(customer);
					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
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
				customer = servicePoints[currentServicePoint - 1].removeQueue(simulationTime);
				if (customer != null) {
					// Calculate queue time only if there was someone ahead
					if (servicePoints[currentServicePoint - 1].getQueueLength() > 0) {
						queueTime = simulationTime - customer.getArrivalTime();
					} else {
						queueTime = 0.0;
					}
					customer.queueTime(currentServicePoint, queueTime);
					serviceTime = new Normal(5, 2).sample();
					customer.stayTime(currentServicePoint, serviceTime);
					customer.setArrivalTime(simulationTime + serviceTime);
					nextServicePoint = customer.getNextServicePoint();
					if (nextServicePoint != -1) {
						customer.queueing(nextServicePoint, servicePoints[nextServicePoint - 1].getQueueLength());
						servicePoints[nextServicePoint - 1].addQueue(customer);
						controller.visualiseCustomer(nextServicePoint);
					} else {
						customer.setRemovalTime(simulationTime + serviceTime);
						customer.recordSummary();
					}
				}
				break;
		}

		for (ServicePoint sp : servicePoints) {
			if (sp.isOnQueue() && !sp.isReserved()) {
				sp.beginService(simulationTime);
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

	@Override
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
