package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.utils.Validator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("owner")
public class Owner extends User {
    private String address;
    private String cpf;

    Owner() {}

    Owner(String name, String email, String password, String address, String cpf) {
        super(name, email, password);
        this.address = address;
        this.cpf = cpf;
    }

    public static Owner create(String name, String email, String password, String address, String cpf) {
        User.create(name, email, password);

        if (Validator.isNullOrEmpty(cpf)) {
            throw new RuntimeException("CPF invalido");
        }

        if (Validator.isNullOrEmpty(address)) {
            throw new RuntimeException("Endereco invalido");
        }

        if (cpf.length() != 14) {
            throw new RuntimeException("CPF invalido");
        }



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
