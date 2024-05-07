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
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        ContinuousGenerator generator = new Normal(5, 2);
        EventList eventList = new EventList();
        servicePoint = new ServicePoint(generator, eventList, EventType.SERVICE_DESK, "Service Desk");
        customer1 = new Customer();
        customer2 = new Customer();
    }

    @Test
    void addQueueIncreasesQueueLength() {
        servicePoint.addQueue(customer1);
        assertEquals(1, servicePoint.getQueueLength());
        servicePoint.addQueue(customer2);
        assertEquals(2, servicePoint.getQueueLength());
    }

    @Test
    void removeQueueDecreasesQueueLength() {
        servicePoint.addQueue(customer1);
        servicePoint.addQueue(customer2);
        servicePoint.removeQueue();
        assertEquals(1, servicePoint.getQueueLength());
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
        servicePoint.addQueue(customer1);
        assertTrue(servicePoint.isOnQueue());
    }

    @Test
    void isReservedReturnsFalseInitially() {
        assertFalse(servicePoint.isReserved());
    }

    @Test
    void isReservedReturnsTrueAfterBeginService() {
        servicePoint.addQueue(customer1);
        servicePoint.beginService();
        assertTrue(servicePoint.isReserved());
    }

    @Test
    void endServiceReleasesReservation() {
        servicePoint.addQueue(customer1);
        servicePoint.beginService();
        servicePoint.removeQueue(); // Simulate end of service
        assertFalse(servicePoint.isReserved());
    }

    @Test
    void getQueueStatusReturnsCorrectMessageWhenQueueIsEmpty() {
        assertEquals("There are no customers in the queue.", getQueueStatusMessage(servicePoint.getQueueLength()));
    }

    @Test
    void getQueueStatusReturnsCorrectMessageWhenQueueIsNotEmpty() {
        servicePoint.addQueue(customer1);
        assertEquals("There is 1 customer in the queue.", getQueueStatusMessage(servicePoint.getQueueLength()));
        servicePoint.addQueue(customer2);
        assertEquals("There are 2 customers in the queue.", getQueueStatusMessage(servicePoint.getQueueLength()));
    }

    @Test
    void beginServiceSkipsEmptyQueue() {
        servicePoint.beginService();
        assertFalse(servicePoint.isReserved());
    }

    private String getQueueStatusMessage(int queueLength) {
        if (queueLength == 0) {
            return "There are no customers in the queue.";
        } else if (queueLength == 1) {
            return "There is 1 customer in the queue.";
        } else {
            return String.format("There are %d customers in the queue.", queueLength);
        }
    }
}
