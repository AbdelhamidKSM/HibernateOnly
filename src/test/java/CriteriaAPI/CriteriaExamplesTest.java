package CriteriaAPI;

import jakarta.persistence.criteria.*;
import org.example.relationships.Author;
import org.example.relationships.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CriteriaExamplesTest {

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

    /* Example:  Joins ==> lazily fetched
    different types of joins are available:

    ** Inner Join: Returns only the matching rows between two tables.
    ** Left Outer Join: Returns all rows from the left table and matching rows from the right table.
    ** Right Outer Join: Returns all rows from the right table and matching rows from the left table.
    ** Full Outer Join: Returns all rows from both tables, including unmatched rows.

     we used inner join in this case to return only the rows that have matching values in both tables (most commonly used )
     */
    @Test
    public void inner_Join_Operations() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);

        // inner Join
        Join<Author, Book> bookAuthorJoin = bookRoot.join("author");
        query.select(bookRoot)
                .where(criteriaBuilder.equal(bookAuthorJoin.get("name"), "ECKHART TOLL"));

        List<Book> innerJoinResult = session.createQuery(query).getResultList();
        assertNotNull(innerJoinResult);
        assertEquals(77, innerJoinResult.size());

    }

    // Fetch ==> eagerly fetched (Brings everything)
    @Test
    public void fetch_Operations() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);

        // Fetch the author association eagerly
        bookRoot.fetch("author", JoinType.INNER);

        query.select(bookRoot).distinct(true);

        List<Book> books = session.createQuery(query).getResultList();

        assertNotNull(books);
        assertEquals(77, books.size());
    }

}
