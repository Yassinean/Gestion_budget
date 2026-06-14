package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.TransactionType;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    
    boolean create(Category category);
    boolean update(Category category);
    boolean delete(Long id);
    Optional<Category> findById(Long id);
    List<Category> findByBudgetId(Long budgetId);
    List<Category> findByType(Long budgetId,TransactionType type);
    Optional<Category> findByTitle(Long budgetId,String categoryName);
}
