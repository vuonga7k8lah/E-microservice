package com.vuongkma.products.services;

import com.vuongkma.orders_service.entities.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private final ProductService productService;

    public KafkaConsumerService(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.update-stock-quantity}", groupId = "${spring.kafka.consumer.group-id}")
    public void UpdateStock(OrderItemEntity orderItemEntity) {
        try {
            log.info("Received Message in group: " + orderItemEntity);
            // Xử lý logic của bạn ở đây
            productService.updateStock(orderItemEntity.getProduct_id(), orderItemEntity.getQuantity());
        } catch (Exception e) {
            log.error("Failed to process orderItemEntity: {}", orderItemEntity, e);
        }

    }
}
