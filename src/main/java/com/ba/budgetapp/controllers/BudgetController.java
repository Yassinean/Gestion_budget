package com.ba.budgetapp.controllers;

import java.math.BigDecimal;

import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Impl.BudgetServiceImpl;
import com.ba.budgetapp.services.Interface.BudgetService;
import com.ba.budgetapp.utils.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BudgetController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField amountField;

    @FXML
    private TableColumn<Budget, BigDecimal> amountColumn;

    @FXML
    private ComboBox<String> currencyBox;

    @FXML
    private TableView<Budget> budgetTable;

    @FXML
    private TableColumn<Budget, Long> idColumn;

    @FXML
    private TableColumn<Budget, String> titleColumn;

    @FXML
    private TableColumn<Budget, String> currencyColumn;

    private final BudgetService budgetService = new BudgetServiceImpl(new BudgetDAOImpl());

    
    private final Long ownerId = SessionManager.getCurrentAccountId();

    public void initialize() {

        currencyBox.getItems().addAll(
                "MAD"
        );

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("budgetId"));

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title"));

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount"));

        currencyColumn.setCellValueFactory(
                new PropertyValueFactory<>("currency"));

        loadBudgets();

        budgetTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, selected) -> {

                    if (selected != null) {

                        titleField.setText(selected.getTitle());

                        currencyBox.setValue(selected.getCurrency());

                    }

                });

    }

    private void loadBudgets() {

        budgetTable.getItems().setAll(
                budgetService.findByOwnerId(ownerId)
        );

    }

    @FXML
    private void addBudget() {

        try {

            Budget budget = new Budget();

            budget.setOwnerId(ownerId);

            budget.setTitle(titleField.getText());

            budget.setAmount(new BigDecimal(amountField.getText()));

            budget.setCurrency(currencyBox.getValue());

            if (budgetService.create(budget)) {
                clearFields();
                loadBudgets();
            }

        } catch (Exception e) {

            showError(e.getMessage());

        }

    }

    @FXML
    private void updateBudget() {

        Budget selected =
                budgetTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        try {

            selected.setTitle(titleField.getText());

            selected.setAmount(new BigDecimal(amountField.getText()));

            selected.setCurrency(currencyBox.getValue());

            budgetService.update(selected);

            loadBudgets();

        } catch (Exception e) {

            showError(e.getMessage());

        }

    }

    @FXML
    private void deleteBudget() {

        Budget selected =
                budgetTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        budgetService.delete(selected.getBudgetId());

        clearFields();

        loadBudgets();

    }

    private void clearFields() {

        titleField.clear();

        currencyBox.setValue(null);

    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

}
