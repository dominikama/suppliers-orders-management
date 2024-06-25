package com.stockmanagement.application.services;

import com.stockmanagement.dtos.ProductDto;

public interface ProductService {

    void create(ProductDto productDto);

    void update(ProductDto productDto, String productCode);

    void delete(String productCode);
}
