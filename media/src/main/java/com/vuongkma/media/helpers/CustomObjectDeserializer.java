package com.vuongkma.media.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomObjectDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> targetClass;

    public CustomObjectDeserializer(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Optional configuration if needed
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, targetClass); // Deserialize bytes to Object
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing byte[] to object", e);
        }
    }

    @Override
    public void close() {
        // Clean up resources if needed
    }
}
