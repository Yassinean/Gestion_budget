package com.ba.budgetapp.models.DAO.Interface;

import java.util.List;
import java.util.Optional;

import com.ba.budgetapp.models.entities.Budget;

public interface BudgetDAO extends CrudDAO<Budget, Long> {
    List<Budget> findByOwnerId(Long ownerId);
    Optional<Budget> findDefaultBudget(Long ownerId);
}