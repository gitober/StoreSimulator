package frameworkTests;

import controller.IControllerMtoV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;
import simu.framework.Engine;
import simu.model.EventType;
import simu.model.ServicePoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EngineTest {

    private Engine engine;
    private Clock clock;
    private EventList eventList;
    private ServicePoint[] servicePoints;

    @BeforeEach
    void setUp() {
        clock = Clock.getInstance();
        eventList = Mockito.mock(EventList.class);
        servicePoints = new ServicePoint[]{Mockito.mock(ServicePoint.class)};
        IControllerMtoV controller = Mockito.mock(IControllerMtoV.class);
        engine = Mockito.spy(new Engine.ConcreteEngine(controller, 10));
        engine.setEventList(eventList);
        engine.setServicePoints(servicePoints);
    }

    @Test
    void runBEventsRemovesAndRunsEventsWithCurrentTime() {
        Event event = new Event(EventType.ARRIVAL, clock.getTime());
        when(eventList.isEmpty()).thenReturn(false, true); // Return true on the second call
        when(eventList.getNextTime()).thenReturn(clock.getTime());
        when(eventList.remove()).thenReturn(event);

        engine.runBEvents();

        verify(eventList, times(1)).remove();
        verify(engine, times(1)).runEvent(event);
    }

    @Test
    void tryCEventsBeginsServiceForUnreservedServicePointsWithQueue() {
        when(servicePoints[0].isReserved()).thenReturn(false);
        when(servicePoints[0].isOnQueue()).thenReturn(true);

        engine.tryCEvents();

        verify(servicePoints[0], times(1)).beginService();
    }

    @Test
    void simulateReturnsFalseWhenCurrentTimeExceedsSimulationTime() {
        engine.setSimulationTime(5.0);
        clock.setTime(10.0);

        boolean result = engine.simulate();

        assertEquals(false, result);
    }

    @Test
    void simulateReturnsFalseWhenNextEventTimeIsInfinity() {
        when(eventList.getNextTime()).thenReturn(Double.POSITIVE_INFINITY);

        boolean result = engine.simulate();

        assertEquals(false, result);
    }
}