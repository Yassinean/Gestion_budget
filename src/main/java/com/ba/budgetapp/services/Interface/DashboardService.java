package com.ba.budgetapp.services.Interface;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {
    BigDecimal getTotalIncome(Long budgetId);

    BigDecimal getTotalExpense(Long budgetId);

    BigDecimal getCurrentBalance(Long budgetId);

    long getTransactionCount(Long budgetId);

    Map<String, Double> getExpensesByCategory(Long budgetId);

    Map<String, Double> getMonthlyIncome(Long budgetId);

    Map<String, Double> getMonthlyExpense(Long budgetId);
}
