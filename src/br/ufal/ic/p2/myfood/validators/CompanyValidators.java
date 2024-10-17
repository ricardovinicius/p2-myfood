package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.company.NotOwnerUserException;
import br.ufal.ic.p2.myfood.exceptions.company.SameNameAndAddressCompanyException;
import br.ufal.ic.p2.myfood.exceptions.company.SameNameCompanyException;
import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;

import java.util.Optional;

public class CompanyValidators {
    public static void userCanCreateCompany(User user) throws NotOwnerUserException {
        if (!(user.getDtype().equals("owner"))) {
            throw new NotOwnerUserException("Usuario nao pode criar uma empresa");
        }
    }

    public static void userCanDelivery(User user) throws IllegalArgumentException {
        if (!(user.getDtype().equals("delivery"))) {
            throw new IllegalArgumentException("Usuario nao e um entregador");
        }
    }

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
