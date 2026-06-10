package com.ba.budgetapp.models.DAO.Interface;

import com.ba.budgetapp.models.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO extends CrudDAO<Category, Long> {
    List<Category> findByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long categoryId, Long userId);
}
