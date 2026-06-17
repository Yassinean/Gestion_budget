package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetService {
        boolean create(Budget budget);
        boolean update(Budget budget);
        boolean delete(Long id);
        Optional<Budget> findById(Long id);
        List<Budget> findByOwnerId(Long ownerId);
        Optional<Budget> findDefaultBudget(Long ownerId);
        List<Budget> findAll();
}
