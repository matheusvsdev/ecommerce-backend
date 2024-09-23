package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.PaymentMethod;

public class PaymentDTO {

    private PaymentMethod paymentMethod;

    public PaymentDTO() {
    }

    public PaymentDTO(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
