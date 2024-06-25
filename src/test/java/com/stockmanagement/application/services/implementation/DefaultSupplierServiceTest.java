package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.SupplierRepository;
import com.stockmanagement.application.mappers.SupplierMapper;
import com.stockmanagement.domain.models.Supplier;
import com.stockmanagement.dtos.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSupplierServiceTest {
    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private DefaultSupplierService supplierService;

    private Supplier supplier;
    private SupplierDto supplierDto;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("Supplier Name");
        supplier.setAddress("Supplier Address");
        supplier.setEmail("supplier@example.com");
        supplier.setPhone("123456789");

        supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setName("Supplier Name");
        supplierDto.setAddress("Supplier Address");
        supplierDto.setEmail("supplier@example.com");
        supplierDto.setPhone("123456789");
    }

    @Test
    void createSupplierTest() {
        when(supplierMapper.toEntity(any(SupplierDto.class))).thenReturn(supplier);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(supplierDto);

        SupplierDto createdSupplier = supplierService.create(supplierDto);

        assertNotNull(createdSupplier);
        verify(supplierMapper).toEntity(supplierDto);
        verify(supplierRepository).save(supplier);
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void getSuppliersTest() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(supplierDto);

        List<SupplierDto> supplierDtos = supplierService.get();

        assertNotNull(supplierDtos);
        assertEquals(1, supplierDtos.size());
        verify(supplierRepository).findAll();
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void getSupplierByIdTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(supplierDto);

        SupplierDto foundSupplier = supplierService.getById(1);

        assertNotNull(foundSupplier);
        verify(supplierRepository).findById(1);
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void updateSupplierTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(supplierDto);

        SupplierDto updatedSupplier = supplierService.update(1, supplierDto);

        assertNotNull(updatedSupplier);
        verify(supplierRepository).findById(1);
        verify(supplierRepository).save(supplier);
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void deleteSupplierTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.of(supplier));

        supplierService.delete(1);

        verify(supplierRepository).findById(1);
        verify(supplierRepository).delete(supplier);
    }

    @Test
    void getSupplierByIdNotFoundTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> supplierService.getById(1));

        verify(supplierRepository).findById(1);
    }

    @Test
    void updateSupplierNotFoundTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> supplierService.update(1, supplierDto));

        verify(supplierRepository).findById(1);
    }

    @Test
    void deleteSupplierNotFoundTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> supplierService.delete(1));

        verify(supplierRepository).findById(1);
    }
}