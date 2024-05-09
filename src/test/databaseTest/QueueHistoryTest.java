package databaseTest;

import db.connections.entity.QueueHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QueueHistoryTest {

    private QueueHistory queueHistory;
    private LocalDateTime now;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        queueHistory = new QueueHistory(1, 1, "Service Point 1", now, now, 1.0);
    }

    @Test
    public void shouldReturnCorrectId() {
        assertEquals(1, queueHistory.getId());
    }

    @Test
    public void shouldReturnCorrectCustomerId() {
        assertEquals(1, queueHistory.getCustomerId());
    }

    @Test
    public void shouldReturnCorrectServicePointName() {
        assertEquals("Service Point 1", queueHistory.getServicePointName());
    }

    @Test
    public void shouldReturnCorrectArrivalTime() {
        assertEquals(now, queueHistory.getArrivalTime());
    }

    @Test
    public void shouldReturnCorrectDepartureTime() {
        assertEquals(now, queueHistory.getDepartureTime());
    }

    @Test
    public void shouldReturnCorrectQueueTime() {
        assertEquals(1.0, queueHistory.getQueueTime());
    }

    @Test
    public void shouldSetAndReturnCorrectId() {
        queueHistory.setId(2);
        assertEquals(2, queueHistory.getId());
    }

    @Test
    public void shouldSetAndReturnCorrectCustomerId() {
        queueHistory.setCustomerId(2);
        assertEquals(2, queueHistory.getCustomerId());
    }

    @Test
    public void shouldSetAndReturnCorrectServicePointName() {
        queueHistory.setServicePointName("Service Point 2");
        assertEquals("Service Point 2", queueHistory.getServicePointName());
    }

    @Test
    public void shouldSetAndReturnCorrectArrivalTime() {
        LocalDateTime newTime = LocalDateTime.now();
        queueHistory.setArrivalTime(newTime);
        assertEquals(newTime, queueHistory.getArrivalTime());
    }

    @Test
    public void shouldSetAndReturnCorrectDepartureTime() {
        LocalDateTime newTime = LocalDateTime.now();
        queueHistory.setDepartureTime(newTime);
        assertEquals(newTime, queueHistory.getDepartureTime());
    }

    @Test
    public void shouldSetAndReturnCorrectQueueTime() {
        queueHistory.setQueueTime(2.0);
        assertEquals(2.0, queueHistory.getQueueTime());
    }

    @Test
    public void shouldNotReturnIncorrectId() {
        assertNotEquals(2, queueHistory.getId());
    }

    @Test
    public void shouldNotReturnIncorrectCustomerId() {
        assertNotEquals(2, queueHistory.getCustomerId());
    }

    @Test
    public void shouldNotReturnIncorrectServicePointName() {
        assertNotEquals("Service Point 2", queueHistory.getServicePointName());
    }

    @Test
    public void shouldNotReturnIncorrectArrivalTime() {
        LocalDateTime differentTime = now.plusSeconds(1);
        assertNotEquals(differentTime, queueHistory.getArrivalTime());
    }

    @Test
    public void shouldNotReturnIncorrectDepartureTime() {
        // Set the departure time to a different time from the current time
        LocalDateTime differentTime = now.plusSeconds(1);
        queueHistory.setDepartureTime(differentTime);

        // Assert that the departure time is not equal to the current time
        assertNotEquals(LocalDateTime.now(), queueHistory.getDepartureTime());
    }

    @Test
    public void shouldNotReturnIncorrectQueueTime() {
        assertNotEquals(2.0, queueHistory.getQueueTime());
    }
}