package com.ba.budgetapp.models.DAO.Interface;

import java.util.List;
import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountDAO extends CrudDAO<Account, Long> {

    List<Account> findByUserId(Long userId);

    List<Account> findActiveByUserId(Long userId);

    Optional<Account> findByIdAndUserId(Long accountId, Long userId);
}
