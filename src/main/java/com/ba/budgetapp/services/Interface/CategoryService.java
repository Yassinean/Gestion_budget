package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean createCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(Long id);

    List<Category> getAllCategories();

    List<Category> getCategoriesByUser(Long userId);

    Optional<Category> getCategoryByIdForUser(Long categoryId, Long userId);
}
