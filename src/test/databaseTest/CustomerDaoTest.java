package databaseTest;


import db.connections.dao.CustomerDao;
import db.connections.datasource.MariaDbConnection;
import db.connections.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerDaoTest {

    private CustomerDao customerDao;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        customerDao = new CustomerDao();
        MariaDbConnection.setConnection(connection);
    }

    @Test
    public void shouldRetrieveAllCustomers() throws Exception {
        Customer customer1 = new Customer("John", "Doe", "johndoe@somemail.com", "XYZ123");
        Customer customer2 = new Customer("Jane", "Doe", "janedoe@somemail.com", "XYZ456");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("first_name")).thenReturn(customer1.getFirstName(), customer2.getFirstName());
        when(resultSet.getString("last_name")).thenReturn(customer1.getLastName(), customer2.getLastName());
        when(resultSet.getString("email")).thenReturn(customer1.getEmail(), customer2.getEmail());
        when(resultSet.getString("loyal_card_number")).thenReturn(customer1.getLoyalCardNumber(), customer2.getLoyalCardNumber());

        List<Customer> result = customerDao.getAllCustomers();

        for (int i = 0; i < customers.size(); i++) {
            assertEquals(customers.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(customers.get(i).getLastName(), result.get(i).getLastName());
            assertEquals(customers.get(i).getEmail(), result.get(i).getEmail());
            assertEquals(customers.get(i).getLoyalCardNumber(), result.get(i).getLoyalCardNumber());
        }
    }

    @Test
    public void shouldRetrieveCustomerById() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@somemail.com", "XYZ123");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("first_name")).thenReturn(customer.getFirstName());
        when(resultSet.getString("last_name")).thenReturn(customer.getLastName());
        when(resultSet.getString("email")).thenReturn(customer.getEmail());
        when(resultSet.getString("loyal_card_number")).thenReturn(customer.getLoyalCardNumber());

        Customer result = customerDao.getCustomer(1);

        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
        assertEquals(customer.getEmail(), result.getEmail());
        assertEquals(customer.getLoyalCardNumber(), result.getLoyalCardNumber());
    }

    @Test
    public void shouldReturnNullWhenCustomerNotFound() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Customer result = customerDao.getCustomer(1);

        assertEquals(null, result);
    }

    @Test
    public void shouldPersistNewCustomer() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@somemail.com", "XYZ123");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        customerDao.persist(customer);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}

