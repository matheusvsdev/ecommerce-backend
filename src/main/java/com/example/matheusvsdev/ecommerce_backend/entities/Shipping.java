package com.example.matheusvsdev.ecommerce_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ShippingStatus status;

    private LocalDateTime orderUpdate, deliveryTime;

    private Double freightCost;

    @OneToOne(mappedBy = "delivery")
    @JsonIgnore
    private Order order;

    public Shipping() {
    }

    public Shipping(Long id,
                    ShippingStatus status,
                    LocalDateTime orderUpdate,
                    LocalDateTime deliveryTime, Double freightCost) {
        this.id = id;
        this.status = status;
        this.orderUpdate = orderUpdate;
        this.deliveryTime = deliveryTime;
        this.freightCost = freightCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
            this.status = status;
    }

    public LocalDateTime getOrderUpdate() {
        return orderUpdate;
    }

    public void setOrderUpdate(LocalDateTime orderUpdate) {
        this.orderUpdate = orderUpdate;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(Double freightCost) {
        this.freightCost = freightCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipping delivery = (Shipping) o;
        return Objects.equals(id, delivery.id) && status == delivery.status && Objects.equals(orderUpdate, delivery.orderUpdate) && Objects.equals(deliveryTime, delivery.deliveryTime) && Objects.equals(freightCost, delivery.freightCost) && Objects.equals(order, delivery.order);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
