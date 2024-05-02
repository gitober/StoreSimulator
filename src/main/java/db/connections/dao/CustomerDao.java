package db.connections.dao;

import db.connections.datasource.MariaDbConnection;
import db.connections.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    public List<Customer> getAllCustomers() {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT first_name, last_name, email, salary FROM customer";
        List<Customer> customers = new ArrayList<Customer>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String firstName = rs.getString(1);
                String lastName = rs.getString(2);
                String email = rs.getString(3);
                double salary = rs.getDouble(4);
                Customer customer = new Customer(firstName, lastName, email, salary);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }


    public Customer getCustomer(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "SELECT first_name, last_name, email, salary FROM customer WHERE id=?";

        String firstName = null;
        String lastName = null;
        String email = null;
        double salary = 0.0;
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
                salary = rs.getDouble(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (count==1) {
            return new Customer(firstName, lastName, email, salary);
        }
        else {
            return null;
        }
    }

    public void persist(Customer customer) {
        Connection conn = MariaDbConnection.getConnection();
        String sql = "INSERT INTO customer (first_name, last_name, email, salary) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setDouble(4, customer.getSalary());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}