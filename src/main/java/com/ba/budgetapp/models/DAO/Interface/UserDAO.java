package com.ba.budgetapp.models.DAO.Interface;

import java.util.Optional;

import com.ba.budgetapp.models.entities.User;

public interface UserDAO extends CrudDAO<User, Long> {

    Optional<User> findByUserName(String string);
}