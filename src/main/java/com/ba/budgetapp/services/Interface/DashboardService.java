package com.ba.budgetapp.services.Interface;

import java.math.BigDecimal;

public interface DashboardService {
    BigDecimal getTotalIncome();

    BigDecimal getTotalExpense();

    BigDecimal getCurrentBalance();

    long getTransactionCount();
}
