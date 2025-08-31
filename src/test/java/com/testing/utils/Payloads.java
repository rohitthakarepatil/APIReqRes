package com.testing.utils;

public class Payloads {
    public static String createUserPayload(String name, String job) {
        return String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
    }
}
