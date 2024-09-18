package com.example.matheusvsdev.ecommerce_backend.entities;

public enum OrderStatus {

    AGUARDANDO_PAGAMENTO,
    CONFIRMANDO_PAGAMENTO,
    PAGAMENTO_APROVADO,
    FALHA_PAGAMENTO,
    CANCELADO;

}
