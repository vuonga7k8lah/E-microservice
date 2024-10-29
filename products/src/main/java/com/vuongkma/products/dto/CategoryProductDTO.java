package com.vuongkma.products.dto;

import lombok.Data;

@Data
public class CategoryProductDTO {
    private String name;
    private Integer parent_id;
    private String status;
}
