package com.http.las.minsides.shared.entity;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.http.las.minsides.shared.util.ReflectionUtil.setValueFromTo;

public abstract class DaoEntity {
    protected DaoEntity dump;

    public DaoEntity() {

    }

    public boolean backup() {
        boolean done = setValueFromTo(dump, this);
        return done;
    }

    public boolean createDump() {
        try {
            dump = (DaoEntity) Class.forName(this.getClass().getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean created = setValueFromTo(this, dump);
        return created;
    }

    public abstract boolean equalsExcludeId(DaoEntity o);

}
