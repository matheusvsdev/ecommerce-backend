package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Inventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class InventoryDTO {

    private Long id;

    private Long productId;

    private Integer quantity;

    private Integer outputQuantity;

    private LocalDateTime updateTime;

    public InventoryDTO() {
    }

    public InventoryDTO(Long id, Long productId, Integer quantity, Integer outputQuantity, LocalDateTime updateTime) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.outputQuantity = outputQuantity;
        this.updateTime = updateTime;
    }

    public InventoryDTO(Inventory entity) {
        id = entity.getId();
        productId = entity.getProduct().getId();
        quantity = entity.getQuantity();
        outputQuantity = entity.getOutputQuantity();
        updateTime = entity.getUpdateTime();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOutputQuantity() {
        return outputQuantity;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
