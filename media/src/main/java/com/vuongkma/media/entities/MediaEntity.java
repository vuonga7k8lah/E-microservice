package com.vuongkma.media.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "images")
@Getter
@Setter
public class MediaEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String url;
    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }

}
