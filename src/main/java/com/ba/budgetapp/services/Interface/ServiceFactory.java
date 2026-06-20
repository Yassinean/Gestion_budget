package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.services.Impl.AccountServiceImpl;
import com.ba.budgetapp.services.Impl.BudgetServiceImpl;
import com.ba.budgetapp.services.Impl.CategoryServiceImpl;
import com.ba.budgetapp.services.Impl.DashboardServiceImpl;
import com.ba.budgetapp.services.Impl.TransactionServiceImpl;

public final class ServiceFactory {

    private static final BudgetService BUDGET_SERVICE =
            new BudgetServiceImpl(new BudgetDAOImpl());

    private static final TransactionService TRANSACTION_SERVICE =
            new TransactionServiceImpl();

    private static final CategoryService CATEGORY_SERVICE =
            new CategoryServiceImpl();

    private static final AccountService ACCOUNT_SERVICE =
            new AccountServiceImpl(new AccountDAOImpl());

    private static final DashboardService DASHBOARD_SERVICE =
            new DashboardServiceImpl();

    private ServiceFactory() {}

    public static TransactionService transactionService() {
        return TRANSACTION_SERVICE;
    }

    public static CategoryService categoryService() {
        return CATEGORY_SERVICE;
    }

    public static AccountService accountService() {
        return ACCOUNT_SERVICE;
    }

    public static BudgetService budgetService() {
        return BUDGET_SERVICE;
    }

    public static DashboardService dashboardService() {
        return DASHBOARD_SERVICE;
    }
}
