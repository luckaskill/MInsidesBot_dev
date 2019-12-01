package com.http.las.minsides.shared.util;

import com.http.las.minsides.shared.entity.DaoEntity;
import com.http.las.minsides.shared.entity.IgnoreDump;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtil {
    private final static String GET_PREFIX = "get";

    public static String generateGetterName(String fieldName) {
        StringBuilder builder = new StringBuilder(fieldName);
        builder
                .replace(0, 1, builder.substring(0, 1).toUpperCase())
                .insert(0, GET_PREFIX);
        return builder.toString();
    }

    public static boolean setValueFromTo(DaoEntity from, DaoEntity to) {
        boolean success = false;
        try {
            Class<? extends DaoEntity> toClass = to.getClass();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field : fields) {

                Annotation idAnnotation = field.getAnnotation(Id.class);
                Annotation ignore = field.getAnnotation(IgnoreDump.class);
                if (idAnnotation == null && ignore == null) {
                    setFieldValueFromTo(field, from, to);
                }
            }
            success = true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    private static void setFieldValueFromTo(Field field, DaoEntity from, DaoEntity to) throws NoSuchFieldException, IllegalAccessException {
        boolean accessChanged = openAccessIfNeeded(field);

        Class<? extends DaoEntity> fromClass = from.getClass();

        String name = field.getName();
        Field sourceField = fromClass.getDeclaredField(name);
        boolean sourceFieldAccessChanged = openAccessIfNeeded(sourceField);
        Object backupValue = sourceField.get(from);

        int modifiers = field.getModifiers();
        String modifiersStr = Modifier.toString(modifiers);
        if (!modifiersStr.contains("final")) {

            field.set(to, backupValue);
            if (sourceFieldAccessChanged) {
                changeAccessToOpposite(sourceField);
            }
            if (accessChanged) {
                changeAccessToOpposite(field);
            }
        }
    }


    private static boolean openAccessIfNeeded(Field f) {
        boolean changed = false;
        if (!f.isAccessible()) {
            f.setAccessible(changed = true);
        }
        return changed;
    }

    private static void changeAccessToOpposite(Field field) {
        boolean oppositeAccess = !field.isAccessible();
        field.setAccessible(oppositeAccess);
    }

}
