package com.ba.budgetapp.models.DAO.Interface;

import java.util.List;

import com.ba.budgetapp.models.entities.Budget;

public interface BudgetDAO extends CrudDAO<Budget, Long> {
    List<Budget> findByUser(Long userId);
}