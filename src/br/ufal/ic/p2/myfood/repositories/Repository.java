package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.types.Unique;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * A generic repository class that handles the persistence of entities extending {@link Persistent}.
 * Implements the Repository Pattern to abstract and encapsulate data access logic.
 *
 * <p>This repository supports adding, removing, updating, and listing persistent objects of type {@code T},
 * where {@code T} is any class that extends {@link Persistent}. It also manages serialization and
 * deserialization of objects to and from the file system using Jackson's {@link ObjectMapper}.</p>
 *
 * <p>Each entity is persisted as a separate file, with the file name corresponding to the unique ID
 * of the entity. This allows efficient loading, saving, and updating of individual entities.</p>
 *
 * <p>In addition to basic CRUD operations, the repository enforces unique field constraints by
 * checking if an entity being added has any {@link Unique} fields that collide with existing entities.</p>
 *
 * @param <T> the type of the entities managed by this repository, which must extend {@link Persistent}.
 */
public abstract class Repository<T extends Persistent> {
    private final List<T> listItem = new ArrayList<>();
    private final String DATA_FOLDER_PATH = "./data";
    Class<T> clazz;

    /**
     * Constructs a new repository for managing entities of type {@code T}.
     *
     * <p>The constructor attempts to load any previously saved entities from the file system.</p>
     *
     * @param clazz the class type of the entities managed by this repository.
     */
    public Repository(Class<T> clazz) {
        this.clazz = clazz;

        try {
            createOrLoadPersistence();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new entity to the repository and checks for unique field violations.
     *
     * <p>If any field marked as {@link Unique} in the entity is already present in another data,
     * a {@link UniqueFieldException} is thrown.</p>
     *
     * @param item the entity to be added.
     * @throws UniqueFieldException if a unique field constraint is violated.
     */
    public void add(T item) throws UniqueFieldException {
         Field[] uniqueFields = item.getUniqueFields();
         for (Field field : uniqueFields) {
             String fieldName = field.getName();

             Method getFieldMethod;
             try {
                 String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                 getFieldMethod = clazz.getMethod("get" + capitalizedFieldName);
             } catch (NoSuchMethodException e) {
                 throw new RuntimeException(e);
             }

             for (T i : listItem) {
                 try {
                     if (getFieldMethod.invoke(i).equals(getFieldMethod.invoke(item))) {
                         throw new UniqueFieldException(field);
                     }
                 } catch (IllegalAccessException | InvocationTargetException e) {
                     throw new RuntimeException(e);
                 }
             }
         }

        listItem.add(item);
    };

    /**
     * Removes the specified entity from the repository.
     *
     * @param item the entity to be removed.
     */
    public void remove(T item){
        listItem.remove(item);
    };

    /**
     * Updates an existing entity in the repository.
     *
     * <p>The entity is identified by its unique ID. If an entity with the same ID is found,
     * it is replaced by the provided entity.</p>
     *
     * @param item the entity to be updated.
     * @throws NoSuchElementException if no entity with the given ID is found.
     */
    public void update(T item) throws NoSuchElementException {
        for (int i = 0; i < listItem.size(); i++) {
            T obj = listItem.get(i);
            if (obj.getId() == item.getId()) {
                listItem.set(i, item);
                return;
            }
        }

        throw new NoSuchElementException("Object with given ID not found");
    }

    /**
     * Returns an unmodifiable list of all entities in the repository.
     *
     * @return a list of all persistent entities.
     */
    public List<T> list() {
        return Collections.unmodifiableList(listItem);
    }

    /**
     * Loads previously persisted entities from the file system into the repository.
     *
     * <p>If the persistence directory does not exist, it will be created, and no entities
     * will be loaded. If files exist in the directory, they are deserialized into entities
     * of type {@code T} and added to the repository.</p>
     *
     * @throws IOException if an error occurs during file handling or deserialization.
     */
    void createOrLoadPersistence() throws IOException {
        File dir = new File(DATA_FOLDER_PATH + "/" + clazz.getSimpleName());

        if (!dir.exists()) {
            boolean dirWasCreated = new File(DATA_FOLDER_PATH + "/" + clazz.getSimpleName()).mkdir();

            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        File[] files = dir.listFiles(File::isFile);

        if (files == null) {
            return;
        }

        for (File file: files) {
            T item = mapper.readValue(file, clazz);
            listItem.add(item);
        }

    }

    /**
     * Clears all data from the repository and deletes all persisted files from the file system.
     *
     * @throws IOException if an error occurs while deleting the files.
     */
    public void clean() throws IOException {
        File directory = new File(DATA_FOLDER_PATH + "/" + clazz.getSimpleName());

        // Ensure the directory exists
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                throw new IOException("Failed to create directory: " + directory.getPath());
            }
        }

        File[] files = directory.listFiles(File::isFile);

        if (files == null) {
            return;
        }

        for (File file: files) {
            file.delete();
        }

        listItem.clear();
    }

    /**
     * Saves all entities in the repository to the file system.
     *
     * <p>Each entity is serialized into a separate file, named after the entity's unique ID.</p>
     *
     * @throws IOException if an error occurs during file creation or serialization.
     */
    public void save() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File directory = new File(DATA_FOLDER_PATH + "/" + clazz.getSimpleName());

        // Ensure the directory exists
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                throw new IOException("Failed to create directory: " + directory.getPath());
            }
        }

        for (T item : listItem) {
            File resultFile = new File(directory, item.getId() + ".dat");

            // Create the file if it does not exist
            if (!resultFile.exists()) {
                resultFile.createNewFile();
            }

            // Write the individual item to the file
            mapper.writeValue(resultFile, item);
        }
    }
}
