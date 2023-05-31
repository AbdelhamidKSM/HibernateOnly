package baseTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;

public abstract class BaseTestJPA {
     EntityManagerFactory entityManagerFactory;
     public EntityManager entityManager;
     EntityTransaction entityTransaction;


    @Before
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-only");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }

    @After
    public void cleanUp() {
        entityTransaction.rollback();
        entityManager.close();
        entityManagerFactory.close();
    }
}
