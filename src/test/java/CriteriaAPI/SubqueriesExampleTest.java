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

import static org.junit.Assert.assertNotNull;

public class SubqueriesExampleTest {

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
    public void test_SubQuery() {
        // Create the outer query
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        // Select * from Author
        Root<Author> root = query.from(Author.class); // outer query

        /*  count book writer by an author */

        // start creating the sub Query
        Subquery<Long> subquery = query.subquery(Long.class);
        // select * from Book
        Root<Book> subRoot = subquery.from(Book.class);
        // joins between entities
        Join<Book ,Author> subBooksAuthor = subRoot.join("author");

        // SELECT count * FROM  Book  WHERE book.authorId = Author.Id
        subquery.select(criteriaBuilder.count(subRoot.get("id")))
                .where(criteriaBuilder.equal(subBooksAuthor.get("id"), root.get("id")));


       // add the exists predicate with the subquery to the outer query
        /*  query.where(criteriaBuilder.exists(subquery));  it's correct as well */
        query.select(root)
                .where(criteriaBuilder.exists(subquery));

        // we execute the main Query
        List<Author> authors = session.createQuery(query).getResultList();


        assertNotNull(authors);

    }
}