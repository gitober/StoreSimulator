package db.connections.dao;

import db.connections.entity.QueueHistory;
import db.connections.datasource.MariaDbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Data Access Object (DAO) for queue history.
 */
public class QueueHistoryDao {

    private String tableName;

    public QueueHistoryDao(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Retrieves all queue histories from the database.
     * @return A list of all queue histories.
     */
    public List<QueueHistory> getAllQueueHistories() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = String.format("SELECT id, customer_id, service_point_name, arrival_time, departure_time, queue_time FROM `%s`", tableName);
        List<QueueHistory> histories = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt(1);
                int customerId = rs.getInt(2);
                String servicePointName = rs.getString(3);
                LocalDateTime arrivalTime = rs.getTimestamp(4).toLocalDateTime();
                LocalDateTime departureTime = rs.getTimestamp(5).toLocalDateTime();
                double queueTime = rs.getDouble(6);
                QueueHistory history = new QueueHistory(id, customerId, servicePointName, arrivalTime, departureTime, queueTime);
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return histories;
    }

    /**
     * Retrieves the queue history for a specific customer ID from the database.
     * @param customerId The ID of the customer.
     * @return A list of queue histories for the given customer ID.
     */
    public List<QueueHistory> getQueueHistoryForCustomer(int customerId) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = String.format("SELECT id, customer_id, service_point_name, arrival_time, departure_time, queue_time FROM `%s` WHERE customer_id=?", tableName);
        List<QueueHistory> histories = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String servicePointName = rs.getString(3);
                LocalDateTime arrivalTime = rs.getTimestamp(4).toLocalDateTime();
                LocalDateTime departureTime = rs.getTimestamp(5).toLocalDateTime();
                double queueTime = rs.getDouble(6);
                QueueHistory history = new QueueHistory(id, customerId, servicePointName, arrivalTime, departureTime, queueTime);
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return histories;
    }

    /**
     * Persists a new queue history to the database.
     * @param history The queue history to be persisted.
     */
    public void persist(QueueHistory history) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = String.format("INSERT INTO `%s` (customer_id, service_point_name, arrival_time, departure_time, queue_time) VALUES (?, ?, ?, ?, ?)", tableName);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, history.getCustomerId());
            ps.setString(2, history.getServicePointName());
            ps.setTimestamp(3, Timestamp.valueOf(history.getArrivalTime()));
            ps.setTimestamp(4, Timestamp.valueOf(history.getDepartureTime()));
            ps.setDouble(5, history.getQueueTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
