package paginationAndSorting;

import baseTest.BaseTestHibernate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.relationships.Book;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PaginationAndSortingTest extends BaseTestHibernate {

/**
  In This example we will see :
  * pagination and sorting
  * how to express queries by combining between
      Criteria API( use the expressive and type-safe Criteria API for defining the query structure)
       & HQL/JPQL queries (capabilities to interact with the database.)
  * Working with scrollableResult (particularly useful when working with large datasets or when you need to perform batch processing or reporting tasks.)

  * */
    @Test
    public void testPaginationAndSorting() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);

        // Sorting
        query.orderBy(criteriaBuilder.asc(bookRoot.get("title")));

        // Pagination : the goal is to have only 5 result in a page
        int pageSize = 5;
        int firstResult = 0;

        // use Hql Query to be able to work more freely with db
        Query<Book> hibernateQuery = session.createQuery(query);
        hibernateQuery.setFirstResult(firstResult);
        hibernateQuery.setMaxResults(pageSize);

        List<Book> books = hibernateQuery.getResultList();
        assertNotNull(books);
        assertEquals(pageSize, books.size());
    }

    @Test
    public void workingWithScrollableResult (){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root);

        // working with scrollable result
        Query<Book> hibernateQuery = session.createQuery(query);
        ScrollableResults<Book> scrollableResults = hibernateQuery.scroll(ScrollMode.FORWARD_ONLY);

        while (scrollableResults.next()) {
            Book book =scrollableResults.get();
            assertNotNull(book);
        }
    }

}
