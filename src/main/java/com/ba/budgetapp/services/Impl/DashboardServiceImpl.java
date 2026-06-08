package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.services.Interface.DashboardService;

import java.math.BigDecimal;

public class DashboardServiceImpl implements DashboardService {

    private final TransactionDAO transactionDAO;

    public DashboardServiceImpl() {
        transactionDAO = new TransactionDAOImpl();
    }

    @Override
    public BigDecimal getTotalIncome() {
        return transactionDAO.getTotalIncome();
    }

    @Override
    public BigDecimal getTotalExpense() {
        return transactionDAO.getTotalExpense();
    }

    @Override
    public BigDecimal getCurrentBalance() {
        return transactionDAO.getCurrentBalance();
    }

    @Override
    public long getTransactionCount() {
        return transactionDAO.countTransactions();
    }

}
