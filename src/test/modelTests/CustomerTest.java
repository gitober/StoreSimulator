package modelTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Trace;
import simu.model.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {
    private Customer customer;

    @BeforeEach
    void setUp() {
        Trace.setTraceLevel(Trace.Level.INFO);
        customer = new Customer();
    }

    @Test
    void testGetQueueStatus() {
        Customer customer = new Customer();
        int id = customer.getId();

        String status = customer.getQueueStatus(0);
        System.out.println(status); // print the actual output
        assertTrue(status.equals("• Customer #" + id + " has no queue"));

        status = customer.getQueueStatus(1);
        System.out.println(status); // print the actual output
        assertTrue(status.equals("• Customer #" + id + " is in queue"));
    }

    @Test
    void testGetNextServicePoint() {
        int nextServicePoint = customer.getNextServicePoint();
        assertTrue(nextServicePoint >= 1 && nextServicePoint <= 4);
    }

    @Test
    void testGetServiceTime() {
        double serviceTime = customer.getServiceTime(1);
        assertEquals(0.0, serviceTime, 0.001);
    }

    @Test
    void testAddServiceTime() {
        customer.addServiceTime(1, 10.0);
        double serviceTime = customer.getServiceTime(1);
        assertEquals(10.0, serviceTime, 0.001);
    }

    @Test
    void testGetRemovalTime() {
        double removalTime = customer.getRemovalTime();
        assertEquals(0.0, removalTime, 0.001);
    }

    @Test
    void testSetRemovalTime() {
        customer.setRemovalTime(20.0);
        double removalTime = customer.getRemovalTime();
        assertEquals(20.0, removalTime, 0.001);
    }

    @Test
    void testGetArrivalTime() {
        double arrivalTime = customer.getArrivalTime();
        assertTrue(arrivalTime >= 0);
    }

    @Test
    void testSetArrivalTime() {
        customer.setArrivalTime(10.0);
        double arrivalTime = customer.getArrivalTime();
        assertEquals(10.0, arrivalTime, 0.001);
    }
}