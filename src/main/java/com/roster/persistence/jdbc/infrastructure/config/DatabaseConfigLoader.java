package com.roster.persistence.jdbc.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class DatabaseConfigLoader {
    private static final String DB_URL = "DB_URL";
    private static final String DB_USER = "DB_USER";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    private DatabaseConfigLoader() { }

    public static DatabaseConfig load() {
        Dotenv dotenv = Dotenv.load();

        return new DatabaseConfig(
                getRequiredValue(dotenv, DB_URL),
                getRequiredValue(dotenv, DB_USER),
                getRequiredValue(dotenv, DB_PASSWORD)
        );
    }

    private static String getRequiredValue(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required environment variable is missing: " + key);
        }

        return value.trim();
    }
}
