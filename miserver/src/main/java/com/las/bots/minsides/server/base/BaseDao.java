package com.las.bots.minsides.server.base;

import com.las.bots.minsides.shared.shared.entity.DaoEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
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
