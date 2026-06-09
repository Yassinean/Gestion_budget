package com.ba.budgetapp.services.Interface;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {
    BigDecimal getTotalIncome(Long userId);

    BigDecimal getTotalExpense(Long userId);

    BigDecimal getCurrentBalance(Long userId);

    long getTransactionCount(Long userId);

    Map<String, Double> getExpensesByCategory(Long userId);

    Map<String, Double> getMonthlyIncome(Long userId);

    Map<String, Double> getMonthlyExpense(Long userId);
}
