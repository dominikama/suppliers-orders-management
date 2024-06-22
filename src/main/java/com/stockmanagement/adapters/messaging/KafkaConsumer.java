package com.stockmanagement.adapters.messaging;

import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.events.WarehouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final WarehouseService warehouseService;

    public KafkaConsumer(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @KafkaListener(topics = "warehouse-events", groupId = "warehouse-group")
    public void listen(WarehouseEvent event) {
        logger.info("Received event: {}", event);
        warehouseService.processWarehouseEvent(event);
    }
}
