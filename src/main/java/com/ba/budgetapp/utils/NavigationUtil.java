package com.ba.budgetapp.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class NavigationUtil {

    private NavigationUtil() {
    }

    public static Parent loadFXML(String path) throws IOException {
        URL resource = NavigationUtil.class.getResource(path);
        if (resource == null) {
            throw new IOException("FXML resource not found: " + path);
        }
        return FXMLLoader.load(resource);
    }

    public static void setScene(Stage stage, String path) throws IOException {
        stage.setScene(new Scene(loadFXML(path)));
    }

    public static void loadInto(StackPane container, String path) throws IOException {
        container.getChildren().setAll(loadFXML(path));
    }

    public static void loadIntoCenter(BorderPane container, String path) throws IOException {
        container.setCenter(loadFXML(path));
    }
}
