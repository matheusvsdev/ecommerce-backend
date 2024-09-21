package com.example.matheusvsdev.ecommerce_backend.resource;

import com.example.matheusvsdev.ecommerce_backend.dto.DeliveryDTO;
import com.example.matheusvsdev.ecommerce_backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> updateProduct(@PathVariable Long id, @RequestBody DeliveryDTO deliveryDTO) {
        deliveryDTO = deliveryService.updateDeliveryStatus(id, deliveryDTO);
        return ResponseEntity.ok(deliveryDTO);
    }
}
