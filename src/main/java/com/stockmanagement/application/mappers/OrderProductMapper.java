package com.stockmanagement.application.mappers;

import com.stockmanagement.domain.models.OrderProduct;
import com.stockmanagement.domain.models.Product;
import com.stockmanagement.dtos.OrderProductsDto;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {

    public OrderProductsDto toDto(OrderProduct orderProduct) {
        Product product = orderProduct.getProduct();
        OrderProductsDto productOrderDto = new OrderProductsDto();
        productOrderDto.setProductId(product.getId());
        productOrderDto.setProductCode(product.getProductCode());
        productOrderDto.setQuantity(orderProduct.getQuantity());
        return productOrderDto;
    }
}
