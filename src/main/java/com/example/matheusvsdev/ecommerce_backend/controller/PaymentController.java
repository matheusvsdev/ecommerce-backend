package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.entities.Payment;
import com.example.matheusvsdev.ecommerce_backend.service.StripePaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripePaymentService stripePaymentService;

    @Autowired
    private StripePaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestBody Payment payment) {
        try {
            String chargeId = paymentService.createCharge(payment.getToken(), payment.getAmount());
            return ResponseEntity.ok("Pagamento realizado com sucesso! Charge ID: " + chargeId);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao realizar o pagamento: " + e.getMessage());
        }
    }
}