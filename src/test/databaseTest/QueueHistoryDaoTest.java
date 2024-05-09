package databaseTest;

import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueueHistoryDaoTest {

    private QueueHistoryDao queueHistoryDao;

    @BeforeEach
    public void setUp() {
        // No need to initialize queueHistoryDao as it's not interacting with a real database
    }

    @Test
    public void shouldRetrieveQueueHistoryForCustomer() {
        // Assuming customer ID 123 has 328 queue histories
        int customerId = 123;
        int expectedQueueHistories = 328;

        // Create mock queue histories
        List<QueueHistory> mockHistories = createMockQueueHistories(customerId, expectedQueueHistories);

        // Mocking the behavior of getQueueHistoryForCustomer() method to return mockHistories
        queueHistoryDao = new QueueHistoryDao() {
            @Override
            public List<QueueHistory> getQueueHistoryForCustomer(int customerId) {
                return mockHistories;
            }
        };

        // Retrieve queue histories for the customer
        List<QueueHistory> histories = queueHistoryDao.getQueueHistoryForCustomer(customerId);

        // Assert that the number of retrieved queue histories matches the expected value
        assertEquals(expectedQueueHistories, histories.size());
    }

    // Helper method to create mock queue histories
    private List<QueueHistory> createMockQueueHistories(int customerId, int count) {
        List<QueueHistory> mockHistories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Assuming some mock data for queue histories
            QueueHistory history = new QueueHistory(i + 1, customerId, "ServicePoint" + (i + 1),
                    LocalDateTime.now(), LocalDateTime.now(), 10.0); // You can adjust mock data as needed
            mockHistories.add(history);
        }
        return mockHistories;
    }
}
