package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class TransactionDAOImpl
        extends BaseDAO
        implements TransactionDAO {

    private static final String INSERT = """
            INSERT INTO transactions(
                amount,
                description,
                transaction_date,
                transaction_type,
                account_id,
                category_id
            )
            VALUES(?,?,?,?,?,?)
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
            ps.setBigDecimal(1, transaction.getAmount());

            ps.setString(2, transaction.getDescription());

            ps.setDate( 3, Date.valueOf(transaction.getTransactionDate()));

            ps.setString(4, transaction.getTransactionType().name());

            ps.setLong(5, transaction.getAccountId());

            ps.setLong(
                    6,
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

            ps.setLong(
                    5,
                    transaction.getAccountId());

            ps.setLong(
                    6,
                    transaction.getCategoryId());

            ps.setLong(
                    7,
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
    public Map<String, Double> getExpensesByCategory(Long userId) {

        String sql = """
        SELECT c.category_name,
               SUM(t.amount) AS total
        FROM transactions t
        JOIN categories c
             ON t.category_id = c.category_id
        JOIN accounts a
             ON t.account_id = a.account_id
        WHERE t.transaction_type = 'EXPENSE'
        AND a.user_id = ?
        GROUP BY c.category_name
        """;

        Map<String, Double> result =
                new HashMap<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    result.put(
                            rs.getString("category_name"),
                            rs.getDouble("total"));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return result;
    }

    @Override
    public BigDecimal getTotalIncome(Long userId) {
        return getSumByType("INCOME", userId);
    }

    @Override
    public BigDecimal getTotalExpense(Long userId) {
        return getSumByType("EXPENSE" , userId);
    }

    @Override
    public Map<String, Double> getMonthlyIncome(Long userId) {
        String sql = """
        SELECT MONTH(t.transaction_date) AS month,
               SUM(t.amount) AS total
        FROM transactions t
        JOIN accounts a
             ON t.account_id = a.account_id
        WHERE t.transaction_type = 'INCOME'
        AND a.user_id = ?
        GROUP BY MONTH(t.transaction_date)
        ORDER BY MONTH(t.transaction_date)
        """;
        Map<String, Double> data = new LinkedHashMap<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    data.put(
                            String.valueOf(
                                    rs.getInt("month")),
                            rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Map<String, Double> getMonthlyExpense(Long userId) {

        String sql = """
        SELECT MONTH(t.transaction_date) AS month,
               SUM(t.amount) AS total
        FROM transactions t
        JOIN accounts a
             ON t.account_id = a.account_id
        WHERE t.transaction_type = 'EXPENSE'
        AND a.user_id = ?
        GROUP BY MONTH(t.transaction_date)
        ORDER BY MONTH(t.transaction_date)
        """;

        Map<String, Double> data =
                new LinkedHashMap<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.put(
                            String.valueOf(
                                    rs.getInt("month")),
                            rs.getDouble("total"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public BigDecimal getCurrentBalance(Long userId) {

        return getTotalIncome(userId)
                .subtract(getTotalExpense(userId));
    }

    @Override
    public long countTransactions(Long userId) {
        String sql = """
        SELECT COUNT(*)
        FROM transactions t
        JOIN accounts a
             ON t.account_id = a.account_id
        WHERE a.user_id = ?
        """;
        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
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
            String type,
            Long userId) {

        String sql = """
        SELECT COALESCE(SUM(t.amount),0)
        FROM transactions t
        JOIN accounts a
             ON t.account_id = a.account_id
        WHERE t.transaction_type = ?
        AND a.user_id = ?
        """;

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setString(1, type);
            ps.setLong(2, userId);

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