package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.events.OrderStatusUpdateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderStatusUpdateEventProcessorTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderStatusUpdateEventProcessor orderStatusUpdateEventProcessor;

    private OrderStatusUpdateEvent orderStatusUpdateEvent;

    @BeforeEach
    void setUp() {
        orderStatusUpdateEvent = new OrderStatusUpdateEvent();
        orderStatusUpdateEvent.setStatus("NEW_STATUS");
        orderStatusUpdateEvent.setOrderNumber("ORDER123");
    }

    @Test
    void processOrderStatusUpdateEvent() {
        orderStatusUpdateEventProcessor.process(orderStatusUpdateEvent);

        verify(orderService).updateStatus(orderStatusUpdateEvent.getStatus(),
                orderStatusUpdateEvent.getOrderNumber());
    }
}
