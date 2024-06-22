package com.stockmanagement.adapters.messaging;


import com.stockmanagement.events.OrderEvent;

public interface EventPublisher {
    void publishEvent(OrderEvent event);
}
