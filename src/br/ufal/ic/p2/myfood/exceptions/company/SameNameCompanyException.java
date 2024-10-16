package br.ufal.ic.p2.myfood.exceptions.company;

public class SameNameCompanyException extends RuntimeException {
    public SameNameCompanyException(String message) {
        super(message);
    }
}
