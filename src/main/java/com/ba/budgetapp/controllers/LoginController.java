package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Impl.AccountServiceImpl;
import com.ba.budgetapp.services.Impl.BudgetServiceImpl;
import com.ba.budgetapp.services.Interface.AccountService;
import com.ba.budgetapp.services.Interface.BudgetService;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.NavigationUtil;
import com.ba.budgetapp.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
    private final BudgetService budgetService = new BudgetServiceImpl(new BudgetDAOImpl());

    @FXML
    private void login() {

        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("Veuillez remplir tous les champs.");
            return;
        }

        try {

            Optional<Account> accountOpt = accountService.authenticate( email, password);

            if (accountOpt.isPresent()) {
                Account account = accountOpt.get();
                Budget budget = budgetService.findDefaultBudget(account.getAccountId()).orElse(null);

                SessionManager.startSession(account, budget);
                openMainLayout();

            } else {
                AlertUtil.showError("Nom d'utilisateur, email ou mot de passe incorrect.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Une erreur est survenue lors de la connexion.");
        }
    }

    private void openMainLayout() {
        try {
            Stage stage = (Stage) emailField
                    .getScene()
                    .getWindow();

            NavigationUtil.setScene(
                    stage,
                    "/com/ba/budgetapp/Views/MainLayout.fxml"
            );

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Impossible d'ouvrir l'application.");
        }
    }

    @FXML
    private void goToRegister() {

        try {
            Stage stage = (Stage) usernameField
                    .getScene()
                    .getWindow();
            NavigationUtil.setScene(
                    stage,
                    "/com/ba/budgetapp/Views/Register.fxml"
            );

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Impossible d'ouvrir l'inscription.");
        }
    }
}