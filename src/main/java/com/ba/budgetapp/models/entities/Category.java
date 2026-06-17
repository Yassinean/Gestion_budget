package com.ba.budgetapp.models.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Catégorie d'une transaction.
 *
 * Exemple :
 * Alimentation
 * Transport
 * Salaire
 * Freelance
 *
 * @author Etudiant
 */
public class Category {

    private Long categoryId;

    private Long budgetId;

    private String title;

    private String budgetTitle;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Category() {
    }

    public void validate() {

        if (budgetId == null) {
            throw new IllegalArgumentException("Budget obligatoire.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Titre obligatoire.");
        }

    }

    public Category(Long categoryId,
                    String title,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (title == null ||
                title.isBlank()) {

            throw new IllegalArgumentException(
                    "Titre obligatoire");
        }

        this.title = title;
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

        if (!(o instanceof Category category))
            return false;

        return Objects.equals(categoryId,
                category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }

    @Override
    public String toString() {
        return title;
    }

    public String getBudgetTitle() {
        return budgetTitle;
    }

    public void setBudgetTitle(String budgetTitle) {
        this.budgetTitle = budgetTitle;
    }
}