package frameworkTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTest {

    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.getInstance();
    }

    @Test
    void getInstanceReturnsSameInstance() {
        Clock anotherClock = Clock.getInstance();
        assertEquals(clock, anotherClock);
    }

    @Test
    void setTimeChangesTime() {
        double newTime = 5.0;
        clock.setTime(newTime);
        assertEquals(newTime, clock.getTime());
    }

    @Test
    void getTimeReturnsSetTime() {
        double setTime = 10.0;
        clock.setTime(setTime);
        assertEquals(setTime, clock.getTime());
    }
}