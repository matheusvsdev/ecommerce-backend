package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_inventory_movement")
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "movement_type")
    private String movementType;  // "ENTRADA" ou "SAIDA"

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "moment")
    private LocalDateTime moment;

    @Column(name = "remaining_stock")
    private int remainingStock;

    public InventoryMovement() {}

    public InventoryMovement(Product product, String movementType, int quantity, int remainingStock) {
        this.product = product;
        this.movementType = movementType;
        this.quantity = quantity;
        this.remainingStock = remainingStock;
        this.moment = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public int getRemainingStock() {
        return remainingStock;
    }

    public void setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
    }
}
