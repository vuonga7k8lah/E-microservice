package com.vuongkma.orders_service.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private String message;
    private ProductData data;
}
