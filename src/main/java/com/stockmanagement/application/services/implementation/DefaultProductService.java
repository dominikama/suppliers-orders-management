package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.ProductRepository;
import com.stockmanagement.application.services.ProductService;
import com.stockmanagement.domain.models.Product;
import com.stockmanagement.dtos.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    private final Logger LOG = LoggerFactory.getLogger(DefaultProductService.class);

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void create(ProductDto productDto) {
        if (productRepository.findByProductCode(productDto.getProductCode()).isEmpty()) {
            Product product = new Product();
            product.setProductCode(productDto.getProductCode());
            product.setUnitPrice(BigDecimal.valueOf(productDto.getProductPrice()));
            product.setName(productDto.getProductName());
            productRepository.save(product);
            LOG.info("Product with code={} created", productDto.getProductCode());
        } else {
            LOG.warn("Ignoring creation for productCode={}, it already exists", productDto.getProductCode());
        }
    }

    @Override
    public void update(ProductDto productDto, String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find product with code=%s", productCode)));
        product.setProductCode(productDto.getProductCode());
        product.setUnitPrice(BigDecimal.valueOf(productDto.getProductPrice()));
        product.setName(productDto.getProductName());
        productRepository.save(product);
        LOG.info("Product with code={} updated", productCode);
    }

    @Override
    public void delete(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find product with code=%s", productCode)));
        productRepository.delete(product);
        LOG.info("Product with code={} deleted", productCode);
    }
}
