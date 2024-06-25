package com.stockmanagement.adapters.controller;

import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.application.services.WarehouseService;
import com.stockmanagement.dtos.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.get();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDTO) {
        return orderService.create(orderDTO);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Integer orderId, @RequestBody OrderDto orderDTO) {
        return ResponseEntity.ok(orderService.update(orderId, orderDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }
}
