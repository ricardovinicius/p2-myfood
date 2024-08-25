package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.utils.Validator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Customer.class, name = "customer"),
        @JsonSubTypes.Type(value = Owner.class, name = "owner")
})
public class User extends Persistent {
    private static int id_counter = 1;
    private String name;
    private String email;
    private String password;

    User(String name, String email, String password) {
        this.id = id_counter++;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public static void create(String name, String email, String password) {
        if (Validator.isNullOrEmpty(name)) {
            throw new RuntimeException("Nome invalido");
        }

        // Isso aqui é muita inconsistência da lógica de negócio :/
        if (email == null) {
            throw new RuntimeException("Email invalido");
        }

        if (!Validator.isValidEmail(email)) {
            throw new RuntimeException("Formato de email invalido");
        }

        if (Validator.isNullOrEmpty(password)) {
            throw new RuntimeException("Senha invalido");
        }
    }

    public boolean login(String email, String password) {
        return this.password.equals(password);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("nome")) {
            return name;
        }

        if (attribute.equals("email")) {
            return email;
        }

        if (attribute.equals("senha")) {
            return password;
        }

        return null;
    }

    public String getPassword() {
        return password;
    }
}
