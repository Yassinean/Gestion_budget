package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Account;

import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements CrudDAO<Account, Long> {
    @Override
    public boolean create(Account entity) {
        return false;
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Account entity) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}