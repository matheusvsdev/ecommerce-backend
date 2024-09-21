package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.PaymentMethod;
import com.example.matheusvsdev.ecommerce_backend.entities.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentDTO {

    private PaymentMethod paymentMethod;

    public PaymentDTO() {
    }

    public PaymentDTO(PaymentMethod paymentMethod, PaymentStatus paymentStatus, LocalDateTime paymentDueDate) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
