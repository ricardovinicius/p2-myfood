package br.ufal.ic.p2.myfood.exceptions.order;

public class DoneOrderException extends RuntimeException {
    public DoneOrderException(String message) {
        super(message);
    }
}
