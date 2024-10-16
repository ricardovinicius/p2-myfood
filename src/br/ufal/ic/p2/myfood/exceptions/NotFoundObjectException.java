package br.ufal.ic.p2.myfood.exceptions;

public class NotFoundObjectException extends RuntimeException {
    public NotFoundObjectException(String message) {
        super(message);
    }
}
