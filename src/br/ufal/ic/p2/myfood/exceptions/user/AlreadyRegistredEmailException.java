package br.ufal.ic.p2.myfood.exceptions.user;

public class AlreadyRegistredEmailException extends RuntimeException {
    public AlreadyRegistredEmailException(String message) {
        super(message);
    }
}
