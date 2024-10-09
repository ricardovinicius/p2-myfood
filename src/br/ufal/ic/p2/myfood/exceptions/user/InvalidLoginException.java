package br.ufal.ic.p2.myfood.exceptions.user;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
