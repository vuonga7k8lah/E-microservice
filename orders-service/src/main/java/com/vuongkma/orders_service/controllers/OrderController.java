package com.vuongkma.orders_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vuongkma.customer.helpers.ResponseFormat;
import com.vuongkma.orders_service.dto.CreateOrderDTO;
import com.vuongkma.orders_service.dto.ItemOrderDTO;
import com.vuongkma.orders_service.entities.OrderEntity;
import com.vuongkma.orders_service.helpers.APIHelper;
import com.vuongkma.orders_service.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping(APIHelper.restRoot)
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;


    @PostMapping()
    private ResponseEntity<Object> createOrder(@RequestBody CreateOrderDTO createOrderDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {

            //map data
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus("pending");
            orderEntity.setShipping_address(createOrderDTO.getShipping_address());
            orderEntity.setTotal(createOrderDTO.getTotal());
            orderEntity.setUser_id(createOrderDTO.getUser_id());
            orderEntity.setShipping_address(createOrderDTO.getShipping_address());
            if (createOrderDTO.getItems().isEmpty()) {
                throw new RuntimeException("Order items is required");
            }

            createOrderDTO.getItems().forEach((ItemOrderDTO item) -> {

                //check product inventory
                var data = productClient.getProduct(item.getProduct_id(), authorizationHeader);
                if (!data.getStatusCode().is2xxSuccessful() || Objects.requireNonNull(data.getBody()).getData().getStockQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Product inventory is not enough");
                }

            });


            //create order
            OrderEntity orderCreateDTO = orderService.createOrder(orderEntity);

            //create order item
            if (orderCreateDTO.getId() != null) {
                createOrderDTO.getItems().forEach((ItemOrderDTO item) -> {
                    try {
                        orderService.createOrderItems(orderCreateDTO, item);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return ResponseEntity.ok(ResponseFormat.build(
                    orderEntity,
                    "Congrats, the data information has been fetched successfully."
            ));

        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(ResponseFormat.build(
                    "",
                    exception.getMessage()
            ));
        }
    }
}
