package com.vuongkma.products.controllers;

import com.vuongkma.products.dto.ProductRequestDTO;
import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.entities.ProductEntity;
import com.vuongkma.products.helpers.ResponseFormat;
import com.vuongkma.products.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/v1/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping
    private ResponseEntity<Object> create(@RequestBody ProductRequestDTO productRequestDTO){

        var data = productService.create(productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(
                        data.getId(),
//                        "",
                        "Congrats, the data has been created with the id  successfully."
                )
        );
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> result = productService.search(search, pageable);
        Map<String, Object> data = new HashMap<>();
        var items = result.getContent();
        data.put("items",items);
        data.put("currentPage", result.getNumber());
        data.put("totalItems", result.getTotalElements());
        data.put("totalPages", result.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(data, "Data fetched successfully with search and pagination.")
        );
    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") int id,Integer quantity){
        var data = productService.updateStock(id,quantity);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(
                        data,
                        "Congrats, the data has been updated successfully."
                )
        );
    };
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable("id") int id){
        var data = productService.findOne((long) id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(
                        data,
                        "Congrats, the data has been get successfully."
                )
        );
    };
}
