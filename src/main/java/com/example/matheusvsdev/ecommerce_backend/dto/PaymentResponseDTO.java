package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Payment;
import com.example.matheusvsdev.ecommerce_backend.entities.PaymentStatus;
import com.example.matheusvsdev.ecommerce_backend.entities.PaymentMethod;

import java.time.LocalDateTime;

public class PaymentResponseDTO {

    private Long id;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private Long orderId;
    private LocalDateTime paymentDate;
    private String transactionId;
    private Integer amount;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(Long id, PaymentMethod paymentMethod, PaymentStatus status, Long orderId, LocalDateTime paymentDate, String transactionId, Integer amount) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public PaymentResponseDTO(Payment payment) {
        id = payment.getId();
        paymentMethod = payment.getPaymentMethod();
        status = payment.getStatus();
        orderId = payment.getOrder().getId();
        paymentDate = payment.getPaymentDate();
        transactionId = payment.getTransactionId();
        amount = payment.getAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
