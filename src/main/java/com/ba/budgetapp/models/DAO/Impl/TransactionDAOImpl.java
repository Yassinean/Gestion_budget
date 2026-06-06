package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionDAOImpl implements CrudDAO<Transaction, Long> {
    @Override
    public boolean create(Transaction entity) {
        return false;
    }

    @Override
    public Optional<Transaction> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Transaction entity) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}