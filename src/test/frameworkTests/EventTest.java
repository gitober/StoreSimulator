package frameworkTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Event;
import simu.framework.IEventType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class EventTest {

    private Event event;
    private IEventType eventType;

    @BeforeEach
    void setUp() {
        eventType = mock(IEventType.class);
        event = new Event(eventType, 5.0);
    }

    @Test
    void setTypeChangesEventType() {
        IEventType newType = mock(IEventType.class);
        event.setType(newType);
        assertEquals(newType, event.getType());
    }

    @Test
    void getTimeReturnsCorrectTime() {
        assertEquals(5.0, event.getTime());
    }

    @Test
    void setTimeChangesTime() {
        event.setTime(10.0);
        assertEquals(10.0, event.getTime());
    }

    @Test
    void compareToReturnsNegativeWhenEventTimeIsLess() {
        Event otherEvent = new Event(eventType, 10.0);
        assertEquals(-1, event.compareTo(otherEvent));
    }

    @Test
    void compareToReturnsPositiveWhenEventTimeIsMore() {
        Event otherEvent = new Event(eventType, 2.0);
        assertEquals(1, event.compareTo(otherEvent));
    }

    @Test
    void compareToReturnsZeroWhenEventTimeIsEqual() {
        Event otherEvent = new Event(eventType, 5.0);
        assertEquals(0, event.compareTo(otherEvent));
    }
}