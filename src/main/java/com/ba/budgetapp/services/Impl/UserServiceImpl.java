package com.ba.budgetapp.services.Impl;

import com.ba.budgetapp.models.DAO.Impl.UserDAOImpl;
import com.ba.budgetapp.models.DAO.Interface.UserDAO;
import com.ba.budgetapp.models.entities.User;
import com.ba.budgetapp.services.Interface.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public Optional<User> authenticate(
            String username,
            String password) {

        Optional<User> user =
                userDAO.findByUserName(
                        username);

        if (user.isPresent()
                &&
                user.get()
                        .getPassword()
                        .equals(password)) {

            return user;
        }

        return Optional.empty();
    }

    @Override
    public boolean register(User user) {
        Optional<User> existingUser = userDAO.findByUserName(user.getUsername());
        if (existingUser.isPresent()) {
            throw new DuplicateResourceException(
                    "Ce nom d'utilisateur existe déjà.");
        }

        return userDAO.create(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUserName(username);
    }
}
