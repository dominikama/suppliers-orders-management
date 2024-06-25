package com.stockmanagement.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmanagement.application.processors.OrderStatusUpdateEventProcessor;
import com.stockmanagement.application.processors.ProductEventProcessor;
import com.stockmanagement.application.processors.WarehouseEventProcessor;
import com.stockmanagement.events.OrderStatusUpdateEvent;
import com.stockmanagement.events.ProductEvent;
import com.stockmanagement.events.WarehouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final WarehouseEventProcessor warehouseEventProcessor;

    private final ProductEventProcessor productEventProcessor;

    private final OrderStatusUpdateEventProcessor ordeStatusUpdateEventProcessor;

    private final ObjectMapper mapper;

    public KafkaConsumer(WarehouseEventProcessor warehouseEventProcessor, ProductEventProcessor productEventProcessor,
                         OrderStatusUpdateEventProcessor ordeStatusUpdateEventProcessor, ObjectMapper mapper) {
        this.warehouseEventProcessor = warehouseEventProcessor;
        this.ordeStatusUpdateEventProcessor = ordeStatusUpdateEventProcessor;
        this.productEventProcessor = productEventProcessor;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "warehouse-events", groupId = "orders-suppliers-group")
    public void listenWarehouses(String event) throws Exception{
        logger.info("Received event: {}", event);
        warehouseEventProcessor.process(mapper.readValue(event, WarehouseEvent.class));
    }

    @KafkaListener(topics = "product-events", groupId = "orders-suppliers-group")
    public void listenProducts(String event) throws Exception {
        logger.info("Received event: {}", event);
        productEventProcessor.process(mapper.readValue(event, ProductEvent.class));
    }

    @KafkaListener(topics = "order-status-updates-events", groupId = "orders-suppliers-group")
    public void listenOrderStatusUpdates(String event) throws Exception {
        logger.info("Received event: {}", event);
        ordeStatusUpdateEventProcessor.process(mapper.readValue(event, OrderStatusUpdateEvent.class));
    }
}
