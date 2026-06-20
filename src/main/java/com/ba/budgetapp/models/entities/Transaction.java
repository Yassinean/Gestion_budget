package com.ba.budgetapp.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Représente une transaction financière.
 *
 * @author Yassine / Ahlam / Salma / Aziza
 */
public class Transaction {

    private Long transactionId;

    private Long budgetId;

    private Long categoryId;

    private TransactionType transactionType;

    private Long amount;

    private String description;

    private LocalDate transactionDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void validate() {

        if (budgetId == null) {
            throw new IllegalArgumentException("Budget obligatoire.");
        }

        if (categoryId == null) {
            throw new IllegalArgumentException("Catégorie obligatoire.");
        }

        if (transactionType == null) {
            throw new IllegalArgumentException("Type obligatoire.");
        }

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Montant invalide.");
        }

        if (transactionDate == null) {
            throw new IllegalArgumentException("Date obligatoire.");
        }
    }
    
    public Transaction() {
    }

    public Transaction(Long transactionId,
                       Long amount,
                       String description,
                       LocalDate transactionDate,
                       TransactionType transactionType,
                       Long budgetId,
                       Long categoryId) {

        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.categoryId = categoryId;
        this.budgetId = budgetId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }

        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == null ||
                description.isBlank()) {

            throw new IllegalArgumentException(
                    "Description obligatoire");
        }

        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {

        if (transactionDate == null) {
            throw new IllegalArgumentException(
                    "Date obligatoire");
        }

        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(
            TransactionType transactionType) {

        if (transactionType == null) {
            throw new IllegalArgumentException(
                    "Type obligatoire");
        }

        this.transactionType = transactionType;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {

        if (budgetId == null || budgetId <= 0) {
            throw new IllegalArgumentException(
                    "Budget invalide");
        }

        this.budgetId = budgetId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {

        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException(
                    "Catégorie invalide");
        }

        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Transaction that))
            return false;

        return Objects.equals(
                transactionId,
                that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {

        return "Transaction{" +
                "id=" + transactionId +
                ", amount=" + amount +
                ", type=" + transactionType +
                '}';
    }
}