package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainManager {
    private List<Manager> managerInstances = new ArrayList<Manager>();

    public MainManager() {
        Manager userManager = new UserManager();

        managerInstances.add(userManager);
    }

    public void cleanSystem() {
        for (Manager manager : managerInstances) {
            try {
                manager.cleanRepository();
            } catch (IOException e) {
                System.out.println("Error cleaning repository of " + manager.getClass().getName());
            }
        }
    }
}
