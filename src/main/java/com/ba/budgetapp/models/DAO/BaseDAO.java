package com.ba.budgetapp.models.DAO;

import com.ba.budgetapp.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe mère de tous les DAO JDBC.
 */
public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
