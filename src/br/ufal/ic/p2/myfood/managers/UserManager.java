package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.exceptions.user.AlreadyRegistredEmailException;
import br.ufal.ic.p2.myfood.exceptions.user.InvalidLoginException;
import br.ufal.ic.p2.myfood.exceptions.user.NotRegistredUserException;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.Repository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.validators.CompanyValidators;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserManager extends Manager {
    UserRepository userRepository = UserRepository.getInstance();

    void cleanRepository() throws IOException {
        userRepository.clean();
    }

    void saveRepository() throws IOException {
        userRepository.save();
    }

    public String getUserAttribute(int user_id, String attribute_name) {
        Optional<User> userOptional = userRepository.getById(user_id);
        if (userOptional.isEmpty()) {
            throw new NotRegistredUserException("Usuario nao cadastrado.");
        }
        User user = userOptional.get();

        return user.getAttribute(attribute_name);
    }

    public void createUser(String userType, String name, String email, String password, String address, String cpf, String veiculo, String placa) {
        User user;

        if (userType.equals("owner")) {
            user = Owner.create(name, email, password, address, cpf);
        } else if (userType.equals("delivery")) {
            user = Delivery.create(name, email, password, address, veiculo, placa);
        } else {
            user = Customer.create(name, email, password, address);
        }

        try {
            userRepository.add(user);
        } catch (UniqueFieldException e) {
            if (e.getField().getName().equals("email")) {
                throw new AlreadyRegistredEmailException("Conta com esse email ja existe");
            }
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


    public String listDeliveryCompanies(int deliveryId) {
        Optional<User> userOptional = userRepository.getById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new NotRegistredUserException("Usuario nao cadastrado.");
        }
        User user = userOptional.get();

        CompanyValidators.userCanDelivery(user);

        CompanyRepository companyRepository = CompanyRepository.getInstance();
        List<Company> companyList = companyRepository.list().stream()
                .filter(company -> company.getDeliveryList().contains(user)).toList();

        return "{" + companyList + "}";
    }
}
