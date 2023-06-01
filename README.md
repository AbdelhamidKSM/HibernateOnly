# Hibernate_JPA_Only

This readme file provides a summary of the topics covered in this app (JPA/Hibernate) persistence Context

##  Changes made between JPA and Hibernate

Replaced `EntityManager` for JPA annotations and `Session` and `SessionFactory` for Hibernate.
Replaced `EntityManagerFactory` with `SessionFactory`. We now configure Hibernate using `Configuration` and `configure("hibernate.cfg.xml")` method.
Replaced `EntityManager` with `Session`. We open a new session using `sessionFactory.openSession()`.
Replaced JPA transaction with Hibernate transaction. We now use `Transaction` from Hibernate instead of `EntityTransaction` from JPA.
Replaced JPA persist, merge, and remove operations with Hibernate equivalents. We use `session.persist()`, `session.merge()`, and `session.remove()` methods instead of their JPA counterparts.
Replaced `em.find()` with `session.get()`. We use `session.get()` to retrieve entities from the database using their identifier.
`hibernate.cfg.xml` is the file for Hibernate and `persistence.xml` is the equivalent in JPA.

##  Associations & Relationships

We'll use a bookstore scenario with three entities: `Author`, `Book`, `Review`, `Genre`, and `AuthorProfile`. The relationships between these entities are as follows:

One-to-One Relationship:
- An `Author` can have only one `Book` as their favorite book.
- An `Author` can have an optional `AuthorProfile`.

One-to-Many Relationship:
- An `Author` can write multiple `Books`.
- A `Book` can have multiple `Reviews`.

Many-to-Many Relationship:
- A `Book` can have multiple `Genres`.
- A `Genre` can be associated with multiple `Books`.

##  Criteria API (Hibernate)

The Criteria API is a programmatic way of building type-safe queries in Hibernate. It provides an alternative approach to writing queries using a fluent and object-oriented syntax, rather than relying on JPQL or SQL strings.

## JPA Overview

JPA is a specification that provides a standard way of managing relational data in Java applications. It defines a set of interfaces and annotations for object-relational mapping (ORM).

## Hibernate

Hibernate is a popular implementation of the JPA specification. It is an ORM framework that allows developers to map Java objects to database tables and provides powerful features for interacting with databases.

## Hibernate Configuration

To use Hibernate, you need to configure it using the `hibernate.cfg.xml` file. This configuration file allows you to specify various settings, including the database connection details, dialect, and entity mappings.

## Entity Mapping

Entity mapping is the process of mapping Java classes to database tables. We covered how to define entity classes using annotations such as `@Entity`, `@Table`, and `@Column`. These annotations help define the relationships between entities and their corresponding database tables and columns.

## Relationships

We explored different types of relationships between entities, such as one-to-one, one-to-many, and many-to-many. We discussed how to establish these relationships using annotations like `@OneToOne`, `@OneToMany`, and `@ManyToMany`.

## Persistence Context

The persistence context represents the set of managed entities in a JPA application. We discussed how the persistence context tracks changes made to entities and synchronizes them with the database using the `EntityManager`.

## Caching

Caching is an important aspect of performance optimization in JPA. We covered the concept of caching and focused on the second-level cache. We explored how to configure and use the second-level cache using Hibernate's cache-related properties.

## Cascading

Cascading is a feature that allows automatic persistence of related entities. We discussed cascading operations such as persist (insert), merge (update), remove (delete), and refresh. We also covered how to configure cascading behavior using the `cascade` attribute in relationship annotations.

## Querying with JPQL

JPQL (Java Persistence Query Language) is a SQL-like query language specific to JPA. We explored how to write JPQL queries using entity classes and their relationships to retrieve data from the database.

## Criteria API

The Criteria API provides a programmatic way to build type-safe queries using a fluent API. We briefly discussed the Criteria API and how it can be used to construct dynamic and complex queries.

## Transactions

Transactions are crucial for ensuring data consistency and integrity in JPA applications. We covered how to manage transactions using the `@Transactional` annotation, which allows you to specify the boundaries of a transactional method.

## Testing with JUnit

We briefly worked on unit testing in JPA applications using the JUnit framework. Writing unit tests helps verify the correctness of your code and ensures that interactions with the database are working as expected.

## Quick Definitions

- **Persistence Context**: The persistence context is the working environment where entities are managed during their lifecycle in ORM frameworks.
- **Cascading**: Cascading is a feature that automatically applies certain operations from parent entities to associated child entities, such as persist (insert), merge (update), remove (delete), and refresh.
