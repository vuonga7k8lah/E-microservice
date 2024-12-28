package com.vuongkma.orders_service.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateOrderDTO {
    private String order_date;
    private String shipping_address;
    private String total;
    private Long user_id;
    private List<ItemOrderDTO> items;

    private String setOrder_date(String order_date){
        this.order_date = new Date().toString();
        return order_date;
    }
    public List<ItemOrderDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemOrderDTO> items) {
        this.items = items;
    }
}
