package com.roster.persistence.jdbc.infrastructure.sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class SqlQueryLoader {
    private SqlQueryLoader() { }

    public static String load(String path) {
        try (InputStream inputStream = SqlQueryLoader.class
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (inputStream == null) throw new IllegalStateException("SQL resource was not found: " + path);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to read SQL resource: " + path, ex);
        }
    }
}
