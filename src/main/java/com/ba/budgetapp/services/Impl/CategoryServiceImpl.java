package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.CategoryDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.services.Interface.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public boolean createCategory(Category category) {
        validateCategory(category);
        return categoryDAO.create(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        validateCategory(category);
        return categoryDAO.update(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        return categoryDAO.delete(id);
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
}
