package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.persistence.ProductRepository;
import com.stockmanagement.domain.models.Product;
import com.stockmanagement.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DefaultProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductCode("P1");
        product.setUnitPrice(BigDecimal.valueOf(100.00));
        product.setName("Product Name");

        productDto = new ProductDto();
        productDto.setProductCode("P1");
        productDto.setProductPrice(100.00);
        productDto.setProductName("Product Name");
    }

    @Test
    void createProductTest() {
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        productService.create(productDto);

        ArgumentCaptor<Product> productCaptor = forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(productDto.getProductCode(), capturedProduct.getProductCode());
        assertEquals(BigDecimal.valueOf(productDto.getProductPrice()), capturedProduct.getUnitPrice());
        assertEquals(productDto.getProductName(), capturedProduct.getName());
    }

    @Test
    void updateProductTest() {
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.of(product));

        productService.update(productDto, "P1");

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).findByProductCode("P1");
        verify(productRepository).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(productDto.getProductCode(), capturedProduct.getProductCode());
        assertEquals(BigDecimal.valueOf(productDto.getProductPrice()), capturedProduct.getUnitPrice());
        assertEquals(productDto.getProductName(), capturedProduct.getName());
    }


    @Test
    void deleteProductTest() {
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.of(product));

        productService.delete("P1");

        verify(productRepository).findByProductCode("P1");
        verify(productRepository).delete(product);
    }

    @Test
    void updateProductNotFoundTest() {
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.update(productDto, "P1"));

        verify(productRepository).findByProductCode("P1");
    }

    @Test
    void deleteProductNotFoundTest() {
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.delete("P1"));

        verify(productRepository).findByProductCode("P1");
    }
}
