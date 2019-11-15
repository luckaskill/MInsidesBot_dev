package com.http.las.minsides.server.base;

import com.http.las.minsides.entity.DaoEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BaseDao <T extends DaoEntity> {
    void save(T value);

    void persist(T value);

    List<T> selectAll();

    T select(Integer id);

    List<T> select(Set<Integer> ids);

    void persist(Collection<T> values);

    void save(Collection<T> values);

    void remove(Collection<T> values);

    void remove(T t);

    void remove(Integer id);

    void delete(T obj);

}
