package com.vuongkma.media.services;

import com.vuongkma.media.dto.ProductMediaProducerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class MediaKafkaProducerService {


    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, ProductMediaProducerDTO> kafkaTemplate;

    public void sendMessage(ProductMediaProducerDTO message) {
        this.kafkaTemplate.send(topicName, message);
        System.out.println("Sent message: " + message);
    }
}
