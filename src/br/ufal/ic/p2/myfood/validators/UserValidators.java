package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.models.Delivery;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidators {

    public static boolean alreadyRegistredLicensePlate(String licensePlate) {
        UserRepository userRepository = UserRepository.getInstance();
        List<User> deliveryUsers = userRepository.list().stream().filter(user -> user.getDtype().equals("delivery")).toList();

       for (User user : deliveryUsers) {
           Delivery delivery = (Delivery) user;

           if (delivery.getLicensePlate().equals(licensePlate)) {
               return true;
           }
       }

       return false;
    }
}
