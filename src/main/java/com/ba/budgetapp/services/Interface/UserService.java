package com.ba.budgetapp.services.Interface;

import com.ba.budgetapp.models.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> authenticate(String username,String password);
    boolean register(User user);
}
