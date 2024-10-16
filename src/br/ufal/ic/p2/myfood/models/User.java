package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.exceptions.user.InvalidUserNameException;
import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.types.Unique;
import br.ufal.ic.p2.myfood.utils.Validators;
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
public abstract class User extends Persistent {
    private static int id_counter = 1;
    private String name;
    private Unique<String> email;
    private String password;
    private String dtype;

    User(String name, String email, String password, String dtype) {
        this.id = id_counter++;
        this.name = name;
        this.email = new Unique(email);
        this.password = password;
        this.dtype = dtype;
    }

    public User() {
    }

    static void create(String name, String email, String password) {
        if (Validators.isNullOrEmpty(name)) {
            throw new InvalidUserNameException("Nome invalido");
        }

        if (email == null) {
            throw new RuntimeException("Email invalido");
        }

        if (!Validators.isValidEmail(email)) {
            throw new RuntimeException("Email invalido");
        }

        if (Validators.isNullOrEmpty(password)) {
            throw new RuntimeException("Senha invalido");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("nome")) {
            return name;
        }

        if (attribute.equals("email")) {
            return email.getValue();
        }

        if (attribute.equals("senha")) {
            return password;
        }

        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getDtype() {
        return dtype;
    }
}
