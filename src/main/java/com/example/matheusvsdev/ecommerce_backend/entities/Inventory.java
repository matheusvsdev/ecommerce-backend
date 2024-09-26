package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private Integer quantity;

    private Integer outputQuantity = 0;

    private LocalDateTime updateTime;

    private boolean available = true;

    public Inventory() {
    }

    public Inventory(Long id, Product product, Integer quantity, Integer outputQuantity, LocalDateTime updateTime) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.outputQuantity = outputQuantity;
        this.updateTime = updateTime;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(Integer outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void updateAvailability() {
        if (this.quantity == 0) {
            this.available = false;
            this.product.setAvailable(false);
        } else {
            this.available = true;
            this.product.setAvailable(true);
        }
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Callback para atualizar o tempo automaticamente antes uma atualização
    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
