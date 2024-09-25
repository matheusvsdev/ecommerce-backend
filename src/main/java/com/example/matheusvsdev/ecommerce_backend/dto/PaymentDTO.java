package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.PaymentMethod;
import jakarta.validation.constraints.NotBlank;

public class PaymentDTO {

    @NotBlank(message = "Campo não preenchido")
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
