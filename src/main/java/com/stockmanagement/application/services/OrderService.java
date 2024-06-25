package com.stockmanagement.application.services;

import com.stockmanagement.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto orderDTO);
    List<OrderDto> get();
    OrderDto getById(Integer id);
    OrderDto update(Integer id, OrderDto updated);
    void delete(Integer id);

    void updateStatus(String status, String orderNumber);
}
