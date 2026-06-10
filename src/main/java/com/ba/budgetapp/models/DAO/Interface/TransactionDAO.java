package com.ba.budgetapp.models.DAO.Interface;

import com.ba.budgetapp.models.entities.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionDAO extends CrudDAO<Transaction, Long> {

    BigDecimal getTotalIncome(Long userId);
    BigDecimal getTotalExpense(Long userId);
    BigDecimal getCurrentBalance(Long userId);
    long countTransactions(Long userId);
    List<Transaction> findByCategory(Long categoryId);
    List<Transaction> findByDateRange(LocalDate start, LocalDate end);
    List<Transaction> search(String keyword);
    Map<String, Double> getExpensesByCategory(Long userId);
    Map<String, Double> getMonthlyIncome(Long userId);
    Map<String, Double> getMonthlyExpense(Long userId);
    List<Transaction> findAllByUser(Long userId);
}