package com.ba.budgetapp.models.entities;

import java.time.LocalDateTime;
import java.util.Objects;



/**
 * Représente un compte utilisateur de l'application.
 * @author Yassine Hanach
 */
public class Account {
    private Long accountId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account() {
    }

    public Account(Long accountId,
                   String username,
                   String email,
                   String password,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {

        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void validate() {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username obligatoire.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email obligatoire.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password obligatoire.");
        }
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(o instanceof Account account)) return false;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }

    @Override
    public String toString() {
        return username + " (" + email + ")";
    }
}