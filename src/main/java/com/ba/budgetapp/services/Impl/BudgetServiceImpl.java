package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.BudgetDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.BudgetDAO;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Interface.BudgetService;

import java.util.List;
import java.util.Optional;

public class BudgetServiceImpl implements BudgetService {

    private final BudgetDAO budgetDAO;

    public BudgetServiceImpl(BudgetDAO budgetDAO) {
        this.budgetDAO = budgetDAO;
    }

    @Override
    public boolean create(Budget budget) {
        budget.validate();
        return budgetDAO.create(budget);
    }

    @Override
    public boolean update(Budget budget) {
        budget.validate();
        return budgetDAO.update(budget);
    }

    @Override
    public boolean delete(Long id) {
        return budgetDAO.delete(id);
    }

    @Override
    public Optional<Budget> findById(Long id) {
        return budgetDAO.findById(id);
    }

    @Override
    public List<Budget> findByOwnerId(Long ownerId) {
        return budgetDAO.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Budget> findDefaultBudget(Long ownerId) {
        return budgetDAO.findDefaultBudget(ownerId);
    }

    @Override
    public List<Budget> findAll() {
        return budgetDAO.findAll();
    }
}