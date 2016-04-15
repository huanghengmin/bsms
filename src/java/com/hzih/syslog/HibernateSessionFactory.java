package com.hzih.syslog;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static final String configure = "hibernate_temp.cfg.xml";
    private static SessionFactory sessionFactory = null;
    /**
     * hibernate static
     */
    static{
        Configuration cfg = new Configuration();
        cfg.configure(configure);
        sessionFactory = cfg.buildSessionFactory();
    }


    public static Session createSession(){
        return sessionFactory.openSession();
    }

    private HibernateSessionFactory() {
    }
	
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	public static void rebuildSessionFactory() {
        Configuration cfg = new Configuration();
        cfg.configure(configure);
        sessionFactory = cfg.buildSessionFactory();
	}

    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
            session.close();
        }
    }

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}