package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.User;
import com.ba.budgetapp.services.Impl.UserServiceImpl;
import com.ba.budgetapp.services.Interface.UserService;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private final UserService userService =
            new UserServiceImpl();

    @FXML
    private void register() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (username.isBlank()
                || password.isBlank()
                || confirm.isBlank()) {

            AlertUtil.showError("Tous les champs sont obligatoires.");
            return;
        }

        if (!password.equals(confirm)) {
            AlertUtil.showError("Les mots de passe ne correspondent pas.");
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            boolean created = userService.register(user);
            if (created) {
                AlertUtil.showInfo("Compte créé avec succès.");
                goToLogin();
            }
        } catch (Exception e) {
            AlertUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            NavigationUtil.setScene(stage, "/com/ba/budgetapp/Views/login.FXML");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Impossible d'ouvrir la connexion.");
        }
    }
}
