package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.dtos.WarehouseDto;
import com.stockmanagement.events.WarehouseEvent;
import com.stockmanagement.events.WarehouseEvent.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseEventProcessorTest {

    @Mock
    private WarehouseService warehouseService;
    @InjectMocks
    private WarehouseEventProcessor warehouseEventProcessor;

    private WarehouseDto warehouseDto;
    private WarehouseEvent warehouseEvent;

    @BeforeEach
    void setUp() {
        warehouseDto = new WarehouseDto();
        warehouseDto.setNumber("WH1");

        warehouseEvent = new WarehouseEvent();
        warehouseEvent.setWarehouseDto(warehouseDto);
    }

    @Test
    void processWarehouseCreatedEvent() {
        warehouseEvent.setEventType(EventType.WAREHOUSE_CREATED);

        warehouseEventProcessor.process(warehouseEvent);

        verify(warehouseService).create(warehouseDto);
    }

    @Test
    void processWarehouseDeletedEvent() {
        warehouseEvent.setEventType(EventType.WAREHOUSE_DELETED);

        warehouseEventProcessor.process(warehouseEvent);

        verify(warehouseService).delete(warehouseDto.getNumber());
    }

}
