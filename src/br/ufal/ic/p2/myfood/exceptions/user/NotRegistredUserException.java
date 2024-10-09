package br.ufal.ic.p2.myfood.exceptions.user;

public class NotRegistredUserException extends RuntimeException {
    public NotRegistredUserException(String message) {
        super(message);
    }
}
