package br.ufal.ic.p2.myfood.exceptions.user;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String message) {
        super(message);
    }
}
