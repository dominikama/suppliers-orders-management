package com.stockmanagement.adapters.controller;

import com.stockmanagement.application.dtos.OrderDTO;

import com.stockmanagement.application.dtos.WarehouseDTO;
import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.application.services.WarehouseService;
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
    public List<OrderDTO> getAllOrders() {
        return orderService.get();
    }

    @GetMapping("/warehouses")
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.create(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.update(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
