package db.connections.dao;

import db.connections.entity.Customer;
import java.sql.*;
import db.connections.datasource.MariaDbConnection;
import java.util.*;

/**
 * This class represents the Data Access Object (DAO) for the Customer entity.
 * It provides methods to retrieve all customers, retrieve a specific customer by ID, and persist a new customer.
 */
public class CustomerDao {

    /**
     * Retrieves all customers from the database.
     * @return A list of all customers.
     */
    public List<Customer> getAllCustomers() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT first_name, last_name, email, loyal_card_number FROM customer";
        List<Customer> customers = new ArrayList<Customer>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String firstName = rs.getString(1);
                String lastName = rs.getString(2);
                String email = rs.getString(3);
                String loyal_card_number = rs.getString(4);
                Customer emp = new Customer(firstName, lastName, email, loyal_card_number);
                customers.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Retrieves a specific customer by ID from the database.
     * @param id The ID of the customer.
     * @return The customer with the given ID, or null if no such customer exists.
     */
    public Customer getCustomer(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT first_name, last_name, email, loyal_card_number FROM customer WHERE id=?";

        String firstName = null;
        String lastName = null;
        String email = null;
        String loyal_card_number = null;
        int count = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;
                firstName = rs.getString(1);
                lastName = rs.getString(2);
                email = rs.getString(3);
                loyal_card_number = rs.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (count==1) {
            return new Customer(firstName, lastName, email, loyal_card_number);
        }
        else {
            return null;
        }
    }

    /**
     * Persists a new customer to the database.
     * @param emp The customer to be persisted.
     */
    public void persist(Customer emp) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "INSERT INTO customer (first_name, last_name, email, loyal_card_number) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getloyal_card_number());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}