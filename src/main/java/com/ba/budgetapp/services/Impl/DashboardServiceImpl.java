package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.services.Interface.DashboardService;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardServiceImpl implements DashboardService {

    private final TransactionDAO transactionDAO;

    public DashboardServiceImpl() {
        transactionDAO = new TransactionDAOImpl();
    }

    @Override
    public BigDecimal getTotalIncome(Long budgetId) {
        return transactionDAO.getTotalIncome(budgetId);
    }

    @Override
    public BigDecimal getTotalExpense(Long budgetId) {
        return transactionDAO.getTotalExpense(budgetId);
    }

    @Override
    public BigDecimal getCurrentBalance(Long budgetId) {
        return transactionDAO.getCurrentBalance(budgetId);
    }

    @Override
    public long getTransactionCount(Long budgetId) {
        return transactionDAO.countTransactions(budgetId);
    }

    @Override
    public Map<String, Double> getExpensesByCategory(Long budgetId) {
        return transactionDAO.getExpensesByCategory(budgetId);
    }

    @Override
    public Map<String, Double> getMonthlyIncome(Long budgetId) {
        return transactionDAO.getMonthlyIncome(budgetId);
    }

    @Override
    public Map<String, Double> getMonthlyExpense(Long budgetId) {
        return transactionDAO.getMonthlyExpense(budgetId);
    }
}
