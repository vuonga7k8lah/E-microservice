package com.vuongkma.products.dto;


import com.vuongkma.products.helpers.enums.StatusEnum;
import lombok.Data;


@Data
public class CategoryProductDTO {
    private String name;
    private Integer parent_id;
    private String status;
    public StatusEnum getStatusEnum() {
        if (status == null) {
            return null;
        }
        return StatusEnum.valueOf(status.toUpperCase());
    }
}
