package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends RepositoryBase<User> {
    public UserRepository() {
        super(User.class);
    }
    List<User> users;
    {
        try {
            users = createOrLoadPersistence();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
