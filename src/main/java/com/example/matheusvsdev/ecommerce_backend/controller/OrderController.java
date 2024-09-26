package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.service.OrderService;
import com.example.matheusvsdev.ecommerce_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@RequestBody OrderDTO dto) {
        OrderDTO order = orderService.placeOrderFromCart(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getOrderId())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO dto = orderService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orderList = orderService.findAll();
        return ResponseEntity.ok(orderList);
    }

    @PostMapping(value = "/payment/{id}")
    public ResponseEntity<String> processPayment(@PathVariable Long id) {
        paymentService.processPayment(id);

        return ResponseEntity.ok("Pagamento processado e e-mail enviado com a nota fiscal.");
    }
}
