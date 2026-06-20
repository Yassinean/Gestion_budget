package com.ba.budgetapp.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private static final int LOG_ROUNDS = 12;

    private PasswordUtils() {
    }

    public static String hashPassword(String password) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(LOG_ROUNDS)
        );
    }

    public static boolean verifyPassword(
            String plainPassword,
            String hashedPassword) {

        return BCrypt.checkpw(
                plainPassword,
                hashedPassword
        );
    }
}