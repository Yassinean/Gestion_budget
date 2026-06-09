package com.ba.budgetapp.services.Impl;


import java.util.Optional;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Interface.AccountService;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public boolean createAccount(Account account) {
        return accountDAO.create(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountDAO.update(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        return accountDAO.delete(id);
    }

    @Override
    public Optional<Account> getAccountsByUser(Long userId) {
        return accountDAO.findByUserId(userId);
    }

}
