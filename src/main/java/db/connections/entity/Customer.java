package db.connections.entity;

public class Customer {

    private String firstName, lastName, email;
    private int vipCustomerNumber;

    public Customer(String firstName, String lastName, String email, int vipCustomerNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.vipCustomerNumber = vipCustomerNumber;
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

    public int getVipCustomerNumber() {
        return vipCustomerNumber;
    }

    public void setVipCustomerNumber(int vipCustomerNumber) {
        this.vipCustomerNumber = vipCustomerNumber;
    }
}
