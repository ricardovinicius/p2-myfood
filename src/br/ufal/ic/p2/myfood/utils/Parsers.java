package br.ufal.ic.p2.myfood.utils;

import br.ufal.ic.p2.myfood.models.User;

import java.util.List;

public class Parsers {
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
