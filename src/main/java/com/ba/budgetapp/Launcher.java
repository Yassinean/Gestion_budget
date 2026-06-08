package com.ba.budgetapp;

import java.math.BigDecimal;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.CategoryDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.UserDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.DAO.Interface.BudgetDAO;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.DAO.Interface.UserDAO;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.User;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        // Application.launch(HelloApplication.class, args);

        UserDAO userDAO = new UserDAOImpl();
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        BudgetDAO budgetDAO = new BudgetDAOImpl();

        // create user
        User user = new User(null, "yassine", "123456", true);
        boolean userCreated = userDAO.create(user);
        System.out.println("User created: " + userCreated);

        // fetch created user to obtain generated id
        var maybeUser = userDAO.findByUserName("yassine");
        if (maybeUser.isEmpty()) {
            System.out.println("Failed to retrieve created user");
            return;
        }else {
            System.out.println("User retrieved: " + maybeUser.get());
        }

        Long userId = maybeUser.get().getUserId();

        // create category for this user
        Category category = new Category(null, "makla", userId);
        boolean categoryCreated = categoryDAO.create(category);
        System.out.println("Category created: " + categoryCreated);

        // find category id
        Long categoryId = 2L;
        for (Category c : categoryDAO.findAll()) {
            if (c.getCategoryName().equals("makla") && c.getUserId().equals(userId)) {
            categoryId = c.getCategoryId();
            break;
            }
        }
        if (categoryId == null) {
            System.out.println("Failed to retrieve category id");
            return;
        }else {
            System.out.println("Category id retrieved: " + categoryId);
        }

        // create account for this user
        boolean accountCreated = accountDAO.create(new com.ba.budgetapp.models.entities.Account(null, "Compte courant", true, userId));
        System.out.println("Account created: " + accountCreated);

        // find account id
        Long accountId = accountDAO.findByUserId(userId)
            .map(com.ba.budgetapp.models.entities.Account::getAccountId)
            .orElse(null);
        System.out.println("Account id retrieved: " + accountId);

        if (accountId == null) {
            System.out.println("Failed to retrieve account id");
            return;
        }else {
            System.out.println("Account id retrieved: " + accountId);
        }

        // create transaction referencing the existing account and category
        boolean result = transactionDAO.create(
            new com.ba.budgetapp.models.entities.Transaction(
                null,
                BigDecimal.valueOf(3.6),
                "Achat de café",
                java.time.LocalDate.now(),
                com.ba.budgetapp.models.entities.TransactionType.EXPENSE,
                accountId,
                categoryId
            )
        );
        if (result) {
            System.out.println("Transaction created successfully" + result);
        } else {
            System.out.println("Failed to create transaction");
        }

        Budget budget = new Budget(1L, BigDecimal.valueOf(8000), java.time.LocalDate.of(2024, 6, 1),1L);
            budgetDAO.create(budget);
    }
}
