package com.vuongkma.orders_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "order_item")
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    private Integer product_id;
    private Integer quantity;
    private Integer price;
    private Integer total;


    private Date created_at;

    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
}
