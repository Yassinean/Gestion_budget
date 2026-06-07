package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.CrudDAO;
import com.ba.budgetapp.models.entities.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetDAOImpl extends BaseDAO implements CrudDAO<Budget, Long> {

    private static final String INSERT = """
            INSERT INTO budgets(
                amount,
                budget_month,
                category_id
            )
            VALUES (?, ?, ?)
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM budgets
            WHERE budget_id = ?
            """;

    private static final String FIND_BY_CATEGORY = """
            SELECT *
            FROM budgets
            WHERE category_id = ?
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM budgets
            ORDER BY budget_month DESC
            """;

    private static final String UPDATE = """
            UPDATE budgets
            SET amount = ?,
                budget_month = ?,
                category_id = ?
            WHERE budget_id = ?
            """;

    private static final String DELETE = """
            DELETE FROM budgets
            WHERE budget_id = ?
            """;

    @Override
    public boolean create(Budget budget) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT)
        ) {

            ps.setBigDecimal(1, budget.getAmount());
            ps.setDate(2, Date.valueOf(budget.getBudgetMonth()));
            ps.setLong(3, budget.getCategoryId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
    public boolean update(Budget budget) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE)
        ) {

            ps.setBigDecimal(1, budget.getAmount());
            ps.setDate(2, Date.valueOf(budget.getBudgetMonth()));
            ps.setLong(3, budget.getCategoryId());
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

    public List<Budget> findByCategory(Long categoryId) {

        List<Budget> budgets = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_CATEGORY)
        ) {

            ps.setLong(1, categoryId);

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
        budget.setAmount(rs.getBigDecimal("amount"));
        budget.setBudgetMonth(
                rs.getDate("budget_month").toLocalDate()
        );
        budget.setCategoryId(rs.getLong("category_id"));

        return budget;
    }
}