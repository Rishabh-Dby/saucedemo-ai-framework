package com.rishabh.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties not found in resources");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return System.getProperty(toSystemKey(key), PROPERTIES.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(get(key));
    }

    private static String toSystemKey(String key) {
        return key.replace('.', '_');
    }
}
