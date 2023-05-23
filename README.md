# HibernateOnly
About all JPA &amp; Hibernate 
In this example, we used :`EntityManager` for JPA annotations   and `Session` and `SessionFactory` for hibernate.

Changes made between Jpa and Hibernate:

1. Replaced `EntityManagerFactory` with `SessionFactory`. We now configure Hibernate using `Configuration` and `configure("hibernate.cfg.xml")` method.
2. Replaced `EntityManager` with `Session`. We open a new session using `sessionFactory.openSession()`.
3. Replaced JPA transaction with Hibernate transaction. We now use `Transaction` from Hibernate instead of `EntityTransaction` from JPA.
4. Replaced JPA persist, merge, and remove operations with Hibernate equivalents. We use `session.persist()`, `session.merge()`, and `session.remove()` methods instead of their JPA counterparts.
5. Replaced `em.find()` with `session.get()`. We use `session.get()` to retrieve entities from the database using their identifier.
6. `hibernate.cfg.xml` is the file for Hibernate and `persistence.xml` is the equivalent in JPA.
