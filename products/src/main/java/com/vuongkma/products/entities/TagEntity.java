package com.vuongkma.products.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Tags")
@Getter
@Setter
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
}
