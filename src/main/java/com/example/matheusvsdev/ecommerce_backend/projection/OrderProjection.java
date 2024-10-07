package com.example.matheusvsdev.ecommerce_backend.projection;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderItemDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.PaymentResponseDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.OrderItem;
import com.example.matheusvsdev.ecommerce_backend.entities.State;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProjection {
    Long getOrderId();
    LocalDateTime getOrderMoment();
    Integer getOrderStatus();
    Double getSubTotal();
    Double getFreightCost();
    Double getTotal();
    String getShippingStatus();
    LocalDateTime getDeliveryTime();
    List<OrderItem> getItems();
    Integer getState();
    String getCity();
    Integer getPaymentMethod();
    Integer getPaymentStatus();
    String getTransactionId();
    String getUserName();
    Long getProductId();
    String getProductName();
    Integer getProductQuantity();
    Double getProductPrice();
    String getProductImg();
}
