package br.ufal.ic.p2.myfood.utils;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.isBlank();
    }

    public static boolean isValidEmail(final String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }

        String regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
                .matcher(s)
                .matches();
    }
}
