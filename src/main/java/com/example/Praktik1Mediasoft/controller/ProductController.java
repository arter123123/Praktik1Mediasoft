package com.example.Praktik1Mediasoft.controller;

import com.example.Praktik1Mediasoft.model.Product;
import com.example.Praktik1Mediasoft.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления продуктами.
 * Обрабатывает HTTP-запросы, связанные с продуктами.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /**
     * Возвращает все продукты.
     *
     * @return Список всех продуктов.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    /**
     * Возвращает продукт по его уникальному идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Продукт с указанным идентификатором.
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id) {
        return service.getProductById(id);
    }

    /**
     * Создает новый продукт.
     *
     * @param product Объект продукта для создания.
     * @return Ответ с созданным продуктом.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = service.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Обновляет данные продукта по его идентификатору.
     *
     * @param id Идентификатор продукта, который нужно обновить.
     * @param product Обновленные данные продукта.
     * @return Обновленный продукт.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @Valid @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта, который нужно удалить.
     * @return Ответ с кодом 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
