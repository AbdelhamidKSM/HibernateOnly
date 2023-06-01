package org.example.relationshipsentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR) //Heads up! add jdbc type code. Just thought I'd mention it!
    private String name;

    @Column(name = "average_price")
    private Double averagePrice;


    /*    We use the Unidirectional relationship because each author has a profile, but the profile does not require a direct reference to the author. */

    /**
     * we can even use Join Column, But Using PrimaryKeyJoinColumn is better than Join Column for one-to-one relations.
     * @ OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
     * @ JoinColumn (name = "author_profile_id")
     * private AuthorProfile authorProfile;
     */

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "author")
    private AuthorProfile authorProfile;

    /*  In this case let's try with bidirectional relationships. let's assume that we need to navigate from an author to their favorite book and vice versa. */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "favorite_book_id")
    private Book favoritebook;

    @OrderColumn(name = "book_order")
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    @Embedded
    private ContactDetails contactDetails ;

}
