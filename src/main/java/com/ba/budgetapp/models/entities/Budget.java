package com.ba.budgetapp.models.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Budget mensuel associé à une catégorie.
 *
 * @author Etudiant
 */
public class Budget {

    private Long budgetId;
    private BigDecimal amount;
    private LocalDate budgetMonth;
    private Long categoryId;

    public Budget() {
    }

    public Budget(Long budgetId,
                  BigDecimal amount,
                  LocalDate budgetMonth,
                  Long categoryId) {

        this.budgetId = budgetId;
        this.amount = amount;
        this.budgetMonth = budgetMonth;
        this.categoryId = categoryId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
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

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(LocalDate budgetMonth) {

        if (budgetMonth == null) {
            throw new IllegalArgumentException(
                    "Date obligatoire");
        }

        this.budgetMonth = budgetMonth;
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

        if (!(o instanceof Budget budget))
            return false;

        return Objects.equals(
                budgetId,
                budget.budgetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", amount=" + amount +
                '}';
    }
}