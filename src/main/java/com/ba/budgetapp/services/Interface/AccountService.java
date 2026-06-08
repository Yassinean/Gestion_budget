package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Account;

import java.util.List;

public interface AccountService {

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(Long id);

}
