package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderAdminDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderResponseDTO;
import com.example.matheusvsdev.ecommerce_backend.service.OrderService;
import com.example.matheusvsdev.ecommerce_backend.service.StripePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StripePaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@RequestBody OrderDTO dto) {
        OrderResponseDTO order = orderService.createOrder(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getOrderId())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        OrderResponseDTO dto = orderService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable) {
        Page<OrderResponseDTO> orderList = orderService.findAll(pageable);
        return ResponseEntity.ok(orderList);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/search")
    public ResponseEntity<Page<OrderAdminDTO>> searchOrders(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable) {

        Page<OrderAdminDTO> result = orderService.searchOrders(clientId, cpf, status, paymentMethod, startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
}
