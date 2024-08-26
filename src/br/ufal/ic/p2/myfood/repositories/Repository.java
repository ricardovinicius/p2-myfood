package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.types.Persistent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class Repository<T extends Persistent> {
    private final List<T> listItem = new ArrayList<>();
    private final String DATA_FOLDER_PATH = "./data";
    Class<T> clazz;

    public Repository(Class<T> clazz) {
        this.clazz = clazz;

        try {
            createOrLoadPersistence();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                     System.out.println(getFieldMethod.invoke(i) + " " + getFieldMethod.invoke(item));
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
    public void remove(T item){
        listItem.remove(item);
    };
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
    public List<T> list() {
        return Collections.unmodifiableList(listItem);
    }
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
