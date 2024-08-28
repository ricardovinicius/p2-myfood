package br.ufal.ic.p2.myfood.exceptions;

import java.lang.reflect.Field;

/**
 * Exception thrown when a violation of a unique field constraint occurs.
 *
 * <p>This exception is used to signal that a field, marked as unique, has been
 * assigned a value that already exists in another instance of the same entity.</p>
 *
 * <p>The {@code field} that caused the violation is stored and can be retrieved
 * using the {@link #getField()} method.</p>
 *
 * @see Field
 * @since 1.0
 */
public class UniqueFieldException extends RuntimeException {
    Field field;

    /**
     * Constructs a new {@code UniqueFieldException} for the specified field.
     *
     * @param field the field that caused the unique constraint violation.
     */
    public UniqueFieldException(Field field) {
        super("Unique fields must be unique. Duplicate found for field: " + field.getName());
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
