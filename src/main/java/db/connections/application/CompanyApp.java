package db.connections.application;

import db.connections.dao.CustomerDao;
import db.connections.entity.Customer;
import java.util.List;

/**
 * The CompanyApp class represents the main application for managing customers in a company.
 * It provides methods to retrieve all customers, retrieve a specific customer by ID,
 * add a new customer, and terminate the database connection.
 */
public class CompanyApp {

    /**
     * The main method for the application.
     * This method initializes the application, retrieves customer data from the database,
     * performs operations such as printing customer details and adding a new customer,
     * and terminates the database connection.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Create a new CustomerDao object
        CustomerDao empdao = new CustomerDao();

        // Retrieve and print all customers
        List<Customer> customers = empdao.getAllCustomers();
        System.out.println("Employee Details:");
        int index = 1;
        for (Customer emp : customers) {
            System.out.println("Customer " + index++ + ":");
            System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Loyal card number: " + emp.getLoyalCardNumber());
            System.out.println();
        }

        // Retrieve and print the customer with ID 2
        Customer emp = empdao.getCustomer(2);
        if (emp != null) {
            System.out.println("Customer Details for ID 2:");
            System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Loyal card number: " + emp.getLoyalCardNumber());
        } else {
            System.out.println("Customer with ID 2 not found.");
        }

        // Add a new customer
        empdao.persist(new Customer("John", "Smith", "johnsmith@somemail.com", "ABC123"));

        // Retrieve and print all customers after the addition
        customers = empdao.getAllCustomers();
        System.out.println("\nCustomer Details after addition:");
        index = 1;
        for (Customer e : customers) {
            System.out.println("Customer " + index++ + ":");
            System.out.println("Name: " + e.getFirstName() + " " + e.getLastName());
            System.out.println("Email: " + e.getEmail());
            System.out.println("Loyal card number: " + e.getLoyalCardNumber()); // Use variable 'e' instead of 'emp'
            System.out.println();
        }

        // Terminate the database connection
        db.connections.datasource.MariaDbConnection.terminate();
    }
}
