package com.ba.budgetapp.models.DAO.Interface;

import com.ba.budgetapp.models.entities.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO extends CrudDAO<Transaction, Long> {

    BigDecimal getTotalIncome();
    BigDecimal getTotalExpense();
    BigDecimal getCurrentBalance();
    long countTransactions();
    List<Transaction> findByCategory(Long categoryId);
    List<Transaction> findByDateRange(LocalDate start, LocalDate end);
    List<Transaction> search(String keyword);
}