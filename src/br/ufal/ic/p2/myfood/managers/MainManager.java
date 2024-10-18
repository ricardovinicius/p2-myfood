package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainManager is the central manager responsible for coordinating
 * various manager instances within the application.
 * <p>
 * This class aggregates multiple managers, such as UserManager,
 * CompanyManager, ProductManager, OrderManager, and RouteManager,
 * and provides unified methods to clean and save repositories.
 * It serves as the primary interface for managing the application’s
 * business logic and repository interactions.
 * </p>
 */
public class MainManager extends Manager {
    private final List<Manager> managerInstances = new ArrayList<Manager>();

    /**
     * Constructs a new MainManager and initializes the various
     * manager instances required for handling different entities
     * within the application.
     * <p>
     * The managers are instantiated and added to the internal list,
     * allowing the MainManager to perform operations across all
     * of them.
     * </p>
     */
    public MainManager() {
        Manager userManager = new UserManager();
        CompanyManager companyManager = new CompanyManager();
        ProductManager productManager = new ProductManager();
        OrderManager orderManager = new OrderManager();
        RouteManager routeManager = new RouteManager();

        managerInstances.add(userManager);
        managerInstances.add(companyManager);
        managerInstances.add(productManager);
        managerInstances.add(orderManager);
        managerInstances.add(routeManager);
    }

    /**
     * Cleans the repositories of all managed instances.
     * <p>
     * This method iterates through each manager and calls their
     * respective cleanRepository method. If any manager encounters
     * an IOException, an error message is printed to the console
     * indicating which manager's repository could not be cleaned.
     * </p>
     */
    public void cleanRepository() {
        for (Manager manager : managerInstances) {
            try {
                manager.cleanRepository();
            } catch (IOException e) {
                System.out.println("Error cleaning repository of " + manager.getClass().getName());
            }
        }
    }

    /**
     * Saves the current state of all managed instances' repositories.
     * <p>
     * This method iterates through each manager and calls their
     * respective saveRepository method. If any manager encounters
     * an IOException, an error message is printed to the console
     * indicating which manager's repository could not be saved.
     * </p>
     */
    public void saveRepository() {
        for (Manager manager : managerInstances) {
            try {
                manager.saveRepository();
            } catch (IOException e) {
                System.out.println("Error saving repository of " + manager.getClass().getName());
            }
        }
    }
}
