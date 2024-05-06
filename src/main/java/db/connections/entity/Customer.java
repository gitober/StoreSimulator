package db.connections.entity;

/**
 * This class represents a customer in the company.
 * It provides methods to get and set the first name, last name, email, and loyal card number of the customer.
 */
public class Customer {

    private String firstName, lastName, email, loyal_card_number;

    /**
     * Constructs a new Customer with the given first name, last name, email, and loyal card number.
     * @param firstName The first name of the customer.
     * @param lastName The last name of the customer.
     * @param email The email of the customer.
     * @param loyal_card_number The loyal card number of the customer.
     */
    public Customer(String firstName, String lastName, String email, String loyal_card_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loyal_card_number = loyal_card_number;
    }

    /**
     * Returns the first name of the customer.
     * @return The first name of the customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the customer.
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the customer.
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the customer.
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the email of the customer.
     * @return The email of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the loyal card number of the customer.
     * @return The loyal card number of the customer.
     */
    public String getloyal_card_number() {
        return loyal_card_number;
    }

    /**
     * Sets the loyal card number of the customer.
     * @param loyal_card_number The loyal card number to set.
     */
    public void setloyal_card_number(String loyal_card_number) {
        this.loyal_card_number = loyal_card_number;
    }
}