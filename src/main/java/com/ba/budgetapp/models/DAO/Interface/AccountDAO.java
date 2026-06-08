package com.ba.budgetapp.models.DAO.Interface;

import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountDAO extends CrudDAO<Account, Long> {

    Optional<Account> findByUserId(Long userId);
    
}