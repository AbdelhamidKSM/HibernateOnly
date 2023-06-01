package namedVsNativeQuery;

import baseTest.BaseTestHibernate;
import jakarta.persistence.TypedQuery;
import org.example.relationshipsentities.Book;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NamedVsNativeQueryTest extends BaseTestHibernate {

    @Test
    public void testNativeQuery() {

        /*
        * Native queries allow you to write SQL queries directly using native database-specific syntax.
        * */
        String sql = "SELECT * FROM Book WHERE Book.title =:title";
        Query<Book> query = session.createNativeQuery(sql, Book.class);
        query.setParameter("title", "Atomic Habits");
        List<Book> books = query.getResultList();

        assertNotNull(books);
        books.forEach(book -> assertEquals("Atomic Habits", book.getTitle()));
    }

    @Test
    public void testNamedQuery() {
        /*
        Named queries are pre-defined queries that are associated with an entity or mapped object
        in the Hibernate mapping files or annotations.
        */
        TypedQuery<Book> query = session.createNamedQuery("Book.findByTitle", Book.class);
        query.setParameter("title", "Atomic Habits");
        List<Book> books = query.getResultList();

        assertNotNull(books);
        books.forEach(book -> assertEquals("Atomic Habits", book.getTitle()));
    }

}
