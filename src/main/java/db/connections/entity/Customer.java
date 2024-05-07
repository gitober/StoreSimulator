package db.connections.entity;

/**
 * This class represents a customer in the company.
 */
public class Customer {
    private int id;
    private String firstName, lastName, email, loyalCardNumber;
    private int servicePointId;

    // Constructor with 'id' and 'servicePointId' parameters
    public Customer(String firstName, String lastName, String email, String loyalCardNumber) {
        this.id = 0;  // Default ID value
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loyalCardNumber = loyalCardNumber;
        this.servicePointId = -1;  // Default service point ID
    }

    public int getId() {
        return id;
    }

    public int getServicePointId() {
        return servicePointId;
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
