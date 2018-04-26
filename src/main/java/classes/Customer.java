package classes;

/**
 * Created by Piotr on 06.03.2018.
 */
public class Customer {

    public Customer(String firstName, String lastName, String address, String city, int postCode, String email, int telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.email = email;
        this.telephone = telephone;
    }

    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int postCode;
    private String email;
    private int telephone;

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    public int getTelephone() {
        return telephone;
    }
}
