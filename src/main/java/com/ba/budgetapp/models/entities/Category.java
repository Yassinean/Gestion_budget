package com.ba.budgetapp.models.entities;

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
    private String categoryName;
    private Long userId;

    public Category() {
    }

    public Category(Long categoryId,
                    String categoryName,
                    Long userId) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        if (categoryName == null ||
                categoryName.isBlank()) {

            throw new IllegalArgumentException(
                    "Nom catégorie obligatoire");
        }

        this.categoryName = categoryName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException(
                    "Utilisateur invalide");
        }

        this.userId = userId;
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
        return categoryName;
    }
}