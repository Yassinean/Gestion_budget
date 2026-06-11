package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Impl.AccountServiceImpl;
import com.ba.budgetapp.services.Interface.AccountService;
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

    private final AccountService accountService =
            new AccountServiceImpl();

    @FXML
    private void login() {

        String username = usernameField.getText();

        String email = emailField.getText();

        String password = passwordField.getText();

        Optional<Account> user =
                accountService.authenticate(
                        username,
                        email,
                        password);

        if (user.isPresent()) {
            SessionManager.login(user.get());
            openMainLayout();
        } else {
            AlertUtil.showError("Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    private void openMainLayout() {

        try {
            Stage stage = (Stage) usernameField
                            .getScene()
                            .getWindow();
            NavigationUtil.setScene(stage, "/com/ba/budgetapp/Views/MainLayout.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Impossible d'ouvrir l'application.");
        }
    }

    @FXML
    private void goToRegister() {

        try {
            Stage stage =
                    (Stage) usernameField
                            .getScene()
                            .getWindow();

            NavigationUtil.setScene(stage, "/com/ba/budgetapp/Views/Register.fxml");

        } catch (Exception e) {

            e.printStackTrace();
            AlertUtil.showError("Impossible d'ouvrir l'inscription.");
        }
    }
}
