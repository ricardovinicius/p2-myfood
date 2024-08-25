package br.ufal.ic.p2.myfood.types;

public class Unique<T> {
    final T value;

    public Unique(T value) {
        this.value = value;
    }

    public Unique(String value) {
        // You need to handle casting the value to T properly based on your use case
        this.value = (T) value; // This is a simple approach, you can make it more sophisticated if needed
    }

    public T getValue() {
        return value;
    }
}

