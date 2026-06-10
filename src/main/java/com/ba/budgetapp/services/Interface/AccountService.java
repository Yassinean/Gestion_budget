package com.ba.budgetapp.services.Interface;

import java.util.List;
import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountService {
    boolean createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(Long id);
    List<Account> getAccountsByUser(Long userId);
    List<Account> getActiveAccountsByUser(Long userId);
    Optional<Account> getAccountByIdForUser(Long accountId, Long userId);
    boolean isAccountActive(Long accountId);
}
