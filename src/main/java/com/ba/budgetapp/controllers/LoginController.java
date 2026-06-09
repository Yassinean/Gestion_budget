package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.User;
import com.ba.budgetapp.services.Impl.UserServiceImpl;
import com.ba.budgetapp.services.Interface.UserService;
import com.ba.budgetapp.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");
            alert.showAndWait();
        }
    }

    private void openMainLayout() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/ba/budgetapp/Views/MainLayout.fxml"));

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField
                            .getScene()
                            .getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
