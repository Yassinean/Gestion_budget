package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.utils.AlertUtil;
import com.ba.budgetapp.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriesController {

    @FXML
    private TextField categoryNameField;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Long> idColumn;

    @FXML
    private TableColumn<Category, String> nameColumn;

    private final CategoryService categoryService = ServiceFactory.categoryService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, selected) -> {
                    if (selected != null) {
                        categoryNameField.setText(selected.getCategoryName());
                    }
                });
        refreshTable();
    }

    @FXML
    private void addCategory() {
        try {
            Category category = new Category();
            category.setCategoryName(categoryNameField.getText());
            category.setUserId(currentUserId());
            categoryService.createCategory(category);
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
            selected.setCategoryName(categoryNameField.getText());
            categoryService.updateCategory(selected);
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
            categoryService.deleteCategory(selected.getCategoryId());
            refreshTable();
            clearForm();
        } catch (Exception e) {
            AlertUtil.showError("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @FXML
    private void refreshTable() {
        categoryTable.getItems().setAll(categoryService.getCategoriesByUser(currentUserId()));
    }

    private void clearForm() {
        categoryNameField.clear();
        categoryTable.getSelectionModel().clearSelection();
    }

    private Long currentUserId() {
        Long userId = 1L;
        // Long userId = SessionManager.getCurrentUserId();
        // if (userId == null) {
        //     throw new IllegalStateException("Aucun utilisateur connecté.");
        // }
        return userId;
    }
}
