package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.Owner;
import br.ufal.ic.p2.myfood.models.Restaurant;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;

import java.io.IOException;
import java.util.Optional;

public class CompanyManager extends Manager {
    private CompanyRepository companyRepository = CompanyRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();

    public CompanyManager() {}

    public void cleanRepository() throws IOException {
        companyRepository.clean();
    }

    public int createCompany(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        Optional<User> userOptional = userRepository.getById(dono);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        if (!(user.getDtype().equals("owner"))) {
            throw new RuntimeException("Usuario nao pode criar uma empresa");
        }

        if (tipoEmpresa.equals("restaurante")) {


            Optional<Company> sameNameRestaurantOptional = companyRepository.getByName(nome);

            if (sameNameRestaurantOptional.isPresent()) {
                Company sameNameRestaurant = sameNameRestaurantOptional.get();

                if (sameNameRestaurant.getOwner().getId() != dono) {
                    throw new RuntimeException("Empresa com esse nome ja existe");
                }

                if (sameNameRestaurant.getAddress().equals(endereco)) {
                    throw new RuntimeException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }

            Restaurant restaurant = Restaurant.create(nome, endereco, tipoCozinha, (Owner) user);
            companyRepository.add(restaurant);

            return restaurant.getId();
        }

        return -1;
    }
}
