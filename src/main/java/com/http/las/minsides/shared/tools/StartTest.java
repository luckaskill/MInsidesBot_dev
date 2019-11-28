package com.http.las.minsides.shared.tools;

import com.http.las.minsides.shared.entity.DaoEntity;
import com.http.las.minsides.shared.exceptions.StartException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StartTest {

    private static final String DAO_ENTITY_PACKAGE = "com.http.las.minsides.shared.entity";

    public static void test() throws StartException {
        testDaoEntities();
    }

    private static void testDaoEntities() throws StartException {
        Reflections reflections = new Reflections(DAO_ENTITY_PACKAGE);
        Set<Class<? extends DaoEntity>> allClasses =
                reflections.getSubTypesOf(DaoEntity.class);

        List<Class> brokenClasses = new ArrayList<>();
        for (Class c : allClasses) {
                if (!hasNoArgsConstructor(c)) {
                    brokenClasses.add(c);
            }
        }
        if (!brokenClasses.isEmpty()) {
            throw new StartException(generateNoEmptyConstructorMsg(brokenClasses));
        }
    }

    private static String generateNoEmptyConstructorMsg(List<Class> classes) {
        StringBuilder builder = new StringBuilder();
        builder.append("Next dao entities don't have no-args constructor:\n");
        for (Class clazz : classes) {
            builder.append(clazz.getSimpleName()).append('\n');
        }
        return builder.toString();
    }

    private static boolean hasNoArgsConstructor(Class c) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }
}
