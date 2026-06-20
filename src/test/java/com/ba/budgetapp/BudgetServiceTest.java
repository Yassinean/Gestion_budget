package com.ba.budgetapp;

import com.ba.budgetapp.models.DAO.Interface.BudgetDAO;
import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Impl.BudgetServiceImpl;
import com.ba.budgetapp.services.Interface.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private BudgetDAO budgetDAO;

    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetServiceImpl(budgetDAO);
    }

    @Test
    void shouldCreateBudget() {

        Budget budget = new Budget();
        budget.setOwnerId(2L);
        budget.setTitle("Test");
        budget.setAmount(new BigDecimal(900));
        budget.setCurrency("MAD");
        when(budgetDAO.create(any(Budget.class))).thenReturn(true);

        boolean result = budgetService.create(budget);

        assertTrue(result);

        verify(budgetDAO).create(any(Budget.class));
    }
}