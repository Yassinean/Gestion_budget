package com.ba.budgetapp.models.DAO.Interface;

import com.ba.budgetapp.models.entities.Category;
import com.ba.budgetapp.models.entities.TransactionType;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO extends CrudDAO<Category, Long> {

    List<Category> findByBudgetId(Long budgetId);
    List<Category> findByType(Long budgetId,TransactionType type);
    Optional<Category> findByTitle(Long budgetId, String title);
    boolean existsByTitle(Long budgetId, String title);
}