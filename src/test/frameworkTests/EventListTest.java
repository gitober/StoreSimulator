package frameworkTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Event;
import simu.framework.EventList;
import simu.framework.IEventType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EventListTest {

    private EventList eventList;
    private Event event;

    @BeforeEach
    void setUp() {
        eventList = new EventList();
        IEventType eventType = mock(IEventType.class);
        event = new Event(eventType, 5.0);
    }

    @Test
    void addEventIncreasesSize() {
        eventList.add(event);
        assertEquals(1, eventList.size());
    }

    @Test
    void removeEventDecreasesSize() {
        eventList.add(event);
        eventList.remove();
        assertEquals(0, eventList.size());
    }

    @Test
    void getNextTimeReturnsCorrectTime() {
        eventList.add(event);
        assertEquals(5.0, eventList.getNextTime());
    }

    @Test
    void getNextTimeReturnsInfinityWhenListIsEmpty() {
        assertEquals(Double.POSITIVE_INFINITY, eventList.getNextTime());
    }

    @Test
    void removeReturnsCorrectEvent() {
        eventList.add(event);
        assertEquals(event, eventList.remove());
    }

    @Test
    void isEmptyReturnsTrueWhenListIsEmpty() {
        assertTrue(eventList.isEmpty());
    }

    @Test
    void isEmptyReturnsFalseWhenListIsNotEmpty() {
        eventList.add(event);
        assertFalse(eventList.isEmpty());
    }
}