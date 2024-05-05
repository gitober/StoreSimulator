package modelTests;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.EventList;
import simu.model.Customer;
import simu.model.EventType;
import simu.model.ServicePoint;

import static org.junit.jupiter.api.Assertions.*;

class ServicePointTest {

    private ServicePoint servicePoint;
    private Customer customer;

    @BeforeEach
    void setUp() {
        ContinuousGenerator generator = new Normal(5, 2);
        EventList eventList = new EventList();
        servicePoint = new ServicePoint(generator, eventList, EventType.SERVICE_DESK);
        customer = new Customer();
    }

    @Test
    void addQueueIncreasesQueueLength() {
        servicePoint.addQueue(customer);
        assertEquals(1, servicePoint.getQueueLength());
    }

    @Test
    void removeQueueDecreasesQueueLength() {
        servicePoint.addQueue(customer);
        servicePoint.removeQueue();
        assertEquals(0, servicePoint.getQueueLength());
    }

    @Test
    void removeQueueFromEmptyQueueReturnsNull() {
        assertNull(servicePoint.removeQueue());
    }

    @Test
    void isOnQueueReturnsFalseWhenQueueIsEmpty() {
        assertFalse(servicePoint.isOnQueue());
    }

    @Test
    void isOnQueueReturnsTrueWhenQueueIsNotEmpty() {
        servicePoint.addQueue(customer);
        assertTrue(servicePoint.isOnQueue());
    }

    @Test
    void isReservedReturnsFalseInitially() {
        assertFalse(servicePoint.isReserved());
    }

    @Test
    void isReservedReturnsTrueAfterBeginService() {
        servicePoint.addQueue(customer);
        servicePoint.beginService();
        assertTrue(servicePoint.isReserved());
    }

    @Test
    void getQueueStatusReturnsCorrectMessageWhenQueueIsEmpty() {
        assertEquals("There are no customers in the queue.", servicePoint.getQueueStatus());
    }

    @Test
    void getQueueStatusReturnsCorrectMessageWhenQueueIsNotEmpty() {
        servicePoint.addQueue(customer);
        assertEquals("There are 1 customers in the queue.", servicePoint.getQueueStatus());
    }
}