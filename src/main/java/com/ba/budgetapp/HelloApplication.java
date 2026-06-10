package com.ba.budgetapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var layout = getClass().getResource("/com/ba/budgetapp/Views/Register.fxml");
        if (layout == null) {
            throw new IOException("FXML resource not found");
        }

        var css = getClass().getResource("/com/ba/budgetapp/CSS/style.css");
        if (css == null) {
            throw new IOException("CSS resource not found");
        }

        Scene scene = new Scene(FXMLLoader.load(layout), 1400, 800);
        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Budget Manager");
        stage.setScene(scene);
        stage.show();
    }
}
