package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.services.Interface.TransactionService;

import java.time.LocalDate;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;

    public TransactionServiceImpl() {
        this.transactionDAO = new TransactionDAOImpl();
    }

    @Override
    public boolean createTransaction(Transaction transaction) {
        validateTransaction(transaction);
        return transactionDAO.create(transaction);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        validateTransaction(transaction);
        return transactionDAO.update(transaction);
    }

    @Override
    public boolean deleteTransaction(Long id) {
        return transactionDAO.delete(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }

    @Override
    public List<Transaction> searchTransactions(String keyword) {
        return transactionDAO.search(keyword);
    }

    @Override
    public List<Transaction> getTransactionsByCategory(Long categoryId) {
        return transactionDAO.findByCategory(categoryId);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange( LocalDate start, LocalDate end) {
        return transactionDAO.findByDateRange(start, end);
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction invalide");
        }
    }
}
