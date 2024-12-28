package com.vuongkma.orders_service.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;
@Data
public class ProductData {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private String status;
    private String thumbnail;
    private String images;
    private Date created_at;
    private Date updated_at;
    private Set<Object> categories;
}
