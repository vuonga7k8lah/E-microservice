package com.vuongkma.orders_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private String total;
    private String shipping_address;
    private String status;
    private String order_date;
    private String payment_method;
    private Date created_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
}
