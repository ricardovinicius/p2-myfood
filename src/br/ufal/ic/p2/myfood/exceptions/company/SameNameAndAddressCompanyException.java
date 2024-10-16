package br.ufal.ic.p2.myfood.exceptions.company;

public class SameNameAndAddressCompanyException extends RuntimeException {
    public SameNameAndAddressCompanyException(String message) {
        super(message);
    }
}
