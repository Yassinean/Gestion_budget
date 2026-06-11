package com.ba.budgetapp.utils;

import com.ba.budgetapp.models.entities.Account;

public final class SessionManager {

    private static Account currentAccount;

    private SessionManager() {
    }

    public static void login(Account account) {
        currentAccount = account;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static Long getCurrentAccountId() {

        if (currentAccount == null) {
            return null;
        }

        return currentAccount.getAccountId();
    }

    public static void logout() {
        currentAccount = null;
    }
}