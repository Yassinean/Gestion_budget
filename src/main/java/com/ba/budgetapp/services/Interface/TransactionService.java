package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    boolean createTransaction(Transaction transaction);

    boolean updateTransaction(Transaction transaction);

    boolean deleteTransaction(Long id);

    List<Transaction> getAllTransactions();

    List<Transaction> searchTransactions(String keyword);

    List<Transaction> getTransactionsByCategory(Long categoryId);

    List<Transaction> getTransactionsByDateRange(
            LocalDate start,
            LocalDate end);

}
