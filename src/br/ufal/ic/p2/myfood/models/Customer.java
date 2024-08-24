package br.ufal.ic.p2.myfood.models;

public class Customer extends User {
    private String address;

    Customer(String name, String email, String password, String address) {
        super(name, email, password);
        this.address = address;
    }

    public static Customer create(String name, String email, String password, String address) {
        User.create(name, email, password);

        return new Customer(name, email, password, address);
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        if (attribute.equals("endereco")) {
            return getAddress();
        }

        return null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
