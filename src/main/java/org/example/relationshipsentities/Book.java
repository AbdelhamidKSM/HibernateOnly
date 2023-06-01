package org.example.relationshipsentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQuery(
        name = "Book.findByTitle",
        query = "SELECT b FROM Book b WHERE b.title =:title"
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "price")
    private Double price;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "Book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id"))
    private List<Genre> genres = new ArrayList<>();


    @OneToOne(mappedBy = "favoritebook", orphanRemoval = true)
    private Author authorFavoriteBook;


}
