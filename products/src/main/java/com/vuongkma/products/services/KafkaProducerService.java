package com.vuongkma.products.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Slf4j
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void sendMessage(@RequestBody List<String> kafkaMessageList) {
        kafkaMessageList.forEach(kafkaMessage -> kafkaTemplate.send(topic, kafkaMessage));
    }
}
