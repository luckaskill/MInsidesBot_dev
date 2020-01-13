package com.las.bots.minsides.server.base;

import com.las.bots.minsides.server.notes.config.HibSessionFactory;
import com.las.bots.minsides.shared.shared.entity.DaoEntity;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unchecked", "JpaQlInspection"})
public class BaseDaoImpl<T extends DaoEntity> implements BaseDao<T> {
    private String entityName;

    public BaseDaoImpl() {
        this.entityName = defineEntityName();
    }

    private String defineEntityName() {
        String simpleName = ((ParameterizedType) (getClass().getGenericSuperclass()))
                .getActualTypeArguments()[0].toString();
        if (simpleName.contains(".")) {
            simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
        }
        return simpleName;
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
        session.saveOrUpdate(value);
        transaction.commit();
    }

    @Override
    public void save(Collection<T> values) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        for (T value : values) {
            session.save(value);
        }
        transaction.commit();
    }

    @Override
    public void remove(Collection<T> values) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        for (T t : values) {
            remove(session, t);
        }
        transaction.commit();
    }

    @Override
    public void remove(T obj) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        remove(session, obj);
        transaction.commit();
    }

    private void remove(Session session, T obj) {
        session.remove(obj);
    }

    @Override
    public void remove(Integer id) {
        @Cleanup
        Session session = HibSessionFactory.open();
        remove(session, id);
    }

    @Override
    public void delete(T obj) {
        @Cleanup
        Session session = HibSessionFactory.open();
        session.delete(obj);
    }

    private void remove(Session session, Integer id) {
        session.createQuery("DELETE FROM " + entityName + " WHERE nid=" + id).executeUpdate();
    }

    @Override
    public void persist(Collection<T> values) {
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
        List<T> list = session.createQuery("FROM " + entityName).list();
        return list;
    }

    @Override
    public List<T> select(Set<Integer> ids) {
        @Cleanup
        Session session = HibSessionFactory.open();
        StringBuilder inCondition = new StringBuilder();
        for (Integer id : ids) {
            inCondition.append(id).append(", ");
        }
        inCondition.delete(inCondition.length() - 3, inCondition.length() - 1);
        List<T> list = session.createQuery("FROM " + entityName + " WHERE nid IN(" + inCondition.toString() + ")").list();
        return list;
    }

    @Override
    public T select(Integer id) {
        @Cleanup
        Session session = HibSessionFactory.open();
        T result = (T) session.createQuery("FROM " + entityName + " WHERE nid = " + id).uniqueResult();
        return result;
    }
}
