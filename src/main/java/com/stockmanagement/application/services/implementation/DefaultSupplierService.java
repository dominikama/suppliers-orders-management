package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.SupplierRepository;
import com.stockmanagement.application.mappers.SupplierMapper;
import com.stockmanagement.application.services.SupplierService;
import com.stockmanagement.domain.models.Supplier;
import com.stockmanagement.dtos.SupplierDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DefaultSupplierService implements SupplierService {

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    private final Logger LOG = LoggerFactory.getLogger(DefaultSupplierService.class);

    public DefaultSupplierService(SupplierRepository supplierRepository,
                                  SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public SupplierDto create(SupplierDto supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier saved = supplierRepository.save(supplier);
        LOG.info("Supplier with name={} saved", supplierDTO.getName());
        return supplierMapper.toDto(saved);
    }

    @Override
    public List<SupplierDto> get() {
        List<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier sup : supplierRepository.findAll()) {
            supplierDtos.add(supplierMapper.toDto(sup));
        }
        return supplierDtos;
    }

    @Override
    public SupplierDto getById(Integer id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find supplier with id=%s", id)));
    }

    @Override
    public SupplierDto update(Integer id, SupplierDto updated) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find supplier with id=%s", id)));
        supplier.setAddress(updated.getAddress());
        supplier.setEmail(updated.getEmail());
        supplier.setName(updated.getName());
        supplier.setPhone(updated.getPhone());
        supplierRepository.save(supplier);
        LOG.info("Supplier with name={} updated", updated.getName());
        return supplierMapper.toDto(supplier);
    }

    @Override
    public void delete(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find supplier with id=%s", id)));
        LOG.info("Supplier with name={} deleted", supplier.getName());
        supplierRepository.delete(supplier);
    }
}
