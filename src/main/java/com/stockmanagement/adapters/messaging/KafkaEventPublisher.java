package com.stockmanagement.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmanagement.events.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper mapper;

    private final Logger LOG = LoggerFactory.getLogger(KafkaEventPublisher.class);

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Autowired
    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void publishEvent(OrderEvent event) {
        try {
            kafkaTemplate.send(kafkaTopic, event.getOrderNumber(), mapper.writeValueAsString(event));
        } catch (Exception e) {
            LOG.error("Error during publishing event for order={} with type={}", event.getOrderNumber(), event.getEventType(), e);
        }
    }
}
