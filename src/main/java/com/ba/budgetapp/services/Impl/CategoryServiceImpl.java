package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.CategoryDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.TransactionType;
import com.ba.budgetapp.services.Interface.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public boolean create(Category category) {
        validateCategory(category);
        return categoryDAO.create(category);
    }

    @Override
    public boolean update(Category category) {
        validateCategory(category);
        return categoryDAO.update(category);
    }

    @Override
    public boolean delete(Long categoryId) {
        return categoryDAO.delete(categoryId);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> findByBudgetId(Long budgetId) {
        return categoryDAO.findByBudgetId(budgetId);
    }

    @Override
    public List<Category> findByOwnerId(Long ownerId) {
        return categoryDAO.findByOwnerId(ownerId);
    }

    @Override
    public List<Category> findByType(Long budgetId, TransactionType type) {
        return categoryDAO.findByType(budgetId,type);
    }

    @Override
    public Optional<Category> findByTitle(Long budgetId, String categoryName){
        return categoryDAO.findByTitle(budgetId, categoryName);
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Catégorie invalide");
        }
    }

}
