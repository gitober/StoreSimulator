package databaseTest;

import db.connections.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("John", "Doe", "john.doe@example.com", "1234567890");
    }

    @Test
    public void shouldReturnCorrectFirstName() {
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void shouldReturnCorrectLastName() {
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    public void shouldReturnCorrectEmail() {
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    public void shouldReturnCorrectLoyalCardNumber() {
        assertEquals("1234567890", customer.getLoyalCardNumber());
    }

    @Test
    public void shouldSetAndReturnCorrectFirstName() {
        customer.setFirstName("Jane");
        assertEquals("Jane", customer.getFirstName());
    }

    @Test
    public void shouldSetAndReturnCorrectLastName() {
        customer.setLastName("Smith");
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    public void shouldSetAndReturnCorrectEmail() {
        customer.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", customer.getEmail());
    }

    @Test
    public void shouldSetAndReturnCorrectLoyalCardNumber() {
        customer.setLoyalCardNumber("0987654321");
        assertEquals("0987654321", customer.getLoyalCardNumber());
    }
}
