package com.techouts.utils.hibernateutil;

import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    static {
        
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    private HibernateUtil() {
    }

    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static Session getHibernateSession() {

        return sessionFactory.openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
