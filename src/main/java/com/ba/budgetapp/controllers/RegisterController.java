package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.User;
import com.ba.budgetapp.services.Impl.UserServiceImpl;
import com.ba.budgetapp.services.Interface.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

            showError("Tous les champs sont obligatoires.");
            return;
        }

        if (!password.equals(confirm)) {
            showError("Les mots de passe ne correspondent pas.");
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            boolean created = userService.register(user);
            if (created) {
                showInfo("Compte créé avec succès.");
                goToLogin();
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/com/ba/budgetapp/Views/login.FXML"));

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}