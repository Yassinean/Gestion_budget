package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Budget;

import java.util.List;

public interface BudgetService {
    boolean createBudget(Budget budget);
    boolean updateBudget(Budget budget);
    boolean deleteBudget(Long id);
    List<Budget> findByUser(Long userId);
}
