package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.OrderItem;

public class OrderItemsDTO {

    private Long productId;

    private String name;

    private Double price;

    private Integer quantity;

    private String img;

    public OrderItemsDTO(Long productId, String name, Double price, Integer quantity, String img) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
    }

    public OrderItemsDTO(OrderItem entity) {
        productId = entity.getProduct().getId();
        name = entity.getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        img = entity.getProduct().getImg();
    }

    public OrderItemsDTO(Long id, Integer quantity, Double price) {
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getImg() {
        return img;
    }
}
