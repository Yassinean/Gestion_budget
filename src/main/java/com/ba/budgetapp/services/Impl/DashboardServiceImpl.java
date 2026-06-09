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
    public BigDecimal getTotalIncome(Long userId) {
        return transactionDAO.getTotalIncome(userId);
    }

    @Override
    public BigDecimal getTotalExpense(Long userId) {
        return transactionDAO.getTotalExpense(userId);
    }

    @Override
    public BigDecimal getCurrentBalance(Long userId) {
        return transactionDAO.getCurrentBalance(userId);
    }

    @Override
    public long getTransactionCount(Long userId) {
        return transactionDAO.countTransactions(userId);
    }

    @Override
    public Map<String, Double> getExpensesByCategory(Long userId) {
        return transactionDAO.getExpensesByCategory(userId);
    }

    @Override
    public Map<String, Double> getMonthlyIncome(Long userId) {
        return transactionDAO.getMonthlyIncome(userId);
    }

    @Override
    public Map<String, Double> getMonthlyExpense(Long userId) {
        return transactionDAO.getMonthlyExpense(userId);
    }
}
