package br.ufal.ic.p2.myfood.exceptions.order;

public class ClosedOrderException extends RuntimeException {
    public ClosedOrderException(String message) {
        super(message);
    }
}
