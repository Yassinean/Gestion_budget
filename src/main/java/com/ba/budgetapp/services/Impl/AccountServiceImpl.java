package com.ba.budgetapp.services.Impl;


import java.util.List;
import java.util.Optional;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Interface.AccountService;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public boolean createAccount(Account account) {
        validateAccount(account);
        return accountDAO.create(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        validateAccount(account);
        return accountDAO.update(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        return accountDAO.delete(id);
    }

    @Override
    public List<Account> getAccountsByUser(Long userId) {
        return accountDAO.findByUserId(userId);
    }

    @Override
    public List<Account> getActiveAccountsByUser(Long userId) {
        return accountDAO.findActiveByUserId(userId);
    }

    @Override
    public Optional<Account> getAccountByIdForUser(Long accountId, Long userId) {
        return accountDAO.findByIdAndUserId(accountId, userId);
    }

    @Override
    public boolean isAccountActive(Long accountId) {
        Optional<Account> accountOpt = accountDAO.findById(accountId);
        return accountOpt.map(Account::isActive).orElse(false);
    }

    private void validateAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Compte invalide");
        }
    }
}
