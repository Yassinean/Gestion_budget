package com.ba.budgetapp.models.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Représente une transaction financière.
 *
 * @author Etudiant
 */
public class Transaction {

    private Long transactionId;

    private BigDecimal amount;

    private String description;

    private LocalDate transactionDate;

    private TransactionType transactionType;

    private Long accountId;

    private Long categoryId;

    public Transaction() {
    }

    public Transaction(Long transactionId,
                       BigDecimal amount,
                       String description,
                       LocalDate transactionDate,
                       TransactionType transactionType,
                       Long accountId,
                       Long categoryId) {

        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Montant invalide");
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {

        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException(
                    "Compte invalide");
        }

        this.accountId = accountId;
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