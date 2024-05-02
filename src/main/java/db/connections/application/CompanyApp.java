package db.connections.application;

import db.connections.dao.CustomerDao;
import db.connections.datasource.MariaDbConnection;
import db.connections.entity.Customer;

import java.util.List;

public class CompanyApp {

    public static void main(String[] args) {

        CustomerDao customerDao = new CustomerDao();

        // Get all customers and print their names
        List<Customer> customers = customerDao.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        }

        // Get customer by ID and print their name
        Customer customerById = customerDao.getCustomer(2);
        if (customerById != null) {
            System.out.println(customerById.getFirstName() + " " + customerById.getLastName());
        }

        // Add a new customer
        Customer newCustomer = new Customer("Viivi", "Puro", "viivip@mymail.fi", 8300);
        customerDao.persist(newCustomer);

        // Delete a customer by ID
        customerDao.deleteCustomer(3);

        // Terminate the database connection
        MariaDbConnection.terminate();
    }
}
