package db.connections.application;
import db.connections.dao.CustomerDao;
import db.connections.entity.Customer;
import java.util.List;

public class CompanyApp {

    public static void main(String[] args) {

        CustomerDao empdao = new CustomerDao();

        List<Customer> customers = empdao.getAllCustomers();
        System.out.println("Employee Details:");
        int index = 1;
        for (Customer emp : customers) {
            System.out.println("Customer " + index++ + ":");
            System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Loyal card number: " + emp.getloyal_card_number());
            System.out.println();
        }

        Customer emp = empdao.getCustomer(2);
        if (emp != null) {
            System.out.println("Customer Details for ID 2:");
            System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Loyal card number: " + emp.getloyal_card_number());
        } else {
            System.out.println("Customer with ID 2 not found.");
        }

        // Add a new employee
        empdao.persist(new Customer("John", "Smith", "johnsmith@somemail.com", "ABC123"));

        // Retrieve and print all employees after addition
        customers = empdao.getAllCustomers();
        System.out.println("\nCustomer Details after addition:");
        index = 1;
        for (Customer e : customers) {
            System.out.println("Customer " + index++ + ":");
            System.out.println("Name: " + e.getFirstName() + " " + e.getLastName());
            System.out.println("Email: " + e.getEmail());
            System.out.println("Loyal card number: " + e.getloyal_card_number());
            System.out.println();
        }

        // Terminate connection
        db.connections.datasource.MariaDbConnection.terminate();
    }
}
