package com.ba.budgetapp.services.Interface;

import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountService {
    boolean createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(Long id);
    Optional<Account> getAccountsByUser(Long userId);
}
