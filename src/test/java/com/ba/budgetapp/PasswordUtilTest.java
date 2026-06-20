package com.ba.budgetapp;

import com.ba.budgetapp.utils.PasswordUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test
    void shouldHashAndVerifyPassword() {

        String raw = "123456";

        String hashed = PasswordUtils.hashPassword(raw);

        assertNotNull(hashed);
        assertTrue(PasswordUtils.verifyPassword(raw, hashed));
        assertFalse(PasswordUtils.verifyPassword("wrong", hashed));
    }
}