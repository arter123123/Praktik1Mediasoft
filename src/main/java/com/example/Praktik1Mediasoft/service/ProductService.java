package com.example.Praktik1Mediasoft.service;


import com.example.Praktik1Mediasoft.model.Product;
import com.example.Praktik1Mediasoft.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с продуктами.
 * Содержит логику получения, создания, обновления и удаления продуктов.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    /**
     * Получить все продукты.
     * @return Список всех продуктов
     */
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    /**
     * Получить продукт по ID.
     * @param id Идентификатор продукта
     * @return Продукт с указанным ID
     */
    public Product getProductById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));
    }

    /**
     * Создать новый продукт.
     * @param product Продукт для создания
     * @return Созданный продукт
     */
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    /**
     * Обновить существующий продукт.
     * @param id Идентификатор продукта
     * @param updatedProduct Продукт с новыми данными
     * @return Обновленный продукт
     */
    public Product updateProduct(UUID id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return repository.save(existingProduct);
    }

    /**
     * Удалить продукт по ID.
     * @param id Идентификатор продукта
     */
    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }
}
