package com.stockmanagement.application.services;

import com.stockmanagement.dtos.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto create(SupplierDto supplierDTO);
    List<SupplierDto> get();
    SupplierDto getById(Long id);
    SupplierDto update(Long id, SupplierDto updated);
    void delete(Long id);
}
