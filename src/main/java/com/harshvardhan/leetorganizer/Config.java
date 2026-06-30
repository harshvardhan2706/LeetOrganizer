package com.harshvardhan.leetorganizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load configuration", e);
        }
    }

    public static String getRepositoryPath() {
        String repositoryPath = properties.getProperty("repository.path");
        return Objects.requireNonNull(repositoryPath, "repository.path must be configured in config.properties").trim();
    }

}