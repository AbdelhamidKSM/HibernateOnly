package org.example.cachingentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}