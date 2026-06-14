package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionService {

    boolean create(Transaction transaction);

    boolean update(Transaction transaction);

    boolean delete(Long transactionId);

    Optional<Transaction> findById(Long transactionId);

    List<Transaction> findAll();

    List<Transaction> findByBudgetId(Long budgetId);

    List<Transaction> findByCategory(Long categoryId);

    List<Transaction> findByDateRange(Long budgetId, LocalDate start, LocalDate end);

    List<Transaction> search(Long budgetId, String keyword);

    List<TransactionView> searchView(Long budgetId, String keyword);

    List<TransactionView> findAllView(Long budgetId);

    BigDecimal getTotalIncome(Long budgetId);

    BigDecimal getTotalExpense(Long budgetId);

    BigDecimal getCurrentBalance(Long budgetId);

    long countTransactions(Long budgetId);

    Map<String, Double> getExpensesByCategory(Long budgetId);

    Map<String, Double> getMonthlyIncome(Long budgetId);

    Map<String, Double> getMonthlyExpense(Long budgetId);
}
