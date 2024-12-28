package com.vuongkma.orders_service.controllers;

import com.vuongkma.customer.helpers.ResponseFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/orders")
public class TestController {
    @GetMapping("test")
    @ResponseBody
    public ResponseEntity<Object> findOne() {

        return ResponseEntity.ok(ResponseFormat.build(
                "",
                "Congrats, the data information has been fetched successfully."
        ));

    }
}

