package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.User;
import com.ba.budgetapp.services.Impl.UserServiceImpl;
import com.ba.budgetapp.services.Interface.UserService;
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
    private PasswordField passwordField;

    private final UserService userService =
            new UserServiceImpl();

    @FXML
    private void login() {

        String username = usernameField.getText();

        String password = passwordField.getText();

        Optional<User> user =
                userService.authenticate(
                        username,
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
