package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionView;
import com.ba.budgetapp.services.Interface.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public boolean create(Transaction transaction) {
        transaction.validate();
        return transactionDAO.create(transaction);
    }

    @Override
    public boolean update(Transaction transaction) {
        transaction.validate();
        return transactionDAO.update(transaction);
    }

    @Override
    public boolean delete(Long transactionId) {
        return transactionDAO.delete(transactionId);
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return transactionDAO.findById(transactionId);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }

    @Override
    public List<Transaction> findByBudgetId(Long budgetId) {
        return transactionDAO.findByBudgetId(budgetId);
    }

    @Override
    public List<Transaction> findByCategory(Long categoryId) {
        return transactionDAO.findByCategory(categoryId);
    }

    @Override
    public List<Transaction> findByDateRange(Long budgetId,LocalDate start,LocalDate end) {

        return transactionDAO.findByDateRange(budgetId,start,end);
    }

    @Override
    public List<Transaction> search(Long budgetId,String keyword) {

        return transactionDAO.search(budgetId,keyword);
    }

    @Override
    public List<TransactionView> searchView(Long budgetId, String keyword) {
        return transactionDAO.searchView(budgetId, keyword);
    }

    @Override
    public List<TransactionView> findAllView(Long budgetId) {
        return transactionDAO.findAllView(budgetId);
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
    public long countTransactions(Long budgetId) {
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