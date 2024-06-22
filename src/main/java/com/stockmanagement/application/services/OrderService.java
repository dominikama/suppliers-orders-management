package com.stockmanagement.application.services;

import com.stockmanagement.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto orderDTO);
    List<OrderDto> get();
    OrderDto getById(Long id);
    OrderDto update(Long id, OrderDto updated);
    void delete(Long id);
}
