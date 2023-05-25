# Hibernate_JPA_Only
About all JPA &amp; Hibernate 
In this example, we used :`EntityManager` for JPA annotations   and `Session` and `SessionFactory` for hibernate.

### Chap 1 : Changes made between Jpa and Hibernate:

1. Replaced `EntityManagerFactory` with `SessionFactory`. We now configure Hibernate using `Configuration` and `configure("hibernate.cfg.xml")` method.
2. Replaced `EntityManager` with `Session`. We open a new session using `sessionFactory.openSession()`.
3. Replaced JPA transaction with Hibernate transaction. We now use `Transaction` from Hibernate instead of `EntityTransaction` from JPA.
4. Replaced JPA persist, merge, and remove operations with Hibernate equivalents. We use `session.persist()`, `session.merge()`, and `session.remove()` methods instead of their JPA counterparts.
5. Replaced `em.find()` with `session.get()`. We use `session.get()` to retrieve entities from the database using their identifier.
6. `hibernate.cfg.xml` is the file for Hibernate and `persistence.xml` is the equivalent in JPA.


### Chap 2 :  Associations & relationships 
We'll use a bookstore scenario with three entities: Author, Book, Review , Genre and AuthorProfile. The relationships between these entities are as follows:
#### One-to-One Relationship: 
An Author can have only one Book as their favorite book.

An Author can have an optional AuthorProfile.
#### One-to-Many Relationship:
An Author can write multiple Books.

A Book can have multiple Reviews.
##### Many-to-Many Relationship:

( Owning side (@JoinTable, intermediate join table)

 Inverse side (mappedBy, references the owning side))

A Book can have multiple Genres.

A Genre can be associated with multiple Books.