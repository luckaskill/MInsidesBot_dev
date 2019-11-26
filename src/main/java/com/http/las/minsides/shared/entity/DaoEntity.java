package com.http.las.minsides.shared.entity;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class DaoEntity {
    protected DaoEntity dump;

    public DaoEntity() {

    }

    public boolean backup() {
        return setValueFromTo(dump, this);
    }

    private boolean setValueFromTo(DaoEntity from, DaoEntity to) {
        try {
            Class<? extends DaoEntity> toClass = to.getClass();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field : fields) {

                Annotation idAnnotation = field.getAnnotation(Id.class);
                Annotation ignore = field.getAnnotation(IgnoreDump.class);
                if (idAnnotation == null && ignore == null) {
                    setValueFromTo(field, from, to);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setValueFromTo(Field field, DaoEntity from, DaoEntity to) throws NoSuchFieldException, IllegalAccessException {
        boolean accessChanged = openAccessIfNeeded(field);

        Class<? extends DaoEntity> fromClass = from.getClass();

        String name = field.getName();
        Field sourceField = fromClass.getDeclaredField(name);
        boolean sourceFieldAccessChanged = openAccessIfNeeded(sourceField);
        Object backupValue = sourceField.get(from);

        field.set(to, backupValue);
        if (sourceFieldAccessChanged) {
            changeAccessToOpposite(sourceField);
        }
        if (accessChanged) {
            changeAccessToOpposite(field);
        }
    }

    public void createDump() {
        try {
            dump = (DaoEntity) Class.forName(this.getClass().getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setValueFromTo(this, dump);
    }

    private boolean openAccessIfNeeded(Field f) {
        boolean changed = false;
        if (!f.isAccessible()) {
            f.setAccessible(changed = true);
        }
        return changed;
    }

    private void changeAccessToOpposite(Field field) {
        boolean oppositeAccess = !field.isAccessible();
        field.setAccessible(oppositeAccess);
    }

    public abstract boolean equalsExcludeId(DaoEntity o);

}
