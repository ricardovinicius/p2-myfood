package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainManager extends Manager {
    private final List<Manager> managerInstances = new ArrayList<Manager>();

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

    public void cleanRepository() {
        for (Manager manager : managerInstances) {
            try {
                manager.cleanRepository();
            } catch (IOException e) {
                System.out.println("Error cleaning repository of " + manager.getClass().getName());
            }
        }
    }

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
