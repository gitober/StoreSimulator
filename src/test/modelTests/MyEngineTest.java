package modelTests;

import controller.IControllerMtoV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.ArrivalProcess;
import simu.framework.Event;
import simu.model.ArrivalPattern;
import simu.model.EventType;
import simu.model.MyEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyEngineTest {
    private MyEngine myEngine;
    private IControllerMtoV controller;

    @BeforeEach
    public void setup() {
        controller = mock(IControllerMtoV.class);
        myEngine = new MyEngine(controller, 10);
    }

    @Test
    public void initializationGeneratesCorrectNumberOfArrivalEvents() {
        myEngine.initialization();
        assertEquals(10, myEngine.getEventList().size());
    }

    @Test
    public void runEventHandlesArrivalEventCorrectly() {
        Event arrivalEvent = new Event(EventType.ARRIVAL, 0);
        myEngine.runEvent(arrivalEvent);
        assertEquals(1, myEngine.getCustomers().size());
    }

    @Test
    public void runEventHandlesServiceDeskEventCorrectly() {
        Event serviceDeskEvent = new Event(EventType.SERVICE_DESK, 0);
        myEngine.runEvent(serviceDeskEvent);
        assertEquals(0, myEngine.getServicePoints()[0].getQueueLength());
    }

    @Test
    public void setArrivalPatternChangesArrivalProcessCorrectly() {
        myEngine.setArrivalPattern(ArrivalPattern.MORNINGRUSH);
        assertTrue(myEngine.getArrivalProcess() instanceof ArrivalProcess);
    }

    @Test
    public void runEventDoesNotAddCustomerWhenMaxCustomersReached() {
        for (int i = 0; i < 10; i++) {
            myEngine.runEvent(new Event(EventType.ARRIVAL, 0));
        }
        myEngine.runEvent(new Event(EventType.ARRIVAL, 0));
        assertEquals(10, myEngine.getCustomers().size());
    }

    @Test
    public void runEventHandlesServiceDeskEventWhenNoCustomersInQueue() {
        Event serviceDeskEvent = new Event(EventType.SERVICE_DESK, 0);
        myEngine.runEvent(serviceDeskEvent);
        assertEquals(0, myEngine.getServicePoints()[0].getQueueLength());
    }
}