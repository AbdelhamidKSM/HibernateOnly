import BaseTest.BaseTestJPA;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.relationships.Author;
import org.example.relationships.Book;
import org.example.relationships.Genre;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


public class JPQLQueriesTest extends BaseTestJPA {
    @Test
    public void select_All_Authors() {
// we work here with TypedQuery to specialize the type of the query
        String jpql = "SELECT a FROM Author a ";
        TypedQuery<Author> query = entityManager.createQuery(jpql, Author.class);
        List<Author> authors = query.getResultList();

        assertNotNull(authors);
    }

    @Test
    public void select_Book_By_Author() {
        // create an author
        Author author = new Author();
        author.setName("Gary Keller");

        // create a a book
        Book book = new Book();
        book.setTitle("The One Thing");
        book.setAuthor(author);

        author.setBooks(List.of(book));

        //  we start manging our entity (managed LifeCycle of Persistence Context ) by persist the entity to the database
        entityManager.persist(author);
        // flush and clear the entity manger to synchronize the changes
        entityManager.flush();
        entityManager.clear();

        String jpql = "SELECT b FROM Book b WHERE b.author= :author ";
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        query.setParameter("author", author);
        List<Book> books = query.getResultList();

        // assert
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("The One Thing", books.get(0).getTitle());
    }


    @Test
    public void select_book_With_Genre() {

        // create a Genre and relate it to a book
        Genre genre = new Genre();
        Book book = new Book();

        /*
         * java.lang.UnsupportedOperationException  is typically thrown when we  attempt to modify an unmodifiable collection or perform an unsupported operation on a collection.
         * In my case, the exception is thrown because you are using List.of() to create an immutable list for the genres property of the Book entity.
         * */
        genre.setName("Personal Development");
        genre.getBooks().add(book);

        book.setTitle("Atomic Habits");
        book.getGenres().add(genre);

        //  we start manging our entity (managed LifeCycle of Persistence Context ) by persist the entity to the database
        entityManager.persist(genre);
        entityManager.persist(book);

        // flush and clear the entity manger to synchronize the changes
        entityManager.flush();
        entityManager.clear();

        // since that we have a many to many relation-ship, and we're working with a join table we need to a join in our query
        String jpql = "SELECT b FROM Book b JOIN b.genres g WHERE g= :genre";
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        query.setParameter("genre", genre);
        List<Book> books = query.getResultList();

        // assert
        assertNotNull(books);
        assertEquals(1, books.size());
    }


    @Test
    public void update_Book_Price() {

        // create a Book with a price
        Book book = new Book();
        book.setTitle("148 Hours ");
        book.setPrice(40.40);

        // persist the entity into the db
        entityManager.persist(book);
        // flush and clear the entity manger to synchronize the changes
        /*
        flush :is used to synchronize the changes made in the persistence context with the underlying database.
               It forces any pending changes to be written to the database immediately.
        Cleat :clears the persistence context. It detaches all managed entities from the persistence context,
               effectively removing them from the managed state.

        the Order is Important when we call these 2 methods to  ensures that the book is detached from the persistence context,
        allowing the subsequent update query to work as expected.
        * */

        entityManager.flush();
        entityManager.clear();

        // update the book price
        String jpql = "UPDATE Book b SET b.price = :newPrice WHERE b.id=:bookId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("newPrice",20.20);
        query.setParameter("bookId",book.getId());
        int updatedCount = query.executeUpdate();

        // Assert the updated count
        assertEquals(1,updatedCount);

        // Assert the update from the db
        Book updateBook = entityManager.find(Book.class , book.getId());
        // assert the update price
        assertEquals(20.20 , updateBook.getPrice(),0.001); // Use delta for floating-point comparisons OR Decimal

    }

    @Test
    public void delete_Book_Before_Date () {

        // create a Book with a price
        Book book = new Book();
        book.setTitle("148 Secret Adversary");
        book.setPrice(40.40);
        book.setPublishDate(LocalDate.of(2023 ,4,3).atStartOfDay());

        // manage the entity lifecycle in the persistence context
        entityManager.persist(book);
        entityManager.flush();
        entityManager.clear();

        // delete the book price
        String jpql = "delete Book b  WHERE b.publishDate <: date ";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("date", LocalDate.of(2023,4,4).atStartOfDay());
        int updatedCount = query.executeUpdate();

        // Assert the updated count
        assertEquals(1,updatedCount);

        // Assert the update from the db
        Book deletedBook = entityManager.find(Book.class , book.getId());
        // assert the update price
        assertNull(deletedBook);
    }


}
