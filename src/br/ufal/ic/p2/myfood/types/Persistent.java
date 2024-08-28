package br.ufal.ic.p2.myfood.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an abstract base class for persistent entities within the system.
 * Each entity that extends this class will have a unique identifier (`id`) and
 * a mechanism to retrieve fields that are marked as unique.
 *
 * @author Ricardo Vinicius
 */
public abstract class Persistent {
    protected int id;

    public int getId() {
        return id;
    }

    @JsonIgnore()
    public Field[] getUniqueFields() {
        Class<?> clazz = this.getClass();
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        List<Field> uniqueFields = new ArrayList<Field>();

        for (Field field : fields) {
            Class<?> fieldType = field.getType();

            if (Unique.class.isAssignableFrom(fieldType)) {
                uniqueFields.add(field);
            }
        }

        return uniqueFields.toArray(new Field[0]);
    }
}
