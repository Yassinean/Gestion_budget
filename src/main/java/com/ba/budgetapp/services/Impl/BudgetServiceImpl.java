package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.BudgetDAO;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Interface.BudgetService;

import java.util.List;

public class BudgetServiceImpl implements BudgetService {

    private final BudgetDAO budgetDAO;

    public BudgetServiceImpl() {
        this.budgetDAO = new BudgetDAOImpl();
    }

    @Override
    public boolean createBudget(Budget budget) {
        validateBudget(budget);
        return budgetDAO.create(budget);
    }

    @Override
    public boolean updateBudget(Budget budget) {
        validateBudget(budget);
        return budgetDAO.update(budget);
    }

    @Override
    public boolean deleteBudget(Long id) {
        return budgetDAO.delete(id);
    }

    @Override
    public List<Budget> findByUser(Long userId) {
        return budgetDAO.findByUser(userId);
    }

    private void validateBudget(Budget Budget) {
        if (Budget == null) {
            throw new IllegalArgumentException("Budget invalide");
        }
    }
}
