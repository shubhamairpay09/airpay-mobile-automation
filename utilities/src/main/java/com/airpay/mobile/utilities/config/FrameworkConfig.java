package com.airpay.mobile.utilities.config;

import java.util.Properties;

public final class FrameworkConfig {

    private final Properties properties;

    public FrameworkConfig(Properties properties) {
        this.properties = properties;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void set(String key, String value) {
        if (value == null) {
            properties.remove(key);
        } else {
            properties.setProperty(key, value);
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value == null || value.isBlank() ? defaultValue : Boolean.parseBoolean(value.trim());
    }

    public int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }

    public long getLong(String key, long defaultValue) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Long.parseLong(value.trim());
    }

    public String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config: " + key);
        }
        return value;
    }

    public String product() {
        return get("airpay.product", "capital").trim();
    }

    public String environment() {
        return get("env", "live").trim();
    }
}
