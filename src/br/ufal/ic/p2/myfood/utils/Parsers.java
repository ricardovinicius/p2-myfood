package br.ufal.ic.p2.myfood.utils;

import br.ufal.ic.p2.myfood.models.User;

import java.util.List;

import java.util.List;

/**
 * Utility class for parsing and formatting data related to users.
 */
public class Parsers {

    /**
     * Parses a list of users and returns a formatted string of their email addresses.
     *
     * @param list the list of {@link User} objects to be parsed.
     * @return a formatted string containing the email addresses of the users
     *         in the format "{[email1, email2, ...]}".
     */
    public static String deliveryListParser(List<User> list) {
        StringBuilder s = new StringBuilder("{[");

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            s.append(user.getEmail());

            if (i < list.size() - 1) {
                s.append(", ");
            }
        }

        s.append("]}");
        return s.toString();
    }

}

