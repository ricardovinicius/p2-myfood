package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.Repository;
import br.ufal.ic.p2.myfood.utils.Validator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Facade {
    Repository<User> userRepository = Repository.getInstance(User.class);
    Repository<Company> companyRepository = Repository.getInstance(Company.class);
    private final List<User> users = userRepository.list();

    public void zerarSistema() throws IOException {
        userRepository.clean();
        companyRepository.clean();
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

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco) {
        Customer customer = Customer.create(nome, email, senha, endereco);

        try {
            userRepository.add(customer);
        } catch (UniqueFieldException e) {
            throw new RuntimeException("Conta com esse email ja existe");
        }

    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String cpf) {
        Owner owner = Owner.create(nome, email, senha, endereco, cpf);

        try {
            userRepository.add(owner);
        } catch (UniqueFieldException e) {
            throw new RuntimeException("Conta com esse email ja existe");
        }
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
            User user = userRepository.list()
                    .stream()
                    .filter(u -> u.getId() == dono)
                    .findFirst()
                    .orElse(null);

            if (!(user instanceof Owner)) {
                throw new RuntimeException("Usuario nao pode criar uma empresa");
            }

            Company sameNameRestaurant = companyRepository.list()
                    .stream()
                    .filter(c -> c.getName().equals(nome))
                    .findFirst()
                    .orElse(null);

            if (sameNameRestaurant != null) {
                if (sameNameRestaurant.getOwnerId() != dono) {
                    throw new RuntimeException("Empresa com esse nome ja existe");
                }

                System.out.println(sameNameRestaurant.getAddress());
                System.out.println(endereco);
                System.out.println("____");

                if (sameNameRestaurant.getAddress().equals(endereco)) {
                    throw new RuntimeException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }

            Restaurant restaurant = Restaurant.create(nome, endereco, tipoCozinha, dono);
            companyRepository.add(restaurant);

            return restaurant.getId();
        }

        return 0;
    }

    public String getEmpresasDoUsuario(int idDono) {
        User user = userRepository.list()
                .stream()
                .filter(u -> u.getId() == idDono)
                .findFirst()
                .orElse(null);

        if (!(user instanceof Owner)) {
            throw new RuntimeException("Usuario nao pode criar uma empresa");
        }

        List<Company> companies = companyRepository.list()
                .stream()
                .filter(c -> c.getOwnerId() == idDono)
                .toList();

        return "{" + companies.toString() + "}";
    }

    public int getIdEmpresa(int idDono, String nome, String indice) {
        int intIndice;

        if (indice == null) {
            throw new RuntimeException("Indice invalido");
        } else {
            intIndice = Integer.parseInt(indice);
        }

        if (Validator.isNullOrEmpty(nome)) {
            throw new RuntimeException("Nome invalido");
        }

        List<Company> companies = companyRepository.list()
                .stream()
                .filter(c -> c.getOwnerId() == idDono)
                .filter(c -> c.getName().equals(nome))
                .toList();

        if (companies.isEmpty()) {
            throw new RuntimeException("Nao existe empresa com esse nome");
        }

        Company company;
        try {
            company = companies.get(intIndice);
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("Indice maior que o esperado");
        }

        return company.getId();
    }

    public String getAtributoEmpresa(int empresa, String atributo) {
        Company company = companyRepository.list()
                .stream()
                .filter(c -> c.getId() == empresa)
                .findFirst()
                .orElse(null);

        if (atributo == null) {
            return "Atributo invalido";
        }

        if (company == null) {
            throw new RuntimeException("Empresa nao cadastrada");
        }

        if (company instanceof Restaurant) {
            company = (Restaurant) company;
        }

        try {
            String attribute = company.getAttribute(atributo);

            return attribute;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Atributo invalido");
        }
    }

    public void encerrarSistema() throws IOException {
        userRepository.save();
        companyRepository.save();
    }

}
