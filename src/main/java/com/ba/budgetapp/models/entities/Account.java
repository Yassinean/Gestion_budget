package com.ba.budgetapp.models.entities;

import java.util.Objects;

/**
 * Représente un compte financier.
 *
 * Exemple :
 * - Compte bancaire
 * - Cash
 * - Épargne
 *
 * @author Etudiant
 */
public class Account {

    private Long accountId;
    private String accountName;
    private boolean active;
    private Long userId;

    public Account() {
    }

    public Account(Long accountId,
                   String accountName,
                   boolean active,
                   Long userId) {

        this.accountId = accountId;
        this.accountName = accountName;
        this.active = active;
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {

        if (accountName == null || accountName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nom du compte obligatoire");
        }

        this.accountName = accountName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

        if (!(o instanceof Account account))
            return false;

        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }

    @Override
    public String toString() {
        return accountName;
    }
}