package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.config.DatabaseConnection;
import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl extends BaseDAO implements CrudDAO<Account, Long> {

    private static final String INSERT =
            """
            INSERT INTO accounts(account_name, active, user_id)
            VALUES(?,?,?)
            """;

    private static final String FIND_BY_USER_ID =
            """
            SELECT *
            FROM accounts
            WHERE user_id = ?
            """;

    private static final String UPDATE =
            """
            UPDATE accounts
            SET account_name=?,
                active=?
            WHERE account_id=?
            """;

    private static final String DELETE =
            """
            DELETE FROM accounts
            WHERE account_id = ?
            """;

    @Override
    public boolean create(Account account) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT)
        ) {
            ps.setLong(1, account.getAccountId());
            ps.setString(2, account.getAccountName());
            ps.setBoolean(3, account.isActive());
            ps.setLong(4, account.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<Account> findByUserId(Long id) {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_USER_ID)
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Account account) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE)
        ) {
            ps.setString(1, account.getAccountName());
            ps.setBoolean(2, account.isActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE)
        ) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setAccountName(rs.getString("account_name"));
        account.setActive(rs.getBoolean("active"));
        account.setUserId(rs.getLong("user_id"));
        return account;
    }
}