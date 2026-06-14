package com.ba.budgetapp.utils;

import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Impl.BudgetServiceImpl;
import com.ba.budgetapp.services.Interface.BudgetService;

public final class SessionManager {

    private static Account currentAccount;
    private static Budget currentBudget;

    private SessionManager() {
    }

    public static void startSession(Account account, Budget budget) {
        currentAccount = account;
        currentBudget = resolveBudget(account, budget);
    }

    private static Budget resolveBudget(Account account, Budget budget) {
        if (budget != null && budget.getBudgetId() != null) {
            return budget;
        }

        if (account == null) {
            return null;
        }

        BudgetService budgetService = new BudgetServiceImpl();

        return budgetService.findDefaultBudget(account.getAccountId())
                .orElseGet(() -> createDefaultBudget(account, budgetService));
    }

    private static Budget createDefaultBudget(Account account, BudgetService budgetService) {
        Budget defaultBudget = new Budget();
        defaultBudget.setOwnerId(account.getAccountId());
        defaultBudget.setTitle("Budget principal");
        defaultBudget.setCurrency("MAD");

        boolean created = budgetService.create(defaultBudget);
        if (!created || defaultBudget.getBudgetId() == null) {
            throw new IllegalStateException("Impossible d'initialiser le budget de session.");
        }

        return defaultBudget;
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static Long getCurrentAccountId() {
        return currentAccount != null
                ? currentAccount.getAccountId()
                : null;
    }

    public static void setCurrentBudget(Budget budget) {
        currentBudget = budget;
    }

    public static Budget getCurrentBudget() {
        return currentBudget;
    }

    public static Long getCurrentBudgetId() {
        return currentBudget != null
                ? currentBudget.getBudgetId()
                : null;
    }

    public static boolean isLoggedIn() {
        return currentAccount != null;
    }

    public static boolean hasCurrentBudget() {
        return currentBudget != null;
    }

    public static void clear() {
        currentAccount = null;
        currentBudget = null;
    }
}