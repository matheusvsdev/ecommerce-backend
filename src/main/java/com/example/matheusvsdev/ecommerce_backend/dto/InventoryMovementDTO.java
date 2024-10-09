package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.InventoryMovement;
import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class InventoryMovementDTO {

    private Long id;

    private Long productId;

    private String movementType;  // "ENTRADA" ou "SAIDA"

    private Integer quantity;

    private LocalDateTime moment;

    private Integer remainingStock;

    public InventoryMovementDTO() {}

    public InventoryMovementDTO(Long product, String movementType, Integer quantity, Integer remainingStock) {
        this.productId = product;
        this.movementType = movementType;
        this.quantity = quantity;
        this.remainingStock = remainingStock;
        this.moment = LocalDateTime.now();
    }

    public InventoryMovementDTO(InventoryMovement movement) {
        id = movement.getId();
        productId = movement.getProduct().getId();
        movementType = movement.getMovementType().toUpperCase();
        quantity = movement.getQuantity();
        moment = movement.getMoment();
        remainingStock = movement.getRemainingStock();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getMovementType() {
        return movementType;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public Integer getRemainingStock() {
        return remainingStock;
    }
}
