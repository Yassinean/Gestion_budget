package com.ba.budgetapp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public final class AlertUtil {

    private AlertUtil() {
    }

    public static void showError(String message) {
        show(Alert.AlertType.ERROR, "Erreur", message);
    }

    public static void showInfo(String message) {
        show(Alert.AlertType.INFORMATION, "Information", message);
    }

    public static void showWarning(String message) {
        show(Alert.AlertType.WARNING, "Attention", message);
    }

    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(ButtonType.OK::equals).isPresent();
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
