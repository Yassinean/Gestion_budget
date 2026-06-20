package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.BudgetDAO;
import com.ba.budgetapp.models.entities.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetDAOImpl extends BaseDAO implements BudgetDAO {

    private static final String INSERT = """
    INSERT INTO budgets(owner_id, title, amount, currency)
    VALUES (?, ?, ?, ?)
    """;

    private static final String FIND_BY_ID = """
        SELECT *
        FROM budgets
        WHERE budget_id = ?
        """;

    private static final String FIND_ALL = """
        SELECT *
        FROM budgets
        ORDER BY created_at DESC
        """;

    private static final String FIND_BY_OWNER = """
        SELECT *
        FROM budgets
        WHERE owner_id = ?
        ORDER BY created_at DESC
        """;

    private static final String UPDATE = """
        UPDATE budgets
        SET title = ?, amount = ?, currency = ?
        WHERE budget_id = ?
        """;

    private static final String DELETE = """
        DELETE FROM budgets
        WHERE budget_id = ?
        """;

    private static final String FIND_DEFAULT = """
        SELECT *
        FROM budgets
        WHERE owner_id = ?
        ORDER BY budget_id
        LIMIT 1
        """;

    @Override
    public boolean create(Budget budget) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setLong(1, budget.getOwnerId());
            ps.setString(2, budget.getTitle());
            ps.setBigDecimal(3,budget.getAmount());
            ps.setString(4, budget.getCurrency());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    budget.setBudgetId(rs.getLong(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Budget budget) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(UPDATE)
        ) {

            ps.setString(1, budget.getTitle());

            ps.setBigDecimal(2, budget.getAmount());

            ps.setString(3, budget.getCurrency());

            ps.setLong(4, budget.getBudgetId());

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
                PreparedStatement ps = connection.prepareStatement(DELETE)
        ) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Budget> findDefaultBudget(Long ownerId) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(FIND_DEFAULT)
        ) {

            ps.setLong(1, ownerId);

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
    public Optional<Budget> findById(Long id) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)
        ) {
            ps.setLong(1, id);
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
    public List<Budget> findAll() {

        List<Budget> budgets = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                budgets.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

    @Override
    public List<Budget> findByOwnerId(Long ownerId) {

        List<Budget> budgets = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(FIND_BY_OWNER)
        ) {

            ps.setLong(1, ownerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    budgets.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return budgets;
    }

    private Budget mapRow(ResultSet rs) throws SQLException {

        Budget budget = new Budget();

        budget.setBudgetId(rs.getLong("budget_id"));

        budget.setOwnerId(rs.getLong("owner_id"));

        budget.setTitle(rs.getString("title"));

        budget.setAmount(rs.getBigDecimal("amount"));

        budget.setCurrency(rs.getString("currency"));

        Timestamp created = rs.getTimestamp("created_at");

        if (created != null) {
            budget.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp updated = rs.getTimestamp("updated_at");
        if (updated != null) {
            budget.setUpdatedAt(updated.toLocalDateTime());
        }
        return budget;
    }
}