package com.example.jeremy.artgenerator.utils.resolvers;

import java.util.List;

public final class ObjectResolver {
    public static boolean isNotEmpty(Object... values) {
        boolean result = true;
        int count = 0;
        for (Object value : values) if (value.toString() == "") count++;
        if (count > 0) result = false;
        return result;
    }
    public static boolean isNotNull(Object... values) {
        boolean result = true;
        int count = 0;
        for (Object value : values) if (value == null) count++;
        if (count > 0) result = false;
        return result;
    }
    public static boolean isNotNull(List<Object> objectList) {
        boolean result = true;
        int count = 0;
        for (Object value : objectList) if (value == null) count++;
        if (count > 0) result = false;
        return result;
    }
}
