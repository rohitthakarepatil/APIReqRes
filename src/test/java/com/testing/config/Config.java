package com.testing.config;

public class Config {
    public static final String BASE_URL = System.getProperty("api.baseUrl", System.getenv().getOrDefault("API_BASE_URL", "https://reqres.in/api"));
    public static final String API_KEY = System.getProperty("api.key", System.getenv().getOrDefault("API_KEY", "reqres-free-v1"));
    public static final String API_KEY_HEADER = System.getProperty("api.keyHeader", System.getenv().getOrDefault("API_KEY_HEADER", "x-api-key"));
}
