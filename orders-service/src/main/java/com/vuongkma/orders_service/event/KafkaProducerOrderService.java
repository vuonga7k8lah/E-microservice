package com.vuongkma.orders_service.event;


import com.vuongkma.orders_service.entities.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerOrderService {
    @Autowired
    private  KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;



    public void sendDataUpdateStock(Object data) {
        log.error("Send data to kafka: " + data);
        kafkaTemplate.send(topicName, data);
    }
}
