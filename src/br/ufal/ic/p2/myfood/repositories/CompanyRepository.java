package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.User;

import java.util.List;
import java.util.Optional;

public class CompanyRepository extends Repository<Company> {
    private static CompanyRepository instance;
    private CompanyRepository() {
        super(Company.class);
    }

    // Method to access the singleton instance
    public static CompanyRepository getInstance() {
        if (instance == null) {
            synchronized (CompanyRepository.class) {
                if (instance == null) {
                    instance = new CompanyRepository();
                }
            }
        }
        return instance;
    }

    public Optional<Company> getByName(String name) {
        return list().stream().filter(c -> c.getName().equals(name)).findFirst();
    }

    public Optional<Company> getById(int id) {
        return list().stream().filter(c -> c.getId() == id).findFirst();
    }

    public List<Company> listByOwnerId(int ownerId) {
        return list().stream().filter(c -> c.getOwner().getId() == ownerId).toList();
    }

    public List<Company> listByDeliveryId(int deliveryId) {
        return list().stream().filter(c -> c.getDeliveryList().stream().anyMatch(user -> user.getId() == deliveryId)).toList();
    }
}
