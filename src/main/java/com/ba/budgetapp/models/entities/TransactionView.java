package com.ba.budgetapp.models.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Représente une transaction financière.
 *
 * @author Yassine
 */
public class TransactionView {

    private Long transactionId;

    private String category;

    private String type;

    private BigDecimal amount;

    private String description;

    private LocalDate transactionDate;

    public TransactionView() {
    }

    public TransactionView(
            Long transactionId,
            String category,
            String type,
            BigDecimal amount,
            String description,
            LocalDate transactionDate) {

        this.transactionId = transactionId;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {   

        if (this == o) return true;

        if (!(o instanceof TransactionView that)) return false;

        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public String toString() {
        return "TransactionView{" +
                "transactionId=" + transactionId +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}