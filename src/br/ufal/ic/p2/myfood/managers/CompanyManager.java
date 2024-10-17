package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;
import br.ufal.ic.p2.myfood.exceptions.NotFoundObjectException;
import br.ufal.ic.p2.myfood.exceptions.company.*;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.utils.Parsers;
import br.ufal.ic.p2.myfood.validators.CommonValidators;
import br.ufal.ic.p2.myfood.validators.CompanyValidators;

import javax.xml.validation.Validator;
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

    public int createCompany(String companyType, int ownerId, String companyName, String address, String subtype,
                             String openingHour, String closingHour, boolean open24hours, int employees) {
        Optional<User> userOptional = userRepository.getById(ownerId);

        if (userOptional.isEmpty()) {
            return -1;
        }

        User user = userOptional.get();

        CompanyValidators.userCanCreateCompany(user);
        CompanyValidators.sameNameCompany(companyName, user, address);

        if (CommonValidators.isNullOrEmpty(companyType)) {
            throw new IllegalArgumentException("Tipo de empresa invalido");
        }

        Company company = null;

        if (companyType.equals("restaurante")) {
            company = Restaurant.create(companyName, address, subtype, user);
        }

        if (companyType.equals("mercado")) {
            company = Market.create(companyName, address, user, openingHour, closingHour, subtype);
        }

        if (companyType.equals("farmacia")) {
            company = DrugStore.create(companyName, address, user, open24hours, employees);
        }

        companyRepository.add(company);

        return company.getId();
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
        int index = CommonValidators.parseIndex(listIndex);

        if (CommonValidators.isNullOrEmpty(companyName)) {
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

        if (CommonValidators.isNullOrEmpty(attributeName)) {
            throw new InvalidAttributeException("Atributo invalido");
        }

        String attribute = company.getAttribute(attributeName);

        if (attribute == null) {
            throw new InvalidAttributeException("Atributo invalido");
        }

        return attribute;
    }

    public void updateMarketTime(int companyId, String openingHour, String closingHour) {
        if (!CommonValidators.isValidTimeFormat(openingHour) || !CommonValidators.isValidTimeFormat(closingHour)) {
            throw new InvalidAttributeException("Formato de hora invalido");
        }

        if (!CommonValidators.isValidTimeRange(openingHour, closingHour)) {
            throw new InvalidAttributeException("Horario invalido");
        }

        Optional<Company> companyOptional = companyRepository.getById(companyId);
        if (companyOptional.isEmpty()) {
            throw new NotFoundObjectException("Empresa nao cadastrada");
        }
        Company company = companyOptional.get();

        if (!company.getDtype().equals("market")) {
            throw new IllegalArgumentException("Nao e um mercado valido");
        }

        Market market = (Market) company;
        market.setOpeningHour(openingHour);
        market.setClosingHour(closingHour);
    }

    public void registerDelivery(int companyId, int deliveryId) {
        Optional<Company> companyOptional = companyRepository.getById(companyId);
        if (companyOptional.isEmpty()) {
            throw new NotFoundObjectException("Empresa nao cadastrada");
        }
        Company company = companyOptional.get();

        Optional<User> userOptional = userRepository.getById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new NotFoundObjectException("Entregador nao cadastrado");
        }
        User user = userOptional.get();

        CompanyValidators.userCanDelivery(user);

        company.getDeliveryList().add(user);
    }

    public String getDelivery(int companyId) {
        Optional<Company> companyOptional = companyRepository.getById(companyId);
        if (companyOptional.isEmpty()) {
            throw new NotFoundObjectException("Empresa nao cadastrada");
        }
        Company company = companyOptional.get();

        return Parsers.deliveryListParser(company.getDeliveryList());
    }
}
