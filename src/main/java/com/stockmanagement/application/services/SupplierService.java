package com.stockmanagement.application.services;

import com.stockmanagement.dtos.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto create(SupplierDto supplierDTO);
    List<SupplierDto> get();
    SupplierDto getById(Integer id);
    SupplierDto update(Integer id, SupplierDto updated);
    void delete(Integer id);
}
