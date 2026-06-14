package com.ba.budgetapp.services.Impl;


import java.util.Optional;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Interface.AccountService;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public boolean register(Account account) {
        account.validate();
        if (accountDAO.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username déjà utilisé.");
        }
        if (accountDAO.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé.");
        }
        return accountDAO.create(account);
    }

    @Override
    public Optional<Account> authenticate(String username, String email, String password) {
        Optional<Account> account = accountDAO.findByUsername(username);
        if (account.isPresent() && account.get().getEmail().equals(email) && account.get().getPassword().equals(password)) {
            return account;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDAO.findById(id);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    @Override
    public boolean update(Account account) {
        account.validate();
        return accountDAO.update(account);
    }

    @Override
    public boolean delete(Long id) {
        return accountDAO.delete(id);
    }

}