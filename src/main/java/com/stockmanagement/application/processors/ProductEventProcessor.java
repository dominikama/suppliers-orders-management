package com.stockmanagement.application.processors;

import com.stockmanagement.application.services.ProductService;
import com.stockmanagement.dtos.ProductDto;
import com.stockmanagement.events.ProductEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductEventProcessor {

    private final ProductService productService;

    private final Logger LOG = LoggerFactory.getLogger(ProductEventProcessor.class);

    public ProductEventProcessor(ProductService productService) {
        this.productService = productService;
    }

    public void process(ProductEvent productEvent) {
        ProductDto productDto = productEvent.getProductDto();
        switch (productEvent.getEventType()) {
            case PRODUCT_CREATED:
                productService.create(productDto);
                break;
            case PRODUCT_DELETED:
                productService.delete(productDto.getProductCode());
                break;
            case PRODUCT_UPDATED:
                productService.update(productDto, productEvent.getProductCode());
                break;
            default:
                LOG.info("Ignoring product event with type={}", productEvent.getEventType());
                break;
        }
    }
}
