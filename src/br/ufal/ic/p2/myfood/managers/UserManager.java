package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.exceptions.user.AlreadyRegistredEmailException;
import br.ufal.ic.p2.myfood.exceptions.user.InvalidLoginException;
import br.ufal.ic.p2.myfood.exceptions.user.NotRegistredUserException;
import br.ufal.ic.p2.myfood.models.Customer;
import br.ufal.ic.p2.myfood.models.Owner;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.UserRepository;

import java.io.IOException;
import java.util.Optional;

public class UserManager extends Manager {
    UserRepository userRepository = UserRepository.getInstance();

    void cleanRepository() throws IOException {
        userRepository.clean();
    }

    public String getUserAttribute(int user_id, String attribute_name) {
        Optional<User> userOptional = userRepository.getById(user_id);
        if (userOptional.isEmpty()) {
            throw new NotRegistredUserException("Usuario nao cadastrado.");
        }
        User user = userOptional.get();

        return user.getAttribute(attribute_name);
    }

    public void createCustomer(String name, String email, String password, String address) {
        Customer customer = Customer.create(name, email, password, address);

        try {
            userRepository.add(customer);
        } catch (UniqueFieldException e) {
            throw new AlreadyRegistredEmailException("Conta com esse email ja existe");
        }
    }

    public void createOwner(String name, String email, String password, String address, String cpf) {
        Owner owner = Owner.create(name, email, password, address, cpf);

        try {
            userRepository.add(owner);
        } catch (UniqueFieldException e) {
            throw new AlreadyRegistredEmailException("Conta com esse email ja existe");
        }
    }

    public int login(String email, String password) {
        Optional<User> userOptional = userRepository.getByEmail(email);

        if (userOptional.isEmpty()) {
            throw new InvalidLoginException("Login ou senha invalidos");
        }

        User user = userOptional.get();

        boolean loginSuccessful =  user.getPassword().equals(password);

        if (!loginSuccessful) {
            throw new InvalidLoginException("Login ou senha invalidos");
        }

        return user.getId();
    }


}
