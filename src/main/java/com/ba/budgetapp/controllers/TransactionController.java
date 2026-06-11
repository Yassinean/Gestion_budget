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

        configureSelection();

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

    private void configureSelection() {
        transactionTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, selected) -> {
                    if (selected != null) {
                        fillForm(selected);
                    }
                });
    }
    
    private void loadCategories() {
    }

    private void loadAccounts() {
    }

    private void loadTransactionTypes() {
        typeCombo.getItems().setAll(TransactionType.values());
    }

    @FXML
    private void refreshTable() {
    }

    @FXML
    private void addTransaction() {
        try {
            Transaction transaction = buildTransactionFromForm();
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
            Transaction formTransaction = buildTransactionFromForm();
            selected.setAmount(formTransaction.getAmount());
            selected.setDescription(formTransaction.getDescription());
            selected.setTransactionDate(formTransaction.getTransactionDate());
            selected.setTransactionType(formTransaction.getTransactionType());
            selected.setCategoryId(formTransaction.getCategoryId());
            selected.setAccountId(formTransaction.getAccountId());
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

    private Transaction buildTransactionFromForm() {
        Account account = accountCombo.getValue();
        Category category = categoryCombo.getValue();
        TransactionType type = typeCombo.getValue();
        if (account == null) {
            throw new IllegalArgumentException("Compte obligatoire");
        }
        if (category == null) {
            throw new IllegalArgumentException("Catégorie obligatoire");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type obligatoire");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(amountField.getText()));
        transaction.setDescription(descriptionField.getText());
        transaction.setTransactionDate(datePicker.getValue());
        transaction.setTransactionType(type);
        transaction.setCategoryId(category.getCategoryId());
        transaction.setAccountId(account.getAccountId());
        return transaction;
    }

    private void fillForm(Transaction transaction) {
        amountField.setText(transaction.getAmount().toPlainString());
        descriptionField.setText(transaction.getDescription());
        datePicker.setValue(transaction.getTransactionDate());
        typeCombo.getSelectionModel().select(transaction.getTransactionType());
        selectCategory(transaction.getCategoryId());
        selectAccount(transaction.getAccountId());
    }

    private void selectCategory(Long categoryId) {
        categoryCombo.getSelectionModel().clearSelection();
        categoryCombo.getItems()
                .stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .findFirst()
                .ifPresent(category -> categoryCombo.getSelectionModel().select(category));
    }

    private void selectAccount(Long accountId) {
        accountCombo.getSelectionModel().clearSelection();
        accountCombo.getItems()
                .stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .ifPresent(account -> accountCombo.getSelectionModel().select(account));
    }
}
