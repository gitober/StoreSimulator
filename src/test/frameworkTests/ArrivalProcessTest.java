package frameworkTests;

import eduni.distributions.ContinuousGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.EventList;
import simu.model.EventType;

class ArrivalProcessTest {

    private ArrivalProcess arrivalProcess;
    private EventList eventList;
    private ContinuousGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new Normal(5, 2);
        eventList = new EventList();
        arrivalProcess = new ArrivalProcess(generator, eventList, EventType.ARRIVAL, 10);
    }

    @Test
    void generateNextAddsEventToEventList() {
        arrivalProcess.generateNext();
        assertEquals(1, eventList.size());
    }

    @Test
    void generateNextDoesNotAddEventWhenMaxCustomersReached() {
        for (int i = 0; i < 10; i++) {
            arrivalProcess.generateNext();
        }
        assertEquals(10, eventList.size());
        arrivalProcess.generateNext();
        assertEquals(10, eventList.size());
    }

    @Test
    void generateNextAddsArrivalEventToEventList() {
        arrivalProcess.generateNext();
        assertFalse(eventList.isEmpty());
    }
}