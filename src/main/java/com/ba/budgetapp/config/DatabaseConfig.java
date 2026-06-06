package com.ba.budgetapp.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     DatabaseConfig.class.getClassLoader()
                             .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Fichier database.properties introuvable");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erreur lors du chargement de la configuration", e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUser() {
        return properties.getProperty("db.user");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}