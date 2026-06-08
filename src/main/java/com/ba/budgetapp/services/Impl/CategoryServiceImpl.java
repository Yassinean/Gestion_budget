package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.CategoryDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.services.Interface.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public boolean createCategory(Category category) {
        return categoryDAO.create(category);
    }

    @Override
    public boolean updateCategory(Category category) {
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
}
