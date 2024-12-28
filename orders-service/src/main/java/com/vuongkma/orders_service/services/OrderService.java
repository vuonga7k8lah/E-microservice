package com.vuongkma.orders_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongkma.orders_service.dto.ItemOrderDTO;
import com.vuongkma.orders_service.entities.OrderEntity;
import com.vuongkma.orders_service.entities.OrderItemEntity;
import com.vuongkma.orders_service.event.KafkaProducerOrderService;
import com.vuongkma.orders_service.repositories.OrderItemRepository;
import com.vuongkma.orders_service.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ObjectMapper objectMapper;

    private final KafkaProducerOrderService kafkaProducerOrderService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ObjectMapper objectMapper, KafkaProducerOrderService kafkaProducerOrderService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.objectMapper = objectMapper;
        this.kafkaProducerOrderService = kafkaProducerOrderService;
    }
    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }
    @Transactional
    public OrderItemEntity createOrderItems(OrderEntity orderEntity, ItemOrderDTO item) throws JsonProcessingException {
        OrderItemEntity orderItemEntity = objectMapper.convertValue(item, OrderItemEntity.class);
        orderItemEntity.setTotal(item.getPrice() * item.getQuantity());
        orderItemEntity.setOrder(orderEntity);
        OrderItemEntity savedItem = orderItemRepository.save(orderItemEntity);
        if (savedItem.getId() != null) {
            //send kafka message update product inventory
            kafkaProducerOrderService.sendDataUpdateStock(savedItem);
            return savedItem;
        }
        throw new RuntimeException("Order item not created");
    }
}
