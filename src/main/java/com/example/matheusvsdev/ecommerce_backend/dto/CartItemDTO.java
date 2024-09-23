package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.CartItem;

public class CartItemDTO {

    private Long productId;

    private String name;

    private Double price;

    private Integer quantity;

    private String img;

    public CartItemDTO(Long productId, String name, Double price, Integer quantity, String img) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
    }

    public CartItemDTO(CartItem cartItem) {
        productId = cartItem.getProduct().getId();
        name = cartItem.getProduct().getName();
        price = cartItem.getPrice();
        quantity = cartItem.getQuantity();
        img = cartItem.getProduct().getImg();
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
