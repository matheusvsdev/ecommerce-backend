package com.example.matheusvsdev.ecommerce_backend.projection;

public interface ProductProjection {

    Long getId();
    String getName();
    String getDescription();
    String getImg();
    Integer getQuantity();
    Double getPrice();
}
