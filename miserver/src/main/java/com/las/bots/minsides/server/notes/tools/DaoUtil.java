package com.las.bots.minsides.server.notes.tools;

import com.las.bots.minsides.server.notes.config.HibSessionFactory;
import lombok.Cleanup;
import org.hibernate.Session;

import java.sql.Timestamp;

public class DaoUtil {
    public static Timestamp getCurTime() {
        @Cleanup
        Session session = HibSessionFactory.getSessionFactory().openSession();
        return (Timestamp) session.createSQLQuery("SELECT NOW()").uniqueResult();
    }
}
