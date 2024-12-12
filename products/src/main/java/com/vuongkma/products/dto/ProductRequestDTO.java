package com.vuongkma.products.dto;

import com.vuongkma.products.helpers.enums.StatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private Integer price;
    private Integer stock_quantity;
    private StatusEnum status;
    private List<String> thumbnail;
    private List<String> images;
    private String category;

}
