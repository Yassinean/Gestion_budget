package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Category;

import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CrudDAO<Category, Long> {
    @Override
    public boolean create(Category entity) {
        return false;
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Category entity) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}