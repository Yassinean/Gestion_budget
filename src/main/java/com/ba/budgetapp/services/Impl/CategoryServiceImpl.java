package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.CategoryDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Interface.AccountService;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.utils.SessionManager;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public boolean createCategory(Category category) {
        validateCategory(category);
        requireActiveAccount(category.getUserId());
        if (!categoryDAO.create(category)) {
            throw new IllegalStateException("Impossible d'ajouter la catégorie.");
        }
        return true;
    }

    @Override
    public boolean updateCategory(Category category) {
        validateCategory(category);
        Long userId = currentUserId();
        requireActiveAccount(userId);
        if (categoryDAO.findByIdAndUserId(category.getCategoryId(), userId).isEmpty()) {
            throw new IllegalStateException("Catégorie inaccessible.");
        }
        if (!categoryDAO.updateForUser(category, userId)) {
            throw new IllegalStateException("Impossible de modifier la catégorie.");
        }
        return true;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Long userId = currentUserId();
        requireActiveAccount(userId);
        if (categoryDAO.findByIdAndUserId(id, userId).isEmpty()) {
            throw new IllegalStateException("Catégorie inaccessible.");
        }
        if (!categoryDAO.deleteForUser(id, userId)) {
            throw new IllegalStateException("Impossible de supprimer la catégorie.");
        }
        return true;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public List<Category> getCategoriesByUser(Long userId) {
        return categoryDAO.findByUserId(userId);
    }

    @Override
    public Optional<Category> getCategoryByIdForUser(Long categoryId, Long userId) {
        return categoryDAO.findByIdAndUserId(categoryId, userId);
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Catégorie invalide");
        }
    }

    private void requireActiveAccount(Long userId) {
        
    }

    private Long currentUserId() {
        Long userId = null;
        if (userId == null) {
            throw new IllegalStateException("Aucun utilisateur connecté.");
        }
        return userId;
    }
}
