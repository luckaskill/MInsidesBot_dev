package com.http.las.minsides.server.base;

import com.http.las.minsides.entity.DaoEntity;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T extends DaoEntity> implements BaseDao<T> {
    private String genericClassName;

    public BaseDaoImpl() {
        String simpleName = ((ParameterizedType) (getClass().getGenericSuperclass()))
                .getActualTypeArguments()[0].toString();
        if (simpleName.contains(".")) {
            simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
        }
        this.genericClassName = simpleName;
    }

    @Override
    public void save(T value) {
        @Cleanup
        Session session = HibSessionFactory.open();
        session.saveOrUpdate(value);
    }

    @Override
    public void persist(T value) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        session.persist(value);
        transaction.commit();
    }

    @Override
    public void save(T... values) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        for (T value : values) {
            session.save(value);
        }
        transaction.commit();
    }

    @Override
    public void persist(T... values) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        for (T value : values) {
            session.persist(value);
        }
        transaction.commit();
    }

    @Override
    public List<T> selectAll() {
        @Cleanup
        Session session = HibSessionFactory.open();
        List<T> list = session.createQuery("FROM " + genericClassName).list();
        return list;
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public List<T> select(Set<Integer> ids) {
        @Cleanup
        Session session = HibSessionFactory.open();
        StringBuilder inCondition = new StringBuilder();
        for (Integer id : ids) {
            inCondition.append(id).append(", ");
        }
        inCondition.delete(inCondition.length() - 3, inCondition.length() - 1);
        List<T> list = session.createQuery("FROM " + genericClassName + " WHERE nid in(" + inCondition.toString() + ")").list();
        return list;
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public T select(Integer id) {
        @Cleanup
        Session session = HibSessionFactory.open();
        T result = (T) session.createQuery("FROM " + genericClassName + " WHERE nid = " + id).uniqueResult();
        return result;
    }


}
