package com.ba.budgetapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestionnaire de connexion MySQL.
 *
 * Implémente le pattern Singleton.
 *
 * @author Etudiant
     */
public final class DatabaseConnection {

    private DatabaseConnection() {
    }

    /**
     * Retourne une connexion JDBC.
     *
     * @return Connection
     * @throws SQLException si erreur JDBC
     */
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
        );
    }
}