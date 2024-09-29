package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Payment;
import com.example.matheusvsdev.ecommerce_backend.entities.PaymentMethod;
import com.example.matheusvsdev.ecommerce_backend.entities.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;

    @NotBlank(message = "Campo não preenchido")
    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private Long orderId;

    private LocalDateTime paymentDate;

    private String transactionId;

    private String token;

    private Integer amount;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, PaymentMethod paymentMethod, PaymentStatus status, Long orderId, LocalDateTime paymentDate, String transactionId, String token, Integer amount) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
        this.token = token;
        this.amount = amount;
    }

    public PaymentDTO(Payment payment) {
        id = payment.getId();
        paymentMethod = payment.getPaymentMethod();
        status = payment.getStatus();
        orderId = payment.getOrder().getId();
        paymentDate = payment.getPaymentDate();
        transactionId = payment.getTransactionId();
        token = payment.getToken();
        amount = payment.getAmount();
    }

    public Long getId() {
        return id;
    }

    public @NotBlank(message = "Campo não preenchido") PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getToken() {
        return token;
    }

    public Integer getAmount() {
        return amount;
    }
}
