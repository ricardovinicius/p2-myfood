package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;
import br.ufal.ic.p2.myfood.exceptions.NotFoundObjectException;
import br.ufal.ic.p2.myfood.exceptions.company.*;
import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.Restaurant;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.utils.Validators;
import br.ufal.ic.p2.myfood.validators.CompanyValidators;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CompanyManager extends Manager {
    private CompanyRepository companyRepository = CompanyRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();

    public CompanyManager() {}

    public void cleanRepository() throws IOException {
        companyRepository.clean();
    }

    public void saveRepository() throws IOException {
        companyRepository.save();
    }

    public int createCompany(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        Optional<User> userOptional = userRepository.getById(dono);

        if (userOptional.isEmpty()) {
            return -1;
        }

        User user = userOptional.get();

        CompanyValidators.userCanCreateCompany(user);

        if (tipoEmpresa.equals("restaurante")) {
            CompanyValidators.sameNameRestaurant(nome, user, endereco);
            Restaurant restaurant = Restaurant.create(nome, endereco, tipoCozinha, user);

            companyRepository.add(restaurant);

            return restaurant.getId();
        }

        return -1;
    }

    public String listCompanyByOwnerId(int ownerId) {
        Optional<User> userOptional = userRepository.getById(ownerId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        CompanyValidators.userCanCreateCompany(user);

        List<Company> companies = companyRepository.listByOwnerId(ownerId);

        // TODO: Do this String format in a better way
        return "{" + companies.toString() + "}";
    }

    public int getCompanyId(int ownerId, String companyName, String listIndex) {
        int index = Validators.parseIndex(listIndex);

        if (Validators.isNullOrEmpty(companyName)) {
            throw new InvalidCompanyNameException("Nome invalido");
        }

        List<Company> companies = companyRepository.listByOwnerId(ownerId);
        List<Company> companiesWithSearchedName = companies.stream()
                .filter(c -> c.getName().equals(companyName))
                .toList();

        if (companiesWithSearchedName.isEmpty()) {
            throw new NotFoundObjectException("Nao existe empresa com esse nome");
        }

        Company company;
        try {
            company = companiesWithSearchedName.get(index);
        } catch(IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Indice maior que o esperado");
        }

        return company.getId();
    }

    public String getCompanyAttribute(int companyId, String attributeName) {
        Optional<Company> companyOptional = companyRepository.getById(companyId);
        if (companyOptional.isEmpty()) {
            throw new NotFoundObjectException("Empresa nao cadastrada");
        }
        Company company = companyOptional.get();

        if (Validators.isNullOrEmpty(attributeName)) {
            throw new InvalidAttributeException("Atributo invalido");
        }

        String attribute = company.getAttribute(attributeName);

        if (attribute == null) {
            throw new InvalidAttributeException("Atributo invalido");
        }

        return attribute;
    }
}
