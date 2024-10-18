package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.company.NotOwnerUserException;
import br.ufal.ic.p2.myfood.exceptions.company.SameNameAndAddressCompanyException;
import br.ufal.ic.p2.myfood.exceptions.company.SameNameCompanyException;
import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;

import java.util.Optional;

/**
 * Validator class for company-related operations.
 */
public class CompanyValidators {

    /**
     * Checks if the user has the right type to create a company.
     *
     * @param user the user attempting to create a company.
     * @throws NotOwnerUserException if the user is not an owner.
     */
    public static void userCanCreateCompany(User user) throws NotOwnerUserException {
        if (!(user.getDtype().equals("owner"))) {
            throw new NotOwnerUserException("Usuario nao pode criar uma empresa");
        }
    }

    /**
     * Checks if the user has the right type to deliver.
     *
     * @param user the user attempting to deliver.
     * @throws IllegalArgumentException if the user is not a delivery person.
     */
    public static void userCanDelivery(User user) throws IllegalArgumentException {
        if (!(user.getDtype().equals("delivery"))) {
            throw new IllegalArgumentException("Usuario nao e um entregador");
        }
    }

    /**
     * Validates if a company with the same name and address already exists.
     *
     * @param companyName the name of the company to be validated.
     * @param user        the user attempting to register the company.
     * @param address     the address of the company to be validated.
     * @throws SameNameAndAddressCompanyException if a company with the same name and address exists.
     * @throws SameNameCompanyException            if a company with the same name exists but different address.
     */
    public static void sameNameCompany(String companyName, User user, String address) throws SameNameAndAddressCompanyException,
            SameNameCompanyException {
        CompanyRepository companyRepository = CompanyRepository.getInstance();

        Optional<Company> sameNameCompanyOptional = companyRepository.getByName(companyName);

        if (sameNameCompanyOptional.isPresent()) {
            Company sameNameRestaurant = sameNameCompanyOptional.get();

            if (sameNameRestaurant.getOwner().getId() != user.getId()) {
                throw new SameNameCompanyException("Empresa com esse nome ja existe");
            }

            if (sameNameRestaurant.getAddress().equals(address)) {
                throw new SameNameAndAddressCompanyException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }
    }
}
