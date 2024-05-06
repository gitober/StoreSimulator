package db.connections.entity;

/**
 * This class represents a customer in the company.
 */
public class Customer {

    private int id;
    private String firstName, lastName, email, loyalCardNumber;

    // Updated constructor without the 'id' parameter
    public Customer(String firstName, String lastName, String email, String loyalCardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loyalCardNumber = loyalCardNumber;
    }

    // Getters and setters (unchanged)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoyalCardNumber() {
        return loyalCardNumber;
    }

    public void setLoyalCardNumber(String loyalCardNumber) {
        this.loyalCardNumber = loyalCardNumber;
    }
}
