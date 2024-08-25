package br.ufal.ic.p2.myfood.exceptions;

import java.lang.reflect.Field;

public class UniqueFieldException extends RuntimeException {
    Field field;
    public UniqueFieldException(Field field) {
        super("Unique fields must be unique. Duplicate found for field: " + field.getName());
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
