package br.ufal.ic.p2.myfood.exceptions.product;

public class SameNameProductException extends RuntimeException {
    public SameNameProductException(String message) {
        super(message);
    }
}
