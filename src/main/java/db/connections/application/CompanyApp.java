package db.connections.application;

import db.connections.entity.Customer;
import db.connections.dao.CustomerDao;
import db.connections.datasource.MariaDbConnection;
import java.util.*;

public class CompanyApp {

    public static void main(String[] args) {

        CustomerDao customerdao = new CustomerDao();

        List <Customer> customers = customerdao.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        }

        Customer customer = customerdao.getCustomer(2);
        System.out.println(customer.getFirstName() + " " + customer.getLastName());

        customerdao.persist(new Customer("Viivi", "Puro", "viivip@mymail.fi", 8300.00));

        MariaDbConnection.terminate();
    }
}