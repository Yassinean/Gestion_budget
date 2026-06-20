package com.ba.budgetapp;

import com.ba.budgetapp.models.DAO.Impl.AccountDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.entities.Account;
import com.ba.budgetapp.services.Impl.AccountServiceImpl;
import com.ba.budgetapp.services.Interface.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(new AccountDAOImpl());
    }

    @Test
    void shouldCreateAccountWithHashedPassword() {

        Account acc = new Account();
        acc.setUsername("yassine");
        acc.setEmail("yassine@gmail.com");
        acc.setPassword("1234");

        when(accountDAO.create(any(Account.class))).thenReturn(true);

        boolean result = accountService.register(acc);

        assertTrue(result);

        verify(accountDAO).create(argThat(a ->
                a.getPassword() != null &&
                        !a.getPassword().equals("1234")
        ));
    }
}