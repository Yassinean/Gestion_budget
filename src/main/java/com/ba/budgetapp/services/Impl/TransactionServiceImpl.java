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
        if (!transactionDAO.create(transaction)) {
            throw new IllegalStateException("Impossible d'ajouter la transaction.");
        }
        return true;
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        Transaction existing = getOwnedTransaction(transaction.getTransactionId());
        requireActiveAccount(existing.getAccountId());
        validateTransaction(transaction);
        if (!transactionDAO.updateForUser(transaction, currentUserId())) {
            throw new IllegalStateException("Impossible de modifier la transaction.");
        }
        return true;
    }

    @Override
    public boolean deleteTransaction(Long id) {
        Transaction existing = getOwnedTransaction(id);
        requireActiveAccount(existing.getAccountId());
        if (!transactionDAO.deleteForUser(id, currentUserId())) {
            throw new IllegalStateException("Impossible de supprimer la transaction.");
        }
        return true;
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId){
        return transactionDAO.findAllByUser(userId);
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return null;
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
        requireActiveAccount(transaction.getAccountId());
        if (categoryService
                .getCategoryByIdForUser(transaction.getCategoryId(), userId)
                .isEmpty()) {
            throw new IllegalStateException("Catégorie inaccessible.");
        }
    }

    private Transaction getOwnedTransaction(Long transactionId) {
            return null;
    }

    private void requireActiveAccount(Long accountId) {
        
    }

    private Long currentUserId() {
        return null;
    }
}
