package baseTest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

public class BaseTestHibernate {
    SessionFactory sessionFactory;
    public Session session;
    Transaction transaction;

    @Before
    public void setUp() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void cleanUp() {
        transaction.rollback();
        session.close();
        sessionFactory.close();
    }
}
