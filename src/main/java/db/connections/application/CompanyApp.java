package db.connections.application;

import db.connections.entity.Customer;
import db.connections.dao.CustomerDao;
import db.connections.datasource.MariaDbConnection;
import java.util.*;

public class CompanyApp {

    public static void main(String[] args) {

        CustomerDao empdao = new CustomerDao();

        List <Customer> employees = empdao.getAllCustomers();
        for (Customer emp : employees) {
            System.out.println(emp.getFirstName() + " " + emp.getLastName());
        }

        Customer emp = empdao.getCustomer(2);
        System.out.println(emp.getFirstName() + " " + emp.getLastName());

        empdao.persist(new Customer("Viivi", "Puro", "viivip@mymail.fi", 8300.00));

        MariaDbConnection.terminate();
    }
}