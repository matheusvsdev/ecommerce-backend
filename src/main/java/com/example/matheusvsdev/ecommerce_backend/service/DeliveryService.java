package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.DeliveryDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Delivery;
import com.example.matheusvsdev.ecommerce_backend.entities.DeliveryStatus;
import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.entities.OrderStatus;
import com.example.matheusvsdev.ecommerce_backend.repository.DeliveryRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeliveryService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryDTO updateDeliveryStatus(Long id, DeliveryDTO dto) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery não encontrado"));

        if (dto.getDeliveryStatus() != null) {
            delivery.setDeliveryStatus(dto.getDeliveryStatus());
        }

        if (dto.getDeliveryStatus() == DeliveryStatus.ENTREGUE) {
            delivery.getOrder().setOrderStatus(OrderStatus.CONCLUIDO);
        }

        if (dto.getDeliveryStatus() == DeliveryStatus.ENTREGA_NAO_EFETUADA) {
            delivery.getOrder().setOrderStatus(OrderStatus.CANCELADO);
        }

        if (dto.getEstimatedDeliveryDate() != null) {
            delivery.setEstimatedDeliveryDate(dto.getEstimatedDeliveryDate());
        }

        delivery.setOrderUpdateDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        return new DeliveryDTO(delivery);
    }
}
