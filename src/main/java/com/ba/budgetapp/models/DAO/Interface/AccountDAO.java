package com.ba.budgetapp.models.DAO.Interface;

import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountDAO extends CrudDAO<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
