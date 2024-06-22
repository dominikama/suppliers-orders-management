package com.stockmanagement.application.services;

import com.stockmanagement.application.dtos.WarehouseDTO;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDTO> get();
}
