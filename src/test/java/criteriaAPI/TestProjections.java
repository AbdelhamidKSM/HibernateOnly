package criteriaAPI;

import baseTest.BaseTestHibernate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.relationshipsentities.Book;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TestProjections extends BaseTestHibernate {

    @Test
    public void testProjections() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Book> bookRoot = query.from(Book.class);

        query.multiselect(bookRoot.get("title"), bookRoot.get("isbn"), bookRoot.get("price"), bookRoot.get("publishDate"));

        List<Object[]> results = session.createQuery(query).getResultList();
        for (Object[] result : results) {
            String title = (String) result[0];
            String  isbn = (String) result[1];
            Double price = (Double) result[2];
            LocalDateTime publishDate = (LocalDateTime) result[3];

            // Process the selected fields as needed
            System.out.println("Title: " + title + ", Author: " + isbn + ", Price: " + price + ", Publish Date:" +publishDate);
            assertNotNull(result);
        }
    }


    @Test
    public void testAggregatingFunctions(){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Book> bookRoot = query.from(Book.class);
        // we use avg function of the CriteriaBuilder  to calculate the avg price  of all books
        query.select(criteriaBuilder.avg(bookRoot.get("price")));

        // to get the result,we used getSingleResult()
        Double averagePrice = session.createQuery(query).getSingleResult();
        System.out.println("Average Price: " + averagePrice);

        assertNotNull(averagePrice);

    }

}
