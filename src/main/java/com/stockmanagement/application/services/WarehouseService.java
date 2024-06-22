package com.stockmanagement.application.services;

import com.stockmanagement.dtos.WarehouseDto;
import com.stockmanagement.events.WarehouseEvent;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDto> get();
    void processWarehouseEvent(WarehouseEvent warehouseEvent);
}
