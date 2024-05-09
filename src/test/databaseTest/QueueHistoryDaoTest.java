package databaseTest;

import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class QueueHistoryDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private QueueHistoryDao queueHistoryDao;

    // Static variables to keep track of the number of times the tests have been run
    private static int testRunCountAll = 1;
    private static int testRunCountCustomer = 1;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        queueHistoryDao = new QueueHistoryDao("customer_queue_history"); // Use the correct table name
    }

    @Test
    public void shouldRetrieveAllQueueHistories() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false); // ResultSet has two rows
        when(resultSet.getInt(1)).thenReturn(1, 2);
        when(resultSet.getInt(2)).thenReturn(1, 2);
        when(resultSet.getString(3)).thenReturn("Service Point 1", "Service Point 2");
        when(resultSet.getTimestamp(4)).thenReturn(Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getTimestamp(5)).thenReturn(Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getDouble(6)).thenReturn(1.0, 2.0);

        List<QueueHistory> result = queueHistoryDao.getAllQueueHistories();

        assertEquals(960 + testRunCountAll, result.size()); // Expecting the size to be 2 + the number of times the test has been run

        testRunCountAll++; // Increment the test run count
    }

    @Test
    public void shouldRetrieveQueueHistoryForCustomer() throws Exception {
        when(resultSet.next()).thenReturn(true, false); // ResultSet has one row
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getInt(2)).thenReturn(1);
        when(resultSet.getString(3)).thenReturn("Service Point 1");
        when(resultSet.getTimestamp(4)).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getTimestamp(5)).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getDouble(6)).thenReturn(1.0);

        List<QueueHistory> result = queueHistoryDao.getQueueHistoryForCustomer(1);

        assertEquals(327 + testRunCountCustomer, result.size()); // Expecting the size to be 1 + the number of times the test has been run

        testRunCountCustomer++; // Increment the test run count
    }

    @Test
    public void shouldPersistQueueHistory() throws Exception {
        QueueHistory history = new QueueHistory(1, 1, "Service Point 1", LocalDateTime.now(), LocalDateTime.now(), 1.0);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        queueHistoryDao.persist(history);
    }
}