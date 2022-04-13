package com.example.demo;

import java.lang.reflect.Field;

public class testutils {

    private static Object target;
    private static String fieldName;
    private static Object toInject;

    public static void injectObjects(Object target, String fieldName, Object toInject) {
        testutils.target = target;
        testutils.fieldName = fieldName;
        testutils.toInject = toInject;

        boolean wasPrivate;
        wasPrivate = false;

        try {
            Field f;
            f = target.getClass().getDeclaredField(fieldName);
            if (f.isAccessible()) {
            } else {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if (wasPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
