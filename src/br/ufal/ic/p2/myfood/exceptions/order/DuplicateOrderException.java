package br.ufal.ic.p2.myfood.exceptions.order;

public class DuplicateOrderException extends RuntimeException {
    public DuplicateOrderException(String message) {
        super(message);
    }
}
