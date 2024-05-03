package db.connections.entity;
public class Customer {

    private String firstName, lastName, email, loyal_card_number;

    public Customer(String firstName, String lastName, String email, String loyal_card_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loyal_card_number = loyal_card_number;
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

    public String getloyal_card_number() {
        return loyal_card_number;
    }

    public void setloyal_card_number(String loyal_card_number) {
        this.loyal_card_number = loyal_card_number;
    }
}