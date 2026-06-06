package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Budget;

import java.util.List;
import java.util.Optional;

public class BudgetDAOImpl implements CrudDAO<Budget, Long> {
    @Override
    public boolean create(Budget entity) {
        return false;
    }

    @Override
    public Optional<Budget> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Budget> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Budget entity) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}