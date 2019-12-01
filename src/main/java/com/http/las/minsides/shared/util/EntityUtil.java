package com.http.las.minsides.shared.util;

import com.http.las.minsides.shared.entity.DaoEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityUtil {
    public static <T> List<T> readEntityValues(List<? extends DaoEntity> entities, String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getterName = ReflectionUtil.generateGetterName(field);
        List<T> values = readEntityValues(getterName, entities);
        return values;
    }
    public static <T> List<T> readEntityValues(String methodName, List<? extends DaoEntity> entities) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<T> values = new ArrayList<>();
        for (DaoEntity entity : entities) {
            Class<? extends DaoEntity> aClass = entity.getClass();
            Method getter = aClass.getMethod(methodName);
            T value = (T) getter.invoke(entity);
            values.add(value);
        }
        return values;
    }

}
