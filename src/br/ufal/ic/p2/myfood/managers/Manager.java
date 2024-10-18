package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;

/**
 * Abstract class representing the service layer responsible for managing
 * repository operations and implementing business logic.
 * <p>
 * The subclasses of this class should define specific logic related to
 * different types of entities or functionalities within the application.
 * This may include cleaning up resources, saving the state of repositories,
 * and executing various business rules.
 * </p>
 */
public abstract class Manager {

    /**
     * Cleans the repository by removing or resetting its contents.
     * <p>
     * This method should be implemented to define how the repository's
     * data is to be cleared, whether it's removing all entries or
     * resetting them to their initial state.
     * </p>
     *
     * @throws IOException if an I/O error occurs during the cleaning process,
     * such as issues accessing the underlying storage.
     */
    abstract void cleanRepository() throws IOException;

    /**
     * Saves the current state of the repository to a persistent storage.
     * <p>
     * Implementing this method allows for the serialization or writing
     * of repository data to a file, database, or other forms of persistent
     * storage. It ensures that the current state can be retrieved in the future.
     * </p>
     *
     * @throws IOException if an I/O error occurs during the saving process,
     * such as problems with writing to the file or database.
     */
    abstract void saveRepository() throws IOException;
}
