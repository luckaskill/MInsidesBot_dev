package com.las.bots.minsides.shared.shared.entity;


import com.las.bots.minsides.shared.shared.util.ReflectionUtil;

public abstract class DaoEntity {
    protected DaoEntity dump;

    public DaoEntity() {

    }

    public boolean backup() {
        boolean done = ReflectionUtil.setValueFromTo(dump, this);
        return done;
    }

    public boolean createDump() {
        try {
            dump = (DaoEntity) Class.forName(this.getClass().getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean created = ReflectionUtil.setValueFromTo(this, dump);
        return created;
    }

    public abstract boolean equalsExcludeId(DaoEntity o);

}
