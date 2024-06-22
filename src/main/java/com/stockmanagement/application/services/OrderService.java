package com.stockmanagement.application.services;

import com.stockmanagement.application.dtos.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    List<OrderDTO> get();
    OrderDTO getById(Long id);
    OrderDTO update(Long id, OrderDTO updated);
    void delete(Long id);
}
