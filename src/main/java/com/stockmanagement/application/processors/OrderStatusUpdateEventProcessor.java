package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.events.OrderStatusUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateEventProcessor {
    private final OrderService orderService;

    public OrderStatusUpdateEventProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    public void process(OrderStatusUpdateEvent orderStatusUpdateEvent) {
        orderService.updateStatus(orderStatusUpdateEvent.getStatus(),
                orderStatusUpdateEvent.getOrderNumber());
    }
}
