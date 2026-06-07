package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAOImpl
        extends BaseDAO
        implements TransactionDAO {

    private static final String INSERT = """
            INSERT INTO transactions(
                amount,
                description,
                transaction_date,
                transaction_type,
                cleared,
                account_id,
                category_id
            )
            VALUES(?,?,?,?,?,?,?)
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM transactions
            WHERE transaction_id = ?
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM transactions
            ORDER BY transaction_date DESC
            """;

    private static final String UPDATE = """
            UPDATE transactions
            SET amount = ?,
                description = ?,
                transaction_date = ?,
                transaction_type = ?,
                cleared = ?,
                account_id = ?,
                category_id = ?
            WHERE transaction_id = ?
            """;

    private static final String DELETE = """
            DELETE FROM transactions
            WHERE transaction_id = ?
            """;

    @Override
    public boolean create(Transaction transaction) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(INSERT)
        ) {

            ps.setBigDecimal(
                    1,
                    transaction.getAmount());

            ps.setString(
                    2,
                    transaction.getDescription());

            ps.setDate(
                    3,
                    Date.valueOf(
                            transaction.getTransactionDate()));

            ps.setString(
                    4,
                    transaction.getTransactionType().name());

            ps.setBoolean(
                    5,
                    transaction.isCleared());

            ps.setLong(
                    6,
                    transaction.getAccountId());

            ps.setLong(
                    7,
                    transaction.getCategoryId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Optional<Transaction> findById(Long id) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(FIND_BY_ID)
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
    public List<Transaction> findAll() {

        List<Transaction> transactions =
                new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                transactions.add(mapRow(rs));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public boolean update(Transaction transaction) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(UPDATE)
        ) {

            ps.setBigDecimal(
                    1,
                    transaction.getAmount());

            ps.setString(
                    2,
                    transaction.getDescription());

            ps.setDate(
                    3,
                    Date.valueOf(
                            transaction.getTransactionDate()));

            ps.setString(
                    4,
                    transaction.getTransactionType().name());

            ps.setBoolean(
                    5,
                    transaction.isCleared());

            ps.setLong(
                    6,
                    transaction.getAccountId());

            ps.setLong(
                    7,
                    transaction.getCategoryId());

            ps.setLong(
                    8,
                    transaction.getTransactionId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(DELETE)
        ) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    @Override
    public BigDecimal getTotalIncome() {

        return getSumByType("INCOME");
    }

    @Override
    public BigDecimal getTotalExpense() {

        return getSumByType("EXPENSE");
    }

    @Override
    public BigDecimal getCurrentBalance() {

        return getTotalIncome()
                .subtract(getTotalExpense());
    }

    @Override
    public long countTransactions() {

        String sql =
                "SELECT COUNT(*) FROM transactions";

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Transaction> findByCategory(
            Long categoryId) {

        String sql = """
                SELECT *
                FROM transactions
                WHERE category_id = ?
                """;

        return executeListQuery(
                sql,
                ps -> ps.setLong(1, categoryId));
    }

    @Override
    public List<Transaction> findByDateRange(
            LocalDate start,
            LocalDate end) {

        String sql = """
                SELECT *
                FROM transactions
                WHERE transaction_date
                BETWEEN ? AND ?
                ORDER BY transaction_date DESC
                """;

        return executeListQuery(
                sql,
                ps -> {
                    ps.setDate(1, Date.valueOf(start));
                    ps.setDate(2, Date.valueOf(end));
                });
    }

    @Override
    public List<Transaction> search(
            String keyword) {

        String sql = """
                SELECT *
                FROM transactions
                WHERE description LIKE ?
                """;

        return executeListQuery(
                sql,
                ps -> ps.setString(
                        1,
                        "%" + keyword + "%"));
    }

    private BigDecimal getSumByType(
            String type) {

        String sql = """
                SELECT COALESCE(
                    SUM(amount),
                    0
                )
                FROM transactions
                WHERE transaction_type = ?
                """;

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    private List<Transaction> executeListQuery(
            String sql,
            StatementSetter setter) {

        List<Transaction> transactions =
                new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            setter.setValues(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                transactions.add(mapRow(rs));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return transactions;
    }

    private Transaction mapRow( ResultSet rs) throws SQLException {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(rs.getLong("transaction_id"));

        transaction.setAmount(rs.getBigDecimal("amount"));

        transaction.setDescription(rs.getString("description"));

        transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());

        transaction.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));

        transaction.setCleared(rs.getBoolean("cleared"));

        transaction.setAccountId(rs.getLong("account_id"));

        transaction.setCategoryId(rs.getLong("category_id"));
        return transaction;
    }

    @FunctionalInterface
    private interface StatementSetter {
        void setValues(
                PreparedStatement ps)
                throws SQLException;
    }
}