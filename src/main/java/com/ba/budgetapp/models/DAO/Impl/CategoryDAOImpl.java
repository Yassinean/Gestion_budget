package com.ba.budgetapp.models.DAO.Impl;


import com.ba.budgetapp.config.DatabaseConnection;
import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.CategoryDAO;
import com.ba.budgetapp.models.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Implémentation JDBC du DAO Category.
 *
 * @author Etudiant
 */
public class CategoryDAOImpl extends BaseDAO implements CategoryDAO {

    private static final String INSERT =
            """
            INSERT INTO categories(category_name,user_id)
            VALUES(?,?)
            """;

    private static final String FIND_BY_ID =
            """
            SELECT *
            FROM categories
            WHERE category_id = ?
            """;

    private static final String FIND_ALL =
            """
            SELECT *
            FROM categories
            ORDER BY category_name
            """;

    private static final String FIND_BY_USER_ID =
            """
            SELECT *
            FROM categories
            WHERE user_id = ?
            ORDER BY category_name
            """;

    private static final String FIND_BY_ID_AND_USER_ID =
            """
            SELECT *
            FROM categories
            WHERE category_id = ?
              AND user_id = ?
            """;

    private static final String UPDATE =
            """
            UPDATE categories
            SET category_name = ?
            WHERE category_id = ?
            """;

    private static final String DELETE =
            """
            DELETE FROM categories
            WHERE category_id = ?
            """;

    @Override
    public boolean create(Category category) {

        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT)
        ) {
            ps.setString(1, category.getCategoryName());
            ps.setLong(2, category.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Category> findById(Long id) {

        try (
                Connection connection = DatabaseConnection.getConnection();
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

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public boolean update(Category category) {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE)
        ) {
            ps.setString(1, category.getCategoryName());
            ps.setLong(2, category.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Category> findByUserId(Long userId) {
        List<Category> categories = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_USER_ID)
        ) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Optional<Category> findByIdAndUserId(Long categoryId, Long userId) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_AND_USER_ID)
        ) {
            ps.setLong(1, categoryId);
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

    /**
     * Convertit une ligne SQL en objet Category.
     */
    private Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId( rs.getLong("category_id"));
        category.setCategoryName( rs.getString("category_name"));
        category.setUserId( rs.getLong("user_id"));
        return category;
    }
}
