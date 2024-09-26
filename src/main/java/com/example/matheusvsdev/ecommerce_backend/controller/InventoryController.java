package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.InventoryDTO;
import com.example.matheusvsdev.ecommerce_backend.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/product/{id}")
    public ResponseEntity<InventoryDTO> replenishStock(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        inventoryDTO = inventoryService.replenishStock(id, inventoryDTO);
        return ResponseEntity.ok(inventoryDTO);
    }
}
