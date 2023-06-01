import baseTest.BaseTestJPA;
import org.example.relationshipsentities.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RelationShipsCrudTest  extends BaseTestJPA {
    @Test
    public  void test_Create_Relations(){
        // Create a new author
        Author author = createAuhtor();

        // create a  list of books
        List<Book> books = new ArrayList<>();

        // Create genres
        Genre genre1 = new Genre();
        genre1.setName("Personal Development");
        Genre genre2 = new Genre();
        genre2.setName("Business");

        // Creat  new books
        Book firstbook  = createBook("The Power of Now","123456",author);
        firstbook.getGenres().add(genre1);

        Book secondBook = createBook("The Power of Now_2","1234567",author);
        secondBook.getGenres().add(genre2);


        // set the author favorite book
        author.setFavoritebook(firstbook);

        books.add(firstbook);
        books.add(secondBook);

        // set the book to the author books list
        author.setBooks(books);

        // Create an authorProfile
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setBiography("Author for personal development");
        authorProfile.setAuthor(author);


        // set the author profile
        author.setAuthorProfile(authorProfile);

        // Create contact Details
        ContactDetails contactDetails  = new ContactDetails();
        contactDetails.setEmail("Echkart@gmail.com");
        contactDetails.setPhone("06 88888888");

        // set the contact Details
        author.setContactDetails(contactDetails);

        // Persist the Author
        entityManager.persist(genre1);
        entityManager.persist(genre2);
        entityManager.persist(author);
        // flush and clear entity manager to synchronize the changes
        entityManager.flush();
        entityManager.clear();

        // Retrieve the author from the database
        Author retrivedAuthor = entityManager.find(Author.class, author.getId());


        // check the author
        assertNotNull(retrivedAuthor);
        assertEquals("ECKHART TOLL",retrivedAuthor.getName());

        // check the favorite book
        assertNotNull(retrivedAuthor.getFavoritebook());
        assertEquals("The Power of Now",retrivedAuthor.getFavoritebook().getTitle());
        assertEquals("123456",retrivedAuthor.getFavoritebook().getIsbn());

        // check the author profile
        assertNotNull(retrivedAuthor.getAuthorProfile());
        assertEquals("Author for personal development",retrivedAuthor.getAuthorProfile().getBiography());

        // check the contactDetails
        assertNotNull(retrivedAuthor.getContactDetails());
        assertEquals("Echkart@gmail.com",retrivedAuthor.getContactDetails().getEmail());
        assertEquals("06 88888888",retrivedAuthor.getContactDetails().getPhone());

        // check the books lists
        assertNotNull(retrivedAuthor.getBooks());
        assertEquals(2,retrivedAuthor.getBooks().size());
        assertEquals("The Power of Now", retrivedAuthor.getBooks().get(0).getTitle());
        assertEquals("The Power of Now_2", retrivedAuthor.getBooks().get(1).getTitle());


        // check the genres for our first book
        List<Book> booksretrived = retrivedAuthor.getBooks();
        List<Genre> retrievedGenres = booksretrived.get(0).getGenres();
        assertNotNull(retrievedGenres);
        assertEquals(1, retrievedGenres.size());
        assertEquals("Personal Development", retrievedGenres.get(0).getName());

    }

    private static Book createBook(String title , String isbn , Author author) {
        Book book =new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        return book;
    }

    private static Author createAuhtor() {
        Author author =new Author();
        author.setName("ECKHART TOLL");
        return author;
    }


    @Test
    public void testUpdate() {
        // Create a new author
        Author author = new Author();
        author.setName("Jane Smith");
        author.setAveragePrice(11D);
        entityManager.persist(author);

        // Update the author's name
        author.setName("Victor Hugo");
        entityManager.merge(author);

        // Flush and clear the EntityManager to synchronize the changes
        entityManager.flush();
        entityManager.clear();

        // Retrieve the updated author from the database
        Author retrievedAuthor = entityManager.find(Author.class, author.getId());

        assertNotNull(retrievedAuthor);
        assertEquals("Victor Hugo", retrievedAuthor.getName());
    }

    @Test
    public void testDelete() {
        // Create a new author
        Author author = new Author();
        author.setName("Mark Brown");
        entityManager.persist(author);

        // Delete the author from the database
        entityManager.remove(author);

        Author retrivedAuthor = entityManager.find(Author.class , author.getId());
        assertNull(retrivedAuthor);
    }
}
