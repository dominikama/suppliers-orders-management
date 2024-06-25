package com.stockmanagement.adapters.controller;

import com.stockmanagement.application.services.SupplierService;
import com.stockmanagement.dtos.SupplierDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierDto> getAllSuppliers() {
        return supplierService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PostMapping
    public SupplierDto createSupplier(@RequestBody SupplierDto supplierDTO) {
        return supplierService.create(supplierDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDto supplierDTO) {
        return ResponseEntity.ok(supplierService.update(id, supplierDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
