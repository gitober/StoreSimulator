package simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import controller.IControllerMtoV;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

public class MyEngine extends Engine {
	private ArrivalProcess arrivalProcess;

	public MyEngine(IControllerMtoV controller) {
		super(controller);

		servicePoints = new ServicePoint[4]; // Assigning four service points as per simulation requirements

		servicePoints[0] = new ServicePoint(new Normal(8, 2), eventList, EventType.DEP1); // Cash registers
		servicePoints[1] = new ServicePoint(new Normal(10, 3), eventList, EventType.DEP2); // Customer service
		servicePoints[2] = new ServicePoint(new Normal(5, 1), eventList, EventType.DEP3); // Vegetable section
		servicePoints[3] = new ServicePoint(new Normal(7, 2), eventList, EventType.DEP4); // Bakery

		arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1); // General store arrival
	}

	@Override
	protected void initialization() {
		arrivalProcess.generateNext(); // Generate the first arrival in the system
	}

	@Override
	protected void runEvent(Event t) {
		Customer a;

		switch ((EventType)t.getType()) {
			case ARR1:
				servicePoints[0].addQueue(new Customer()); // Assume arrivals start at the cash registers
				arrivalProcess.generateNext();
				controller.visualiseCustomer(); // Visualize customer arrival at the UI
				break;

			case DEP1:
				a = servicePoints[0].removeQueue();
				servicePoints[1].addQueue(a); // Move to customer service
				break;

			case DEP2:
				a = servicePoints[1].removeQueue();
				servicePoints[2].addQueue(a); // Move to vegetable section
				break;

			case DEP3:
				a = servicePoints[2].removeQueue();
				servicePoints[3].addQueue(a); // Move to bakery
				break;

			case DEP4:
				a = servicePoints[3].removeQueue();
				a.setRemovalTime(Clock.getInstance().getTime());
				a.reportResults(); // Customer completes their visit and leaves the store
				break;
		}
	}

	@Override
	protected void results() {
		controller.showEndTime(Clock.getInstance().getTime()); // Notify UI about the end of simulation
	}
}
