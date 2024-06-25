package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.dtos.WarehouseDto;
import com.stockmanagement.events.WarehouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEventProcessor {

    private final WarehouseService warehouseService;

    private final Logger LOG = LoggerFactory.getLogger(WarehouseEventProcessor.class);

    public WarehouseEventProcessor(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public void process(WarehouseEvent warehouseEvent) {
        WarehouseDto warehouseDto = warehouseEvent.getWarehouseDto();
        switch (warehouseEvent.getEventType()) {
            case WAREHOUSE_CREATED:
                warehouseService.create(warehouseDto);
                break;
            case WAREHOUSE_DELETED:
                warehouseService.delete(warehouseDto.getNumber());
                break;
            default:
                LOG.info("Ignoring warehouse event with type={}", warehouseEvent.getEventType());
        }
    }
}
