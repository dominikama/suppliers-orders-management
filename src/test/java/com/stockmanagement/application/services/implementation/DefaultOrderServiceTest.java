package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.messaging.EventPublisher;
import com.stockmanagement.adapters.persistence.OrderRepository;
import com.stockmanagement.adapters.persistence.ProductRepository;
import com.stockmanagement.adapters.persistence.SupplierRepository;
import com.stockmanagement.adapters.persistence.WarehouseRepository;
import com.stockmanagement.application.mappers.OrderMapper;
import com.stockmanagement.domain.models.*;
import com.stockmanagement.dtos.OrderDto;
import com.stockmanagement.dtos.OrderProductsDto;
import com.stockmanagement.events.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultOrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private DefaultOrderService orderService;

    private OrderDto orderDto;
    private Order order;
    private Supplier supplier;
    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setSupplierId(1);
        orderDto.setWarehouseCode("WH1");
        OrderProductsDto orderProductsDto = new OrderProductsDto();
        orderProductsDto.setProductCode("P1");
        orderProductsDto.setQuantity(10);

        product = new Product();
        product.setProductCode("P1");

        List<OrderProductsDto> orderProductsDtoList = Collections.singletonList(orderProductsDto);
        orderDto.setOrderProductsDto(orderProductsDtoList);

        supplier = new Supplier();
        supplier.setId(1);

        warehouse = new Warehouse();
        warehouse.setCode("WH1");

        order = new Order();
        order.setId(1);
        order.setOrderDateTime(LocalDateTime.now());
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        List<OrderProduct> orderProductList = new ArrayList<>();
        order.setOrderProducts(orderProductList);
    }

    @Test
    void createOrderTest() {
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.of(supplier));
        when(warehouseRepository.findByCode(anyString())).thenReturn(Optional.of(warehouse));
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDto);

        OrderDto createdOrder = orderService.create(orderDto);

        assertNotNull(createdOrder);
        verify(supplierRepository).findById(orderDto.getSupplierId());
        verify(warehouseRepository).findByCode(orderDto.getWarehouseCode());
        verify(productRepository).findByProductCode(orderDto.getOrderProductsDto().get(0).getProductCode());
        verify(orderRepository).save(any(Order.class));
        verify(eventPublisher).publishEvent(any(OrderEvent.class));
    }

    @Test
    void getOrderByIdTest() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDto);

        OrderDto foundOrder = orderService.getById(1);

        assertNotNull(foundOrder);
        verify(orderRepository).findById(1);
        verify(orderMapper).toDto(order);
    }

    @Test
    void updateOrderTest() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(supplierRepository.findById(anyInt())).thenReturn(Optional.of(supplier));
        when(warehouseRepository.findByCode(anyString())).thenReturn(Optional.of(warehouse));
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDto);

        OrderDto updatedOrder = orderService.update(1, orderDto);

        assertNotNull(updatedOrder);
        verify(orderRepository).findById(1);
        verify(supplierRepository).findById(orderDto.getSupplierId());
        verify(warehouseRepository).findByCode(orderDto.getWarehouseCode());
        verify(productRepository).findByProductCode(orderDto.getOrderProductsDto().get(0).getProductCode());
        verify(orderRepository).save(any(Order.class));
        verify(eventPublisher).publishEvent(any(OrderEvent.class));
    }

    @Test
    void deleteOrderTest() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDto);

        orderService.delete(1);

        verify(orderRepository).findById(1);
        verify(orderRepository).delete(order);
        verify(eventPublisher).publishEvent(any(OrderEvent.class));
    }

    @Test
    void updateStatusTest() {
        when(orderRepository.findByNumber(anyString())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDto);

        orderService.updateStatus("NEW_STATUS", "ORDER123");

        verify(orderRepository).findByNumber("ORDER123");
        verify(orderRepository).save(any(Order.class));
        verify(eventPublisher).publishEvent(any(OrderEvent.class));
    }
}