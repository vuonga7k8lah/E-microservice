package com.vuongkma.media.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomObjectSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Optional configuration if needed
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data); // Serialize Object to JSON as bytes
        } catch (Exception e) {
            throw new RuntimeException("Error serializing object to byte[]", e);
        }
    }

    @Override
    public void close() {
        // Clean up resources if needed
    }
}

