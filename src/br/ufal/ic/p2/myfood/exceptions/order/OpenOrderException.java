package br.ufal.ic.p2.myfood.exceptions.order;

public class OpenOrderException extends RuntimeException {
    public OpenOrderException(String message) {
        super(message);
    }
}
