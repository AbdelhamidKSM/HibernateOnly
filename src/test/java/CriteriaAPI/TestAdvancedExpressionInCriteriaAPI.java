package CriteriaAPI;

import BaseTest.BaseTestHibernate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.relationships.Book;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestAdvancedExpressionInCriteriaAPI extends BaseTestHibernate {


    @Test
    public void testCriteriaAPIAdvancedExpressions() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);

        // predicates
        Predicate likePredicate = criteriaBuilder.like(bookRoot.get("title"), "Atomic Habits");
        Predicate betweenPredicate = criteriaBuilder.between(bookRoot.get("price"), 0, 8);
        Predicate isNullPredicate = criteriaBuilder.isNull(bookRoot.get("isbn"));
        Predicate inPredicate = bookRoot.get("publishDate").in(LocalDateTime.of(2023,6,30,15,1,30),LocalDateTime.of(2023,6,30,15,1,13));


        Predicate combinePredicate = criteriaBuilder.and(likePredicate, betweenPredicate, isNullPredicate ,inPredicate);
        // execute the query
        query.where(combinePredicate);

        List<Book> books = session.createQuery(query).getResultList();
        assertNotNull(books);
        assertEquals(1,books.size());
    }
}
