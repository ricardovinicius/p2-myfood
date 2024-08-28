package br.ufal.ic.p2.myfood.types;

/**
 * The class Unique is a utility class that represents a unique field of
 * another Class. Is generic and can be used with any another type/class
 * of Java. Is part of Persistent abstract class, that have util methods
 * to handle with Unique fields.
 *
 * @author Ricardo Vinicius
 */
public class Unique<T> {
    final T value;

    public Unique(T value) {
        this.value = value;
    }

    public Unique(String value) {
        this.value = (T) value;
    }

    public T getValue() {
        return value;
    }
}

