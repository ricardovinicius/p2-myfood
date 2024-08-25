package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.User;

import java.util.Optional;

public class UserRepository extends Repository<User> {
    private static UserRepository instance;
    private UserRepository() {
        super(User.class);
    }

    // Method to access the singleton instance
    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public Optional<User> getById(int id) {
        return list().stream().filter(u -> u.getId() == id).findFirst();
    }

    public Optional<User> getByEmail(String email) {
        return list().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}
