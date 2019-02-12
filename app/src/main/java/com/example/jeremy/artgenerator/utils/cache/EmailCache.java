package com.example.jeremy.artgenerator.utils.cache;

public final class EmailCache {

    private static String email;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        EmailCache.email = email;
    }
}
