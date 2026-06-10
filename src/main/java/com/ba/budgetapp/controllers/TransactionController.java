package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionType;
import com.ba.budgetapp.services.Interface.AccountService;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.services.Interface.TransactionService;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableColumn<Transaction, String> categoryColumn;

    @FXML
    private ComboBox<Account> accountCombo;

    @FXML
    private ComboBox<Category> categoryCombo;

    @FXML
    private ComboBox<TransactionType> typeCombo;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Long> idColumn;

    @FXML
    private TableColumn<Transaction, LocalDate> dateColumn;

    @FXML
    private TableColumn<Transaction, String> descriptionColumn;

    @FXML
    private TableColumn<Transaction, TransactionType> typeColumn;

    @FXML
    private TableColumn<Transaction, BigDecimal> amountColumn;

    private final TransactionService transactionService = ServiceFactory.transactionService();
    private final CategoryService categoryService = ServiceFactory.categoryService();
    private final AccountService accountService = ServiceFactory.accountService();

    @FXML
    public void initialize() {

        configureTable();

        loadCategories();

        loadAccounts();

        loadTransactionTypes();

        refreshTable();
    }

    private void configureTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }
    
    private void loadCategories() {
        Long userId = SessionManager.getCurrentUserId();
        categoryCombo.getItems().setAll(categoryService.getCategoriesByUser(userId));
    }

    private void loadAccounts() {
        Long userId = SessionManager.getCurrentUserId();
        accountCombo.getItems().setAll(accountService.getActiveAccountsByUser(userId));
    }

    private void loadTransactionTypes() {
        typeCombo.getItems().setAll(TransactionType.values());
    }

    @FXML
    private void refreshTable() {
        transactionTable.getItems().setAll(transactionService.findAllByUserId(SessionManager.getCurrentUserId()));
    }

    @FXML
    private void addTransaction() {
        try {
            Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal(amountField.getText()));
            transaction.setDescription(descriptionField.getText());
            transaction.setTransactionDate(datePicker.getValue());
            transaction.setTransactionType(typeCombo.getValue());
            transaction.setCategoryId(categoryCombo.getValue().getCategoryId());
            transaction.setAccountId(accountCombo.getValue().getAccountId());
            transactionService.createTransaction(transaction);
            refreshTable();
            clearForm();

        } catch (Exception e) {
            AlertUtil.showError("Données invalides : " + e.getMessage());
        }
    }

    @FXML
    private void updateTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Veuillez sélectionner une transaction");
            return;
        }
        try {
            selected.setAmount(new BigDecimal(amountField.getText()));
            selected.setDescription(descriptionField.getText());
            selected.setTransactionDate(datePicker.getValue());
            selected.setTransactionType(typeCombo.getValue());
            selected.setCategoryId(categoryCombo.getValue().getCategoryId());
            selected.setAccountId(accountCombo.getValue().getAccountId());
            transactionService.updateTransaction(selected);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @FXML
    private void deleteTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Veuillez sélectionner une transaction");
            return;
        }
        if (!AlertUtil.showConfirmation("Supprimer cette transaction ?")) {
            return;
        }
        try {
            transactionService.deleteTransaction(selected.getTransactionId());
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de la suppression : " + e.getMessage());
        }
        refreshTable();
    }

    @FXML
    private void searchTransaction() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            refreshTable();
            return;
        }

        transactionTable.getItems().setAll(
                transactionService.searchTransactions(query)
        );
    }

    private void clearForm() {
        amountField.clear();
        descriptionField.clear();
        datePicker.setValue(null);
        categoryCombo.getSelectionModel().clearSelection();
        accountCombo.getSelectionModel().clearSelection();
        typeCombo.getSelectionModel().clearSelection();
    }
}
