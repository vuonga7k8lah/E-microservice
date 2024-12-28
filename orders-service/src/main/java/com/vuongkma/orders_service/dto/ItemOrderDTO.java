package com.vuongkma.orders_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOrderDTO {
    private Integer product_id;
    private Integer quantity;
    private Integer price;
}
