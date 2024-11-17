package com.example.Praktik1Mediasoft.service;

import com.example.Praktik1Mediasoft.model.Product;
import com.example.Praktik1Mediasoft.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для сервиса {@link ProductService}.
 * Используется для проверки логики обработки продуктов в бизнес-слое.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    /**
     * Тестирование создания нового продукта через сервис.
     * Проверяет, что сервис корректно создает продукт и взаимодействует с репозиторием.
     */
    @Test
    void testCreateProduct() {
        // Подготовка данных
        Product product = new Product();
        product.setName("Test Product");
        product.setCategory("Category");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);

        // Мокируем поведение репозитория
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Тестируем метод
        Product createdProduct = productService.createProduct(product);

        // Проверка результатов
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals("Category", createdProduct.getCategory());
        assertEquals(new BigDecimal("100.00"), createdProduct.getPrice()); // Используем BigDecimal
        assertEquals(10, createdProduct.getQuantity());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    /**
     * Тестирование получения продукта по идентификатору через сервис.
     * Проверяет, что сервис корректно находит продукт по ID.
     */
    @Test
    void testGetProductById() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setName("Test Product");
        product.setCategory("Category");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getProductById(productId);

        assertNotNull(retrievedProduct);
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals("Category", retrievedProduct.getCategory());
        assertEquals(new BigDecimal("100.00"), retrievedProduct.getPrice()); // Используем BigDecimal
        assertEquals(10, retrievedProduct.getQuantity());

        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Тестирование обновления продукта через сервис.
     * Проверяет, что сервис корректно обновляет данные продукта.
     */
    @Test
    void testUpdateProduct() {
        UUID productId = UUID.randomUUID();
        Product existingProduct = new Product();
        existingProduct.setName("Old Product");
        existingProduct.setCategory("Old Category");
        existingProduct.setPrice(new BigDecimal("50.00"));
        existingProduct.setQuantity(5);

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setCategory("Updated Category");
        updatedProduct.setPrice(new BigDecimal("120.00"));
        updatedProduct.setQuantity(15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Category", result.getCategory());
        assertEquals(new BigDecimal("120.00"), result.getPrice()); // Используем BigDecimal
        assertEquals(15, result.getQuantity());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    /**
     * Тестирование удаления продукта через сервис.
     * Проверяет, что сервис корректно удаляет продукт по ID.
     */
    @Test
    void testDeleteProduct() {
        UUID productId = UUID.randomUUID();

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}