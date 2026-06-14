package com.ba.budgetapp.models.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Budget mensuel associé à une catégorie.
 *
 * @author Yassine
 */

public class Budget {

    private Long budgetId;

    private Long ownerId;

    private String title;

    private String currency;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Budget() {
    }

    public Budget(Long budgetId,
                  Long ownerId,
                  String title,
                  String currency,
                  LocalDateTime createdAt,
                  LocalDateTime updatedAt) {

        this.budgetId = budgetId;
        this.ownerId = ownerId;
        this.title = title;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void validate() {

        if (ownerId == null) {
            throw new IllegalArgumentException("Owner obligatoire.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Titre obligatoire.");
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Devise obligatoire.");
        }
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Budget budget)) return false;
        return Objects.equals(budgetId, budget.budgetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId);
    }

    @Override
    public String toString() {
        return title;
    }
}