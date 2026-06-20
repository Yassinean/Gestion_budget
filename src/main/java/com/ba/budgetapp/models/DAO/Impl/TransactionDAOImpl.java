package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.TransactionDAO;
import com.ba.budgetapp.models.entities.Transaction;
import com.ba.budgetapp.models.entities.TransactionType;
import com.ba.budgetapp.models.entities.TransactionView;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class TransactionDAOImpl extends BaseDAO implements TransactionDAO {
    
    private static final String INSERT = "INSERT INTO transactions (budget_id, category_id, transaction_type, amount, description, transaction_date) VALUES (?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE transactions SET category_id=?, transaction_type=?, amount=?, description=?, transaction_date=? WHERE transaction_id=?";

    private static final String DELETE = "DELETE FROM transactions WHERE transaction_id = ?";

    private static final String FIND_BY_ID = "SELECT * FROM transactions WHERE transaction_id = ?";

    private static final String FIND_ALL = "SELECT * FROM transactions ORDER BY transaction_date DESC, transaction_id DESC";
    
    private static final String FIND_BY_BUDGET_ID = "SELECT * FROM transactions WHERE budget_id = ? ORDER BY transaction_date DESC";

    private static final String FIND_BY_DATE_RANGE = "SELECT * FROM transactions WHERE budget_id = ? AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";

    private static final String SEARCH = "SELECT * FROM transactions WHERE budget_id = ? AND description LIKE ? ORDER BY transaction_date DESC";

    private static final String SEARCH_VIEW = """
        SELECT t.transaction_id, c.title AS category, t.transaction_type, t.amount, t.description, t.transaction_date
        FROM transactions t
        JOIN categories c ON t.category_id = c.category_id
        WHERE t.budget_id = ?
          AND (
              LOWER(t.description) LIKE ?
              OR LOWER(c.title) LIKE ?
              OR LOWER(t.transaction_type) LIKE ?
          )
        ORDER BY t.transaction_date DESC, t.transaction_id DESC
        """;

    private static final String FIND_ALL_VIEW = """
        SELECT t.transaction_id, c.title AS category, t.transaction_type, t.amount, t.description, t.transaction_date
        FROM transactions t
        JOIN categories c ON t.category_id = c.category_id
        WHERE t.budget_id = ?
        ORDER BY
        transaction_date DESC,
        transaction_id DESC
        """;

    private static final String GET_TOTAL_INCOME = """
            SELECT b.amount + COALESCE(SUM(t.amount), 0) 
            FROM budgets b
            LEFT JOIN transactions t ON t.budget_id = b.budget_id AND t.transaction_type = 'INCOME'
            WHERE b.budget_id = ?
            GROUP BY b.budget_id, b.amount
            """;

    private static final String GET_TOTAL_EXPENSE = "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE budget_id = ? AND transaction_type = 'EXPENSE'";

    private static final String GET_CURRENT_BALANCE = """
        SELECT
            b.amount
            + COALESCE(SUM(CASE WHEN t.transaction_type = 'INCOME' THEN t.amount ELSE 0 END), 0)
            - COALESCE(SUM(CASE WHEN t.transaction_type = 'EXPENSE' THEN t.amount ELSE 0 END), 0) AS balance
        FROM budgets b
        LEFT JOIN transactions t
            ON t.budget_id = b.budget_id
        WHERE b.budget_id = ?
        GROUP BY b.budget_id, b.amount;
        """;

    private static final String COUNT_TRANSACTIONS = "SELECT COUNT(*) FROM transactions WHERE budget_id = ?";

    private static final String GET_EXPENSES_BY_CATEGORY = """
        SELECT c.title AS category, COALESCE(SUM(t.amount), 0) AS total
        FROM transactions t
        JOIN categories c ON t.category_id = c.category_id
        WHERE t.budget_id = ? AND t.transaction_type = 'EXPENSE'
        GROUP BY c.title
        ORDER BY total DESC
        """;

    private static final String GET_MONTHLY_INCOME = """
        SELECT DATE_FORMAT(transaction_date, '%Y-%m') AS month , COALESCE(SUM(amount), 0) AS total
        FROM transactions
        WHERE budget_id = ? AND transaction_type = 'INCOME'
        GROUP BY month
        ORDER BY month
        """;

    private static final String GET_MONTHLY_EXPENSE = """
        SELECT DATE_FORMAT(transaction_date, '%Y-%m') AS month , COALESCE(SUM(amount), 0) AS total
        FROM transactions
        WHERE budget_id = ? AND transaction_type = 'EXPENSE'
        GROUP BY month
        ORDER BY month
        """;

    @Override
    public boolean create(Transaction transaction) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            ) {
                ps.setLong(1, transaction.getBudgetId());
                ps.setLong(2, transaction.getCategoryId());
                ps.setString(3, transaction.getTransactionType().name());
                ps.setLong(4, transaction.getAmount());
                ps.setString(5, transaction.getDescription());
                ps.setDate(6, Date.valueOf(transaction.getTransactionDate()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setTransactionId(generatedKeys.getLong(1));
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
         try (
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)
        ) {

            ps.setLong(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_ALL);
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
    public List<Transaction> findByBudgetId(Long budgetId) {
        List<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_BUDGET_ID)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByCategory(Long categoryId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE category_id = ? ORDER BY transaction_date DESC";
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByDateRange( Long budgetId, LocalDate start, LocalDate end){
        List<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_DATE_RANGE)
        ) {
            ps.setLong(1, budgetId);
            ps.setDate(2, Date.valueOf(start));
            ps.setDate(3, Date.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> search(Long budgetId, String keyword) {
        List<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SEARCH)
        ) { 
            ps.setLong(1, budgetId);
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
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
                PreparedStatement ps = connection.prepareStatement(UPDATE)
        ) {
            ps.setLong(1, transaction.getCategoryId());
            ps.setString(2, transaction.getTransactionType().name());
            ps.setLong(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.setDate(5, Date.valueOf(transaction.getTransactionDate()));
            ps.setLong(6, transaction.getTransactionId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long transactionId) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE)
        ) {
            ps.setLong(1, transactionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TransactionView> searchView(Long budgetId, String keyword) {
        List<TransactionView> transactions = new ArrayList<>();
        String searchTerm = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SEARCH_VIEW)
        ) {
            ps.setLong(1, budgetId);
            ps.setString(2, searchTerm);
            ps.setString(3, searchTerm);
            ps.setString(4, searchTerm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapTransactionView(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<TransactionView> findAllView(Long budgetId) {
        List<TransactionView> transactions = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_ALL_VIEW)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapTransactionView(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public BigDecimal getTotalIncome(Long budgetId) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_TOTAL_INCOME)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalExpense(Long budgetId) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_TOTAL_EXPENSE)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getCurrentBalance(Long budgetId) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_CURRENT_BALANCE)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public long countTransactions(Long budgetId) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(COUNT_TRANSACTIONS)
        ) {
            ps.setLong(1, budgetId);
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
    public Map<String, Double> getExpensesByCategory(Long budgetId) {
        Map<String, Double> expenses = new LinkedHashMap<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_EXPENSES_BY_CATEGORY)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    expenses.put(rs.getString("category"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public Map<String, Double> getMonthlyIncome(Long budgetId) {
        Map<String, Double> income = new LinkedHashMap<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_MONTHLY_INCOME)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    income.put(rs.getString("month"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return income;
    }

    @Override
    public Map<String, Double> getMonthlyExpense(Long budgetId) {
        Map<String, Double> expense = new LinkedHashMap<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_MONTHLY_EXPENSE)
        ) {
            ps.setLong(1, budgetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    expense.put(rs.getString("month"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expense;
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(rs.getLong("transaction_id"));
        transaction.setBudgetId(rs.getLong("budget_id"));
        transaction.setCategoryId(rs.getLong("category_id"));
        transaction.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));
        transaction.setAmount(rs.getLong("amount"));
        transaction.setDescription(rs.getString("description"));
        transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
        Timestamp created = rs.getTimestamp("created_at");

        if (created != null) {
            transaction.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp updated = rs.getTimestamp("updated_at");

        if (updated != null) {
            transaction.setUpdatedAt(updated.toLocalDateTime());
        }

        return transaction;
    }

    private TransactionView mapTransactionView(ResultSet rs) throws SQLException {
        TransactionView view = new TransactionView();
        view.setTransactionId(rs.getLong("transaction_id"));
        view.setCategory(rs.getString("category"));
        view.setType(rs.getString("transaction_type"));
        view.setAmount(rs.getBigDecimal("amount"));
        view.setDescription(rs.getString("description"));
        view.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
        return view;
    }

}
