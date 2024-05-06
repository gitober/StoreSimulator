package simu.model;

import simu.framework.IEventType;

/**
 * This enum represents the different types of events that can occur in the simulation.
 * It implements the IEventType interface.
 * The events include ARRIVAL, SERVICE_DESK, DELI_COUNTER, VEGETABLE_SECTION, and CASHIER.
 */
public enum EventType implements IEventType {
	ARRIVAL, // Represents the arrival of a customer
	SERVICE_DESK, // Represents a customer at the service desk
	DELI_COUNTER, // Represents a customer at the deli counter
	VEGETABLE_SECTION, // Represents a customer in the vegetable section
	CASHIER // Represents a customer at the cashier
}