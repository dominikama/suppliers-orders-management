package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.ProductService;
import com.stockmanagement.dtos.ProductDto;
import com.stockmanagement.events.ProductEvent;
import com.stockmanagement.events.ProductEvent.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductEventProcessorTest {

    @Mock
    private ProductService productService;


    @InjectMocks
    private ProductEventProcessor productEventProcessor;

    private ProductDto productDto;
    private ProductEvent productEvent;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setProductCode("P1");
        productDto.setProductName("Product Name");
        productDto.setProductPrice(100.00);

        productEvent = new ProductEvent();
        productEvent.setProductDto(productDto);
    }

    @Test
    void processProductCreatedEvent() {
        productEvent.setEventType(EventType.PRODUCT_CREATED);

        productEventProcessor.process(productEvent);

        verify(productService).create(productDto);
    }

    @Test
    void processProductDeletedEvent() {
        productEvent.setEventType(EventType.PRODUCT_DELETED);

        productEventProcessor.process(productEvent);

        verify(productService).delete(productDto.getProductCode());
    }

    @Test
    void processProductUpdatedEvent() {
        productEvent.setEventType(EventType.PRODUCT_UPDATED);

        productEventProcessor.process(productEvent);

        verify(productService).update(productDto, productEvent.getProductCode());
    }
}
