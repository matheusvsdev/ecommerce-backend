package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.InventoryDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Inventory;
import com.example.matheusvsdev.ecommerce_backend.entities.InventoryMovement;
import com.example.matheusvsdev.ecommerce_backend.entities.MovementType;
import com.example.matheusvsdev.ecommerce_backend.repository.InventoryMovementRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.InventoryRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.IllegalArgumentException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Transactional
    public InventoryDTO replenishStock(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        addStockMovement(inventory, dto);

        inventory = inventoryRepository.save(inventory);

        return new InventoryDTO(inventory);
    }

    @Transactional
    public void updateStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        // Verifica se a quantidade é suficiente
        if (inventory.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade indisponível em estoque");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventory.setOutputQuantity(inventory.getOutputQuantity() + quantity);

        InventoryMovement movement = new InventoryMovement(
                inventory.getProduct(),
                "SAIDA",
                quantity,
                inventory.getQuantity()
        );

        inventoryMovementRepository.save(movement);

        inventory.updateAvailability();

        inventoryRepository.save(inventory);
    }

    public void addStockMovement(Inventory inventory, InventoryDTO dto) {
        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(inventory.getProduct());
        movement.setMovementType(MovementType.ENTRADA.name());
        movement.setQuantity(dto.getQuantity());

        int newStock = inventory.getQuantity() + dto.getQuantity();

        movement.setRemainingStock(newStock);
        movement.setMoment(LocalDateTime.now());

        inventoryMovementRepository.save(movement);

        inventory.setQuantity(newStock);

        inventory.setOutputQuantity(0);

        inventory.updateAvailability();
    }
}
