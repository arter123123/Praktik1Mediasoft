package com.example.Praktik1Mediasoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность продукта, используемая для хранения информации о товаре в базе данных.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "SKU cannot be null")
    @Size(min = 3, max = 20, message = "SKU must be between 3 and 20 characters")
    private String sku;

    @Column(nullable = false)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String description;

    @Column(nullable = false)
    @NotNull(message = "Category cannot be null")
    private String category;

    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be less than zero")
    private Integer quantity;

    private LocalDateTime lastQuantityUpdate;

    private LocalDateTime createdDate;


    /**
     * Метод, вызываемый перед сохранением сущности.
     * Устанавливает дату создания.
     */
    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    /**
     * Метод, вызываемый перед обновлением сущности.
     * Обновляет дату последнего изменения количества.
     */
    @PreUpdate
    public void preUpdate() {
        lastQuantityUpdate = LocalDateTime.now();
    }
}