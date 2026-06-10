package com.ba.budgetapp.controllers;

import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    public void initialize() {
        loadView("/com/ba/budgetapp/Views/dashboard.FXML");
    }

    @FXML
    private void showDashboard() {
        loadView("/com/ba/budgetapp/Views/dashboard.FXML");
    }

    @FXML
    private void showTransactions() {
        loadView("/com/ba/budgetapp/Views/transactions.FXML");
    }

    @FXML
    private void showCategories() {
        loadView("/com/ba/budgetapp/Views/categories.FXML");
    }

    @FXML
    private void showAccounts() {
        loadView("/com/ba/budgetapp/Views/accounts.FXML");
    }

    @FXML
    private void showBudgets() {
        loadView("/com/ba/budgetapp/Views/budgets.FXML");
    }

    @FXML
    private void exitApplication() {
        System.exit(0);
    }

    private void loadView(String path) {
        try {
            NavigationUtil.loadInto(contentPane, path);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showError("Impossible de charger la vue : " + path);
        }
    }
}
