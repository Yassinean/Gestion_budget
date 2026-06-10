package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.config.DatabaseConnection;
import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.AccountDAO;
import com.ba.budgetapp.models.entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl extends BaseDAO implements AccountDAO {

    private static final String INSERT =
            """
            INSERT INTO accounts(active, user_id)
            VALUES(?,?)
            """;

    private static final String FIND_BY_USER_ID =
            """
            SELECT *
            FROM accounts
            WHERE user_id = ?
            ORDER BY account_id
            """;

    private static final String FIND_ACTIVE_BY_USER_ID =
            """
            SELECT *
            FROM accounts
            WHERE user_id = ?
              AND active = TRUE
            ORDER BY account_id
            """;

    private static final String FIND_BY_ID =
            """
            SELECT *
            FROM accounts
            WHERE account_id = ?
            """;

    private static final String FIND_BY_ID_AND_USER_ID =
            """
            SELECT *
            FROM accounts
            WHERE account_id = ?
              AND user_id = ?
            """;

    private static final String UPDATE =
            """
            UPDATE accounts
            SET active=?
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
            ps.setBoolean(1, account.isActive());
            ps.setLong(2, account.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Account> findById(Long id) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)
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

    public List<Account> findByUserId(Long id) {
        return findAccounts(FIND_BY_USER_ID, id);
    }

    @Override
    public List<Account> findActiveByUserId(Long userId) {
        return findAccounts(FIND_ACTIVE_BY_USER_ID, userId);
    }

    @Override
    public Optional<Account> findByIdAndUserId(Long accountId, Long userId) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_AND_USER_ID)
        ) {
            ps.setLong(1, accountId);
            ps.setLong(2, userId);
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
            ps.setBoolean(1, account.isActive());
            ps.setLong(2, account.getAccountId());
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

    private List<Account> findAccounts(String sql, Long userId) {
        List<Account> accounts = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setActive(rs.getBoolean("active"));
        account.setUserId(rs.getLong("user_id"));
        return account;
    }
}
