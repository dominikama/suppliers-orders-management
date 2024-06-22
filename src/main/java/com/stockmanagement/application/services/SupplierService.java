package com.stockmanagement.application.services;

import com.stockmanagement.application.dtos.SupplierDTO;

import java.util.List;

public interface SupplierService {

    SupplierDTO create(SupplierDTO supplierDTO);
    List<SupplierDTO> get();
    SupplierDTO getById(Long id);
    SupplierDTO update(Long id, SupplierDTO updated);
    void delete(Long id);
}
