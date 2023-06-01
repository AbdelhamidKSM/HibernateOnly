package caching;

import baseTest.BaseTestHibernate;
import org.example.cachingentities.Category;
import org.example.cachingentities.Product;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCachingInHibernate extends BaseTestHibernate {

    @Test
    public void testCacheInHibernate() {
        Category category = new Category();
        category.setName("Electronics");
        session.persist(category);

        Product product1 = new Product();
        product1.setName("Laptop");
        product1.setCategory(category);
        session.persist(product1);

        Product product2 = new Product();
        product2.setName("Phone");
        product2.setCategory(category);
        session.persist(product2);

        // Enable query caching
        String hql = "FROM Product p WHERE p.category = :category";
        Query<Product> query = session.createQuery(hql, Product.class);
        query.setParameter("category", category);
        query.setCacheable(true);

        // Query executed and results cached
        List<Product> products = query.list();

        // Same query executed again, results retrieved from cache
        List<Product> sameProducts = query.list();

        // assertions
        assertEquals(products ,sameProducts);

//        // Obtain the Hibernate Session
//        SessionImplementor sessionImplementor = (SessionImplementor) session;
//
//        // Get the cache region for the "product" entity
//        Region productRegion = sessionImplementor.getFactory().getCache().getRegion("product");
//
//        // Get the cache statistics
//        Statistics cacheStatistics = sessionImplementor.getFactory().getStatistics();
//
//        // Enable statistics if not enabled already
//        if (!cacheStatistics.isStatisticsEnabled()) {
//            cacheStatistics.setStatisticsEnabled(true);
//        }
//
//        // Print cache statistics
//        System.out.println("Product Cache Statistics:");
//        System.out.println("Cache Hits: " + cacheStatistics.getEntityStatistics(productRegion.getName()).getCacheHitCount());
//        System.out.println("Cache Misses: " + cacheStatistics.getEntityStatistics(productRegion.getName()).getCacheMissCount());
//        System.out.println("Cache Puts: " + cacheStatistics.getEntityStatistics(productRegion.getName()).getCachePutCount());
//
//        // Get the cache region for the "category" entity
//        Region categoryRegion = sessionImplementor.getFactory().getCache().getRegion("category");
//
//        // Print cache statistics
//        System.out.println("Category Cache Statistics:");
//        System.out.println("Cache Hits: " + cacheStatistics.getEntityStatistics(categoryRegion.getName()).getCacheHitCount());
//        System.out.println("Cache Misses: " + cacheStatistics.getEntityStatistics(categoryRegion.getName()).getCacheMissCount());
//        System.out.println("Cache Puts: " + cacheStatistics.getEntityStatistics(categoryRegion.getName()).getCachePutCount());

   }
}
