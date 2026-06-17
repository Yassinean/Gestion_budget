package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.services.Interface.BudgetService;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CategoriesController {

    @FXML
    private TextField categoryNameField;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Long> idColumn;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private TableColumn<Category, String> nameBudgetColumn;

    @FXML
    private ComboBox<Budget> budgetCombo;

    private final CategoryService categoryService = ServiceFactory.categoryService();
    private final BudgetService budgetService = ServiceFactory.budgetService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        nameBudgetColumn.setCellValueFactory(new PropertyValueFactory<>("budgetTitle"));
        categoryTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, selected) -> {
                    if (selected != null) {
                        categoryNameField.setText(selected.getTitle());
                    }
                });

        loadBudgets();
        refreshTable();
    }

    @FXML
    private void addCategory() {
        String name = categoryNameField.getText().trim();
        if (name.isBlank()) {
            AlertUtil.showError("Le nom de la catégorie ne peut pas être vide.");
            return;
        }

        Long budgetId = SessionManager.getCurrentBudgetId();
        if (budgetId == null) {
            AlertUtil.showError("Aucun budget actif n'est sélectionné.");
            return;
        }

        try {
            if (categoryService.findByTitle(budgetId, name).isPresent()) {
                AlertUtil.showError("Cette catégorie existe déjà.");
                return;
            }
            Budget selectedBudget = budgetCombo.getValue();

            if(selectedBudget == null){
                AlertUtil.showError("Veuillez sélectionner un budget");
                return;
            }
            Category category = new Category();
            category.setBudgetId(selectedBudget.getBudgetId());
            category.setTitle(categoryNameField.getText());
            categoryService.create(category);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @FXML
    private void updateCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Veuillez sélectionner une catégorie");
            return;
        }
        try {
            selected.setTitle(categoryNameField.getText());
            categoryService.update(selected);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @FXML
    private void deleteCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Veuillez sélectionner une catégorie");
            return;
        }
        if (!AlertUtil.showConfirmation("Supprimer cette catégorie ?")) {
            return;
        }
        try {
            categoryService.delete(selected.getCategoryId());
            refreshTable();
            clearForm();
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @FXML
    private void refreshTable() {
        Long accountId = SessionManager.getCurrentAccountId();
        categoryTable.getItems().setAll(
                accountId == null ? List.of() : categoryService.findByOwnerId(accountId)
        );
    }

    private void loadBudgets() {
        Long accountId = SessionManager.getCurrentAccountId();
        budgetCombo.getItems().setAll(
                accountId == null ? List.of() : budgetService.findByOwnerId(accountId)
        );
    }

    private void clearForm() {
        categoryNameField.clear();
        categoryTable.getSelectionModel().clearSelection();
    }

}
