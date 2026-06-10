package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.TransactionDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.services.Interface.AccountService;
import com.ba.budgetapp.services.Interface.CategoryService;
import com.ba.budgetapp.services.Interface.TransactionService;
import com.ba.budgetapp.utils.SessionManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final AccountService accountService = new AccountServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

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
        return transactionDAO.updateForUser(transaction, currentUserId());
    }

    @Override
    public boolean deleteTransaction(Long id) {
        return transactionDAO.deleteForUser(id, currentUserId());
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId){
        return transactionDAO.findAllByUser(userId);
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return transactionDAO.findById(transactionId)
                .filter(transaction -> accountService
                        .getAccountByIdForUser(transaction.getAccountId(), currentUserId())
                        .isPresent());
    }

    @Override
    public List<Transaction> searchTransactions(String keyword) {
        return transactionDAO.search(keyword, currentUserId());
    }

    @Override
    public List<Transaction> getTransactionsByCategory(Long categoryId) {
        return transactionDAO.findByCategory(categoryId, currentUserId());
    }

    @Override
    public List<Transaction> getTransactionsByDateRange( LocalDate start, LocalDate end) {
        return transactionDAO.findByDateRange(start, end, currentUserId());
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction invalide");
        }
        Long userId = currentUserId();
        if (accountService
                .getAccountByIdForUser(transaction.getAccountId(), userId)
                .filter(account -> account.isActive())
                .isEmpty()) {
            throw new IllegalStateException(
                    "Le compte est désactivé ou inaccessible."
            );
        }
        if (categoryService
                .getCategoryByIdForUser(transaction.getCategoryId(), userId)
                .isEmpty()) {
            throw new IllegalStateException("Catégorie inaccessible.");
        }
    }

    private Long currentUserId() {
        Long userId = SessionManager.getCurrentUserId();
        if (userId == null) {
            throw new IllegalStateException("Aucun utilisateur connecté.");
        }
        return userId;
    }
}
