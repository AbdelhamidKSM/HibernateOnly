package CriteriaAPI;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.relationships.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CheckCriteriaAPIAdvancedTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

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

    @Test
    public void test_with_JPQL() {
        TypedQuery<Author> query = session.createQuery("SELECT a FROM Author a WHERE a.name=:authorName", Author.class);
        query.setParameter("authorName", "ECKHART TOLL");
        List<Author> authors = query.getResultList();

        assertNotNull(authors);
        assertEquals(43, authors.size());
    }

    @Test
    public void test_with_CriteriaAPI() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

        Predicate namePredicate = criteriaBuilder.equal(root.get("name"), "ECKHART TOLL");
        query.select(root).where(namePredicate);

        List<Author> authors = session.createQuery(query).getResultList();
        assertNotNull(authors);
        assertEquals(43, authors.size());
    }

}
