package com.stockmanagement.application.services;

import com.stockmanagement.dtos.WarehouseDto;

public interface WarehouseService {
    void create(WarehouseDto warehouseDto);

    void delete(String warehouseCode);
}
