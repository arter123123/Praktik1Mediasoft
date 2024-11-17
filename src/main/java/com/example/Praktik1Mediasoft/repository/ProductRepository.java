package com.example.Praktik1Mediasoft.repository;


import com.example.Praktik1Mediasoft.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Product}.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
