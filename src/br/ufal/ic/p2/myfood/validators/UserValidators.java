package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.user.DeliveryUserOnRouteException;
import br.ufal.ic.p2.myfood.exceptions.user.NullCompanyUser;
import br.ufal.ic.p2.myfood.models.Delivery;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.UserRepository;

import java.util.List;

/**
 * Validator class for user-related operations.
 */
public class UserValidators {

    /**
     * Checks if a license plate is already registered for delivery users.
     *
     * @param licensePlate the license plate to check.
     * @return true if the license plate is already registered; false otherwise.
     */
    public static boolean alreadyRegistredLicensePlate(String licensePlate) {
        UserRepository userRepository = UserRepository.getInstance();
        List<User> deliveryUsers = userRepository.list().stream()
                .filter(user -> user.getDtype().equals("delivery"))
                .toList();

        for (User user : deliveryUsers) {
            Delivery delivery = (Delivery) user;

            if (delivery.getLicensePlate().equals(licensePlate)) {
                return true;
            }
        }

        return false;
    }
}
