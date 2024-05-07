package db.connections.dao;

import db.connections.datasource.MariaDbConnection;
import db.connections.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CustomerDao class represents the Data Access Object (DAO) for the Customer entity.
 * It provides methods to interact with the database to perform CRUD operations on customer data.
 */
public class CustomerDao {

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of all customers.
     */
    public List<Customer> getAllCustomers() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT id, first_name, last_name, email, loyal_card_number FROM customer";
        List<Customer> customers = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String loyalCardNumber = rs.getString("loyal_card_number");
                Customer customer = new Customer(firstName, lastName, email, loyalCardNumber);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Retrieves a specific customer by ID from the database.
     *
     * @param id The ID of the customer.
     * @return The customer with the given ID, or null if no such customer exists.
     */
    public Customer getCustomer(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT id, first_name, last_name, email, loyal_card_number FROM customer WHERE id=?";

        Customer customer = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String loyalCardNumber = rs.getString("loyal_card_number");
                customer = new Customer(firstName, lastName, email, loyalCardNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    /**
     * Persists a new customer to the database.
     *
     * @param customer The customer to be persisted.
     */
    public void persist(Customer customer) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "INSERT INTO customer (id, first_name, last_name, email, loyal_card_number) VALUES (?, ?, ?, ?, ?)";
        try {
            // Get the next available ID
            int nextId = getNextAvailableId(conn);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nextId);
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getLoyalCardNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the next available ID for a new customer.
     *
     * @param conn The database connection.
     * @return The next available ID.
     * @throws SQLException If a database access error occurs.
     */
    private int getNextAvailableId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id) FROM customer";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1; // If no customers exist, start from ID 1
        }
    }
}
