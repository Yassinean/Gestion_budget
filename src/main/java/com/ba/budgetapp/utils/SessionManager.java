package com.ba.budgetapp.utils;

import com.ba.budgetapp.models.entities.User;

public final class SessionManager {

    private static User currentUser;

    private SessionManager() {
    }

    public static void login(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Long getCurrentUserId() {

        if (currentUser == null) {
            return null;
        }

        return currentUser.getUserId();
    }

    public static void logout() {
        currentUser = null;
    }
}