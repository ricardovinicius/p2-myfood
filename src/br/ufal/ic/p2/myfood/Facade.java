package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;

import java.io.IOException;
import java.util.List;

public class Facade {
    UserRepository userRepository = new UserRepository();
    CompanyRepository companyRepository = new CompanyRepository();
    private final List<User> users = userRepository.list();

    public void zerarSistema() throws IOException {
        userRepository.clean();
    }

    public String getAtributoUsuario(int id, String nome) {
        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new RuntimeException("Usuario nao cadastrado.");
        }

        return user.getAttribute(nome);
    }

    private void checkIfUserExists(String email) {
        User existent_user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);

        if (existent_user != null) {
            throw new RuntimeException("Conta com esse email ja existe");
        }
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco) {
        Customer customer = Customer.create(nome, email, senha, endereco);

        checkIfUserExists(email);

        userRepository.add(customer);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String cpf) {
        Owner owner = Owner.create(nome, email, senha, endereco, cpf);

        checkIfUserExists(email);

        userRepository.add(owner);
    }

    public int login(String email, String senha) {
        User user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);

        if (user == null) {
            throw new RuntimeException("Login ou senha invalidos");
        }

        boolean loginSuccessful = user.login(email, senha);

        if (!loginSuccessful) {
            throw new RuntimeException("Login ou senha invalidos");
        }

        return user.getId();
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        if (tipoEmpresa.equals("restaurante")) {
            Restaurant restaurant = Restaurant.create(nome, endereco, tipoCozinha, dono);

            try {
                companyRepository.add(restaurant);
            } catch (UniqueFieldException e) {
                throw new RuntimeException("Empresa com esse nome ja existe");
            }


            return restaurant.getId();
        }

        return 0;
    }

    public String getEmpresasDoUsuario(int idDono) {
        return "";
    }

    public String getAtributoEmpresa(int empresa, String atributo) {
        return "";
    }

    public void encerrarSistema() throws IOException {
        userRepository.save();
        companyRepository.save();
    }

}
