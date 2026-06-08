package com.ba.budgetapp.models.DAO.Impl;

import com.ba.budgetapp.config.DatabaseConnection;
import com.ba.budgetapp.models.DAO.BaseDAO;
import com.ba.budgetapp.models.DAO.Interface.UserDAO;
import com.ba.budgetapp.models.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl extends BaseDAO implements UserDAO {

    private static final String INSERT = """
    INSERT INTO users(username,password,app_access)
    VALUES(?,?,?)
    """;

    private static final String FIND_BY_ID = """
    SELECT * FROM users
    WHERE user_id=?
    """;

    private static final String FIND_ALL = """
    SELECT * FROM users
    ORDER BY username
    """;

    private static final String UPDATE = """
    UPDATE users
    SET username=?, password=?, app_access=?
    WHERE user_id=?
    """;

    private static final String DELETE = """
    DELETE FROM users
    WHERE user_id=?
    """;

    private static final String FIND_BY_USERNAME = """
    SELECT *
    FROM users
    WHERE username=?
    """;

    @Override
    public boolean create(User user) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAppAccess());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
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

    public Optional<User> findByUserName(String nom) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME)
        ) {
            ps.setString(1, nom);
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
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean update(User user) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAppAccess());
            ps.setLong(4, user.getUserId());
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

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setAppAccess(rs.getBoolean("app_access"));
        return user;
    }
}