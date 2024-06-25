package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.WarehouseRepository;
import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.domain.models.Warehouse;
import com.stockmanagement.dtos.WarehouseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DefaultWarehouseService implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final Logger LOG = LoggerFactory.getLogger(DefaultWarehouseService.class);

    public DefaultWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void create(WarehouseDto warehouseDto) {
        if (warehouseRepository.findByCode(warehouseDto.getNumber()).isEmpty()) {
            Warehouse warehouse = new Warehouse();
            warehouse.setCode(warehouseDto.getNumber());
            warehouseRepository.save(warehouse);
            LOG.info("Warehouse with code={} created", warehouseDto.getNumber());
        } else {
            LOG.warn("Warehouse with code={} not created, it already exists", warehouseDto.getNumber());
        }
    }

    @Override
    public void delete(String warehouseCode) {
        Warehouse warehouse = warehouseRepository.findByCode(warehouseCode)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find warehouse with code=%s", warehouseCode)));
        warehouseRepository.delete(warehouse);
        LOG.info("Warehouse with code={} deleted", warehouseCode);

    }
}
