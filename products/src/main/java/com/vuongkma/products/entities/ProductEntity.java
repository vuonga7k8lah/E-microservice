package com.vuongkma.products.entities;

import com.vuongkma.products.helpers.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Products")
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Number price;
    private Integer stock_quantity;
    private Long category_id;
    private StatusEnum status;
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
