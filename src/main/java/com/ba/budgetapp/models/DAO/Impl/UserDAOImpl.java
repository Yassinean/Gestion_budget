package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements CrudDAO<User, Long> {
    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}