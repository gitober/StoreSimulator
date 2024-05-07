package db.connections.entity;

/**
 * The Customer class represents a customer in the company.
 * It encapsulates information such as the customer's first name, last name, email,
 * and loyal card number.
 */
public class Customer {
    private int id;
    private String firstName, lastName, email, loyalCardNumber;
    private int servicePointId;

    /**
     * Constructs a Customer object with the specified parameters.
     *
     * @param firstName       The first name of the customer.
     * @param lastName        The last name of the customer.
     * @param email           The email of the customer.
     * @param loyalCardNumber The loyal card number of the customer.
     */
    public Customer(String firstName, String lastName, String email, String loyalCardNumber) {
        this.id = 0;  // Default ID value
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loyalCardNumber = loyalCardNumber;
        this.servicePointId = -1;  // Default service point ID
    }

    /**
     * Retrieves the ID of the customer.
     *
     * @return The ID of the customer.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the service point ID of the customer.
     *
     * @return The service point ID of the customer.
     */
    public int getServicePointId() {
        return servicePointId;
    }

    /**
     * Retrieves the first name of the customer.
     *
     * @return The first name of the customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the customer.
     *
     * @param firstName The first name of the customer to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the customer.
     *
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the customer.
     *
     * @param lastName The last name of the customer to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email of the customer.
     *
     * @return The email of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     *
     * @param email The email of the customer to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the loyal card number of the customer.
     *
     * @return The loyal card number of the customer.
     */
    public String getLoyalCardNumber() {
        return loyalCardNumber;
    }

    /**
     * Sets the loyal card number of the customer.
     *
     * @param loyalCardNumber The loyal card number of the customer to set.
     */
    public void setLoyalCardNumber(String loyalCardNumber) {
        this.loyalCardNumber = loyalCardNumber;
    }
}
