package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.ShippingDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ShippingInformationDTO;
import com.example.matheusvsdev.ecommerce_backend.service.AddressService;
import com.example.matheusvsdev.ecommerce_backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ShippingDTO> updateDeliveryStatus(@PathVariable Long id, @RequestBody ShippingDTO deliveryDTO) {
        deliveryDTO = deliveryService.updateDeliveryStatus(id, deliveryDTO);
        return ResponseEntity.ok(deliveryDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/info/{addressId}")
    public ResponseEntity<ShippingInformationDTO> getDeliveryInformation(@PathVariable Long addressId ) {
        ShippingInformationDTO deliveryInformation = deliveryService.getDeliveryInformation(addressId);

        return ResponseEntity.ok(deliveryInformation);
    }

}
