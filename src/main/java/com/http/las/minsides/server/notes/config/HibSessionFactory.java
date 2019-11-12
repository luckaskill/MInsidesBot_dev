package com.http.las.minsides.server.notes.config;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibSessionFactory {
    private static final org.hibernate.SessionFactory sessionFactory = buildSessionFactory();

    private static org.hibernate.SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session open() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
