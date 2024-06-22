package com.stockmanagement.adapters.controller;

import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.dtos.OrderDto;
import com.stockmanagement.dtos.WarehouseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final WarehouseService warehouseService;
    public OrderController(OrderService orderService, WarehouseService warehouseService) {
        this.orderService = orderService;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.get();
    }

    @GetMapping("/warehouses")
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDTO) {
        return orderService.create(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDTO) {
        return ResponseEntity.ok(orderService.update(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
