package com.ba.budgetapp.models.entities;

import java.util.Objects;

/**
 * Représente un utilisateur de l'application.
 *
 * @author Etudiant
 */
public class User {

    private Long userId;
    private String username;
    private String password;
    private boolean appAccess = true;

    public User() {
    }

    public User(Long userId,
                String username,
                String password,
                boolean appAccess) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.appAccess = appAccess;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom d'utilisateur est obligatoire");
        }

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Le mot de passe est obligatoire");
        }

        this.password = password;
    }

    public boolean isAppAccess() {
        return appAccess;
    }

    public void setAppAccess(boolean appAccess) {
        this.appAccess = appAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return username;
    }
}
