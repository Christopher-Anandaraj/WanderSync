//added new clas due to bloater/large class smells violation in order

public class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String customerName) {
        this.name = customerName;
    }

    public String getCustomerEmail() {
        return email;
    }

    public void setCustomerEmail(String customerEmail) {
        this.email = customerEmail;
    }
}