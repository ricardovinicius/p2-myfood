package br.ufal.ic.p2.myfood.models;

public class User {
    private static int id_counter = 1;
    private int id;
    private String name;
    private String email;
    private String password;

    User(String name, String email, String password) {
        this.id = id_counter++;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create(String name, String email, String password) {
        return new User(name, email, password);
    }

    public boolean login(String email, String password) {
        return this.password.equals(password);
    }

    public int getId() {
        return id;
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
            return getName();
        }

        if (attribute.equals("email")) {
            return getEmail();
        }

        return null;
    }
}
