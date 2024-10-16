package br.ufal.ic.p2.myfood.exceptions.company;

public class NotOwnerUserException extends RuntimeException {
    public NotOwnerUserException(String message) {
        super(message);
    }
}
