package com.example.Praktik1Mediasoft.controller;

import com.example.Praktik1Mediasoft.model.Product;
import com.example.Praktik1Mediasoft.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для контроллера {@link ProductController}.
 * Используется для проверки API операций с продуктами.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Тестирование получения продукта по идентификатору.
     * Проверяет, что API корректно возвращает продукт по его ID.
     *
     * @throws Exception если запрос не удался.
     */
    @Test
    public void testGetProductById() throws Exception {
        // Создаем UUID для теста
        UUID productId = UUID.randomUUID();

        // Создаем продукт с этим UUID
        Product product = new Product();
        product.setId(productId);
        product.setSku("ABC123");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setCategory("Electronics");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantity(10);

        // Мокируем сервис
        when(service.getProductById(productId)).thenReturn(product);

        // Выполнение теста
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.sku").value("ABC123"))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    /**
     * Тестирование удаления продукта по идентификатору.
     * Проверяет, что продукт успешно удаляется.
     *
     * @throws Exception если запрос не удался.
     */
    @Test
    public void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        // Выполнение запроса на удаление
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent()); // Ожидаем статус 204 (No Content)
    }

    /**
     * Тестирование создания нового продукта.
     * Проверяет, что продукт успешно создается через API.
     *
     * @throws Exception если запрос не удался.
     */
    @Test
    void testCreateProduct() throws Exception {
        Product product = Product.builder()
                .name("Test Product")
                .sku("TEST123")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .category("Electronics")  // Добавьте обязательное поле category
                .build();

        when(service.createProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))  // Используем objectMapper для сериализации
                .andExpect(status().isCreated())  // Ожидаем статус 201 Created
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.sku").value("TEST123"));
    }

    /**
     * Тестирование ошибки валидации при создании продукта с некорректными данными.
     * Проверяет, что API корректно обрабатывает ошибку валидации (например, некорректная цена).
     *
     * @throws Exception если запрос не удался.
     */
    @Test
    void testCreateProduct_withValidationError() throws Exception {
        Product invalidProduct = Product.builder()
                .sku("TEST123")
                .price(BigDecimal.valueOf(-100))  // Некорректная цена
                .quantity(10)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))  // Сериализуем объект с ошибкой валидации
                .andExpect(status().isBadRequest());  // Ошибка валидации - 400
    }

    /**
     * Тестирование обновления продукта.
     * Проверяет, что продукт корректно обновляется через API.
     *
     * @throws Exception если запрос не удался.
     */
    @Test
    public void testUpdateProduct() throws Exception {
        // UUID продукта для обновления
        UUID productId = UUID.randomUUID();

        // Исходный продукт, который будет обновляться
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setSku("ABC123");
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");
        existingProduct.setCategory("Electronics");
        existingProduct.setPrice(BigDecimal.valueOf(100.00));
        existingProduct.setQuantity(10);

        // Обновленные данные продукта
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setSku("ABC123");
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setCategory("Electronics");
        updatedProduct.setPrice(BigDecimal.valueOf(120.00));
        updatedProduct.setQuantity(15);

        // Мокируем сервис
        when(service.updateProduct(productId, updatedProduct)).thenReturn(updatedProduct);

        // Выполнение теста
        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))  // Сериализуем обновленный продукт
                .andExpect(status().isOk()) // Проверяем, что статус - OK (200)
                .andExpect(jsonPath("$.id").value(productId.toString())) // Проверяем id
                .andExpect(jsonPath("$.sku").value("ABC123")) // Проверяем SKU
                .andExpect(jsonPath("$.name").value("Updated Product")) // Проверяем обновленное имя
                .andExpect(jsonPath("$.description").value("Updated Description")) // Проверяем обновленное описание
                .andExpect(jsonPath("$.price").value(120.00)) // Проверяем обновленную цену
                .andExpect(jsonPath("$.quantity").value(15)); // Проверяем обновленное количество
    }
}
