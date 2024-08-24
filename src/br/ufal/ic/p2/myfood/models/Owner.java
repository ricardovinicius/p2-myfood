package br.ufal.ic.p2.myfood.models;

public class Owner extends User {
    private String address;
    private String cpf;

    Owner(String name, String email, String password, String address, String cpf) {
        super(name, email, password);
        this.address = address;
        this.cpf = cpf;
    }

    public static Owner create(String name, String email, String password, String address, String cpf) {
        User.create(name, email, password);

        return new Owner(name, email, password, address, cpf);
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        if (attribute.equals("endereco")) {
            return getAddress();
        }

        if (attribute.equals("cpf")) {
            return getCpf();
        }

        return null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
