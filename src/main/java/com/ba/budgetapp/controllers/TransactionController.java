package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionType;
import com.ba.budgetapp.models.entities.TransactionView;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.services.Interface.TransactionService;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionController {
    
    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Category> categoryCombo;

    @FXML
    private ComboBox<TransactionType> typeCombo;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<TransactionView> transactionTable;

    @FXML
    private TableColumn<TransactionView, Long> idColumn;

    @FXML
    private TableColumn<TransactionView, LocalDate> dateColumn;

    @FXML
    private TableColumn<TransactionView, String> categoryColumn;

    @FXML
    private TableColumn<TransactionView, String> typeColumn;

    @FXML
    private TableColumn<TransactionView, BigDecimal> amountColumn;

    @FXML
    private TableColumn<TransactionView, String> descriptionColumn;

    private final TransactionService transactionService;

    private final CategoryService categoryService;

    public TransactionController() {
        this(ServiceFactory.transactionService(), ServiceFactory.categoryService());
    }

    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @FXML
    public void initialize() {
        configureTable();
        loadCategories();
        loadTransactionTypes();
        refreshTable();
    }

    private void configureTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadCategories() {
        Long budgetId = SessionManager.getCurrentBudgetId();
        categoryCombo.getItems().setAll(categoryService.findByBudgetId(budgetId));
    }

    private void loadTransactionTypes() {
        typeCombo.getItems().setAll(TransactionType.values());
    }

    @FXML
    private void refreshTable() {
        Long budgetId = SessionManager.getCurrentBudgetId();
        transactionTable.getItems().setAll(transactionService.findAllView(budgetId));
    }


    @FXML
    private void addTransaction() {
        try {

            Category category = categoryCombo.getValue();
            TransactionType type = typeCombo.getValue();

            if (amountField.getText() == null || amountField.getText().trim().isBlank()) {
                AlertUtil.showError("Montant obligatoire.");
                return;
            }

            if (category == null) {
                AlertUtil.showError("Veuillez sélectionner une catégorie.");
                return;
            }

            if (type == null) {
                AlertUtil.showError("Veuillez sélectionner un type.");
                return;
            }

            if (datePicker.getValue() == null) {
                AlertUtil.showError("Date obligatoire.");
                return;
            }

            Transaction transaction = new Transaction();

            transaction.setBudgetId(SessionManager.getCurrentBudgetId());
            transaction.setCategoryId(category.getCategoryId());
            transaction.setTransactionType(type);

            // IMPORTANT
            transaction.setAmount(Long.parseLong(amountField.getText().trim()));

            transaction.setDescription(descriptionField.getText());
            transaction.setTransactionDate(datePicker.getValue());

            transactionService.create(transaction);

            AlertUtil.showInfo("Transaction ajoutée.");
            clearForm();
            refreshTable();

        } catch (NumberFormatException e) {
            AlertUtil.showError("Le montant doit être un nombre entier valide.");
        } catch (Exception e) {
            AlertUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void updateTransaction() {
        try {
            TransactionView selected = transactionTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                AlertUtil.showError("Veuillez sélectionner une transaction dans la table.");
                return;
            }

            if (amountField.getText() == null || amountField.getText().isBlank()) {
                AlertUtil.showError("Montant obligatoire.");
                return;
            }
            if (datePicker.getValue() == null) {
                AlertUtil.showError("Date obligatoire.");
                return;
            }

            Category category = categoryCombo.getValue();
            if (category == null) {
                AlertUtil.showError("Veuillez sélectionner une catégorie.");
                return;
            }

            TransactionType type = typeCombo.getValue();
            if (type == null) {
                AlertUtil.showError("Veuillez sélectionner un type.");
                return;
            }

            Transaction transaction = new Transaction();
            transaction.setTransactionId(selected.getTransactionId());
            transaction.setBudgetId(SessionManager.getCurrentBudgetId());
            transaction.setCategoryId(category.getCategoryId());
            transaction.setTransactionType(typeCombo.getValue());
            transaction.setAmount(Long.parseLong(amountField.getText()));
            transaction.setDescription(descriptionField.getText());
            transaction.setTransactionDate(datePicker.getValue());
            transactionService.update(transaction);
            AlertUtil.showInfo("Transaction modifiée.");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            AlertUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void deleteTransaction() {
        try {
            TransactionView selected = transactionTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                AlertUtil.showError("Veuillez sélectionner une transaction dans la table.");
                return;
            }
            transactionService.delete(selected.getTransactionId());
            AlertUtil.showInfo("Transaction supprimée.");
            clearForm();
            refreshTable();

        } catch (Exception e) {
            AlertUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void searchTransaction() {
        Long budgetId = SessionManager.getCurrentBudgetId();
        String keyword = searchField.getText().trim();
        if (keyword.isBlank()) {
            refreshTable();
            return;
        }
        transactionTable.getItems().setAll(transactionService.searchView(budgetId,keyword));
    }

    private void clearForm() {
        amountField.clear();
        descriptionField.clear();
        datePicker.setValue(null);
        categoryCombo.getSelectionModel().clearSelection();
        typeCombo.getSelectionModel().clearSelection();
    }
}
