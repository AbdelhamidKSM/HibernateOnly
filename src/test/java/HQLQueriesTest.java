import org.example.relationships.Author;
import org.example.relationships.Book;
import org.example.relationships.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class HQLQueriesTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setup() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
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
    public void select_All_Authors() {
        String hql = "SELECT a FROM Author a";
        Query<Author> query = session.createQuery(hql, Author.class);
        List<Author> authors = query.getResultList();

        assertNotNull(authors);
    }

    @Test
    public void select_Book_By_Author() {
        // create an author
        Author author = new Author();
        author.setName("Gary Keller");

        // create a book
        Book book = new Book();
        book.setTitle("The One Thing");
        book.setAuthor(author);

        author.setBooks(List.of(book));

        session.persist(author);
        session.flush();
        session.clear();

        String hql = "SELECT b FROM Book b WHERE b.author = :author";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("author", author);
        List<Book> books = query.getResultList();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("The One Thing", books.get(0).getTitle());
    }

    @Test
    public void select_book_With_Genre() {
        Genre genre = new Genre();
        Book book = new Book();

        genre.setName("Personal Development");
        genre.getBooks().add(book);

        book.setTitle("Atomic Habits");
        book.getGenres().add(genre);

        session.persist(genre);
        session.persist(book);
        session.flush();
        session.clear();

        String hql = "SELECT b FROM Book b JOIN b.genres g WHERE g = :genre";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("genre", genre);
        List<Book> books = query.getResultList();

        assertNotNull(books);
        assertEquals(1, books.size());
    }

    @Test
    public void update_Book_Price() {

        Book book = new Book();
        book.setTitle("148 Hours ");
        book.setPrice(40.40);

        session.persist(book);
        session.flush();
        session.clear();

        String hql = "UPDATE Book b SET b.price = :newPrice WHERE b.id = :bookId";
        Query<?> query = session.createQuery(hql);
        query.setParameter("newPrice", 20.20);
        query.setParameter("bookId", book.getId());
        int updatedCount = query.executeUpdate();

        assertEquals(1, updatedCount);

        Book updatedBook = session.find(Book.class, book.getId());
        assertEquals(20.20, updatedBook.getPrice(), 0.001);
    }

    @Test
    public void delete_Book_Before_Date() {
        Book book = new Book();
        book.setTitle("148 Secret Adversary");
        book.setPrice(40.40);
        book.setPublishDate(LocalDate.of(2023, 4, 3).atStartOfDay());

        session.persist(book);
        session.flush();
        session.clear();

        // delete the book price
        String hql = "delete Book b  WHERE b.publishDate <: date ";
        Query<?> query = session.createQuery(hql);
        query.setParameter("date", LocalDate.of(2023,4,4).atStartOfDay());
        int updatedCount = query.executeUpdate();

        // Assert the updated count
        assertEquals(1,updatedCount);

        // Assert the update from the db
        Book deletedBook = session.find(Book.class , book.getId());
        // assert the update price
        assertNull(deletedBook);


    }
}