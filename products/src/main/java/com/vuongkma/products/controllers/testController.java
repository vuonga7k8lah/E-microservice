package com.vuongkma.products.controllers;

import com.vuongkma.products.helpers.ResponseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("api/v1/products")
@Slf4j
public class testController {
    @GetMapping("")
    public ResponseEntity<Object>  registerCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(123, "Congrats, all users have been fetched successfully.")
        );
    }
}
