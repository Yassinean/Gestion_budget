package com.ba.budgetapp.services.Interface;

import java.util.Optional;

import com.ba.budgetapp.models.entities.Account;

public interface AccountService {    
    boolean register(Account account);
    Optional<Account> authenticate(String username, String email, String password);
    Optional<Account> findById(Long id);
    Optional<Account> findByUsername(String username);
    boolean update(Account account);
    boolean delete(Long id);
}
