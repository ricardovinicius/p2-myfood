package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.Company;

import java.io.IOException;
import java.util.List;

public class CompanyRepository extends RepositoryBase<Company> {
    public CompanyRepository() {
        super(Company.class);
    }

    List<Company> companies;
    {
        try {
            companies = createOrLoadPersistence();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
