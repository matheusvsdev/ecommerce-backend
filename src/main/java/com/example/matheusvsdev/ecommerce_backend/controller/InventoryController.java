package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.InventoryDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.InventoryMovementDTO;
import com.example.matheusvsdev.ecommerce_backend.service.InventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/inventory")
@SecurityRequirement(name = "bearerAuth")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<InventoryDTO>> findAll(Pageable pageable) {
        Page<InventoryDTO> inventoryPage = inventoryService.findAll(pageable);
        return ResponseEntity.ok().body(inventoryPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/movement")
    public ResponseEntity<Page<InventoryMovementDTO>> findMovement(Pageable pageable) {
        Page<InventoryMovementDTO> inventoryPage = inventoryService.inventoryMovement(pageable);
        return ResponseEntity.ok().body(inventoryPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}")
    public ResponseEntity<InventoryDTO> replenishStock(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        inventoryDTO = inventoryService.replenishStock(id, inventoryDTO);
        return ResponseEntity.ok(inventoryDTO);
    }
}
