package com.vuongkma.products.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer price;

    private Integer stockQuantity;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_products_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private Set<CategoryProductEntity> categories;

    private String status;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
    @Column(columnDefinition = "TEXT")
    private String images;

    private Date created_at;
    private Date updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }
}
