package simu.model;

import simu.framework.IEventType;

/**
 * The EventType enum represents different types of events that occur during the simulation.
 */
public enum EventType implements IEventType {
	/**
	 * Represents the arrival of a customer.
	 */
	ARRIVAL,

	/**
	 * Represents the customer service desk event.
	 */
	SERVICE_DESK,

	/**
	 * Represents the deli counter event.
	 */
	DELI_COUNTER,

	/**
	 * Represents the vegetable section event.
	 */
	VEGETABLE_SECTION,

	/**
	 * Represents the cashier event.
	 */
	CASHIER
}
