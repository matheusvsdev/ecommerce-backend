package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.OrderRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (order.getPayment().getStatus() == PaymentStatus.CONFIRMANDO_PAGAMENTO) {

            order.getPayment().setStatus(PaymentStatus.PAGAMENTO_APROVADO);
            order.getPayment().setPaymentConfirmation(LocalDateTime.now());

            orderRepository.save(order);

            emailService.paymentConfirmation(order);
        } else {
            throw new RuntimeException("Pagamento não pode ser processado. Status atual: " + order.getPayment().getStatus());
        }
    }
}
