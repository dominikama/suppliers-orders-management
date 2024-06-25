package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.WarehouseRepository;
import com.stockmanagement.domain.models.Warehouse;
import com.stockmanagement.dtos.WarehouseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultWarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private DefaultWarehouseService warehouseService;

    private Warehouse warehouse;
    private WarehouseDto warehouseDto;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        warehouse.setCode("WH1");

        warehouseDto = new WarehouseDto();
        warehouseDto.setNumber("WH1");
    }

    @Test
    void createWarehouseTest() {
        // Mock the save method to return the warehouse when saved
        when(warehouseRepository.save(any(Warehouse.class))).thenAnswer(invocation -> invocation.getArgument(0));

        warehouseService.create(warehouseDto);

        // Capture the argument passed to save method
        ArgumentCaptor<Warehouse> warehouseCaptor = ArgumentCaptor.forClass(Warehouse.class);
        verify(warehouseRepository).save(warehouseCaptor.capture());

        Warehouse capturedWarehouse = warehouseCaptor.getValue();
        assertEquals(warehouseDto.getNumber(), capturedWarehouse.getCode());
    }

    @Test
    void deleteWarehouseTest() {
        when(warehouseRepository.findByCode(anyString())).thenReturn(Optional.of(warehouse));

        warehouseService.delete("WH1");

        verify(warehouseRepository).findByCode("WH1");
        verify(warehouseRepository).delete(warehouse);
    }

    @Test
    void deleteWarehouseNotFoundTest() {
        when(warehouseRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> warehouseService.delete("WH1"));

        verify(warehouseRepository).findByCode("WH1");
    }
}
