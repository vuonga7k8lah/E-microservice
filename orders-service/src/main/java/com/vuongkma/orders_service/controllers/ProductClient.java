package com.vuongkma.orders_service.controllers;

import com.vuongkma.orders_service.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT", url = "localhost:8083", path = "api/v1/products")
public interface ProductClient {
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") int id, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") int id, @RequestParam("quantity") Integer quantity, @RequestHeader("Authorization") String token);
}
