package com.atlys.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private final Properties props = new Properties();

    public TestConfig() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getBaseUrl() {
        String sys = System.getProperty("baseUrl");
        return sys != null ? sys : props.getProperty("base.url", "https://www.livelyroot.com");
    }

    public boolean isHeadless() {
        String sys = System.getProperty("headless");
        String v = sys != null ? sys : props.getProperty("headless", "true");
        return Boolean.parseBoolean(v);
    }

    public boolean isMockEnabled() {
        String sys = System.getProperty("mock");
        String v = sys != null ? sys : props.getProperty("mock", "true");
        return Boolean.parseBoolean(v);
    }

    public String getApiBaseUri() {
        String sys = System.getProperty("apiBaseUri");
        return sys != null ? sys : props.getProperty("api.base.uri", "http://localhost:8089");
    }
}
