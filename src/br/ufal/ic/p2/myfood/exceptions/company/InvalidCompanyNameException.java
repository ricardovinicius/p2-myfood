package br.ufal.ic.p2.myfood.exceptions.company;

public class InvalidCompanyNameException extends RuntimeException {
    public InvalidCompanyNameException(String message) {
        super(message);
    }
}
