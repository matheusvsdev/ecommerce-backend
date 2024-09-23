package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (order.getPayment().getPaymentStatus() == PaymentStatus.CONFIRMANDO_PAGAMENTO) {

            order.getPayment().setPaymentStatus(PaymentStatus.PAGAMENTO_APROVADO);
            order.getPayment().setPaymentConfirmationDate(LocalDateTime.now());

            orderRepository.save(order);

            emailService.paymentConfirmation(order);
        } else {
            throw new RuntimeException("Pagamento não pode ser processado. Status atual: " + order.getPayment().getPaymentStatus());
        }
    }
}
