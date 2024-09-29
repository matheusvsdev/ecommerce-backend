package com.example.matheusvsdev.ecommerce_backend.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

    @Value("${stripe.api.secret-key}")
    private String secretKey;

    public String createCharge(String token, int amount) throws StripeException {
        Stripe.apiKey = secretKey;

        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency("brl")
                .setDescription("Pagamento de exemplo")
                .setSource(token)
                .build();

        Charge charge = Charge.create(params);
        return charge.getId();
    }
}
