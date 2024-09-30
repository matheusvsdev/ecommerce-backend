package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.AddressDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ShippingDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.AddressRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.DeliveryRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeliveryService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Transactional
    public ShippingDTO updateDeliveryStatus(Long id, ShippingDTO dto) {
        Shipping delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery não encontrado"));

        if (dto.getStatus() != null) {
            delivery.setStatus(dto.getStatus());
            if (dto.getStatus() == ShippingStatus.ENTREGUE) {
                delivery.getOrder().setStatus(OrderStatus.COMPLETED);
            } else if (dto.getStatus() == ShippingStatus.ENTREGA_NAO_EFETUADA) {
                delivery.getOrder().setStatus(OrderStatus.FAILED);
            }
        }

        if (dto.getDeliveryTime() != null) {
            delivery.setDeliveryTime(dto.getDeliveryTime());
        }

        delivery.setOrderUpdate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        return new ShippingDTO(delivery);
    }

    private static final Double STANDARD_RATE = 0.015;
    private static final Map<State, Double> DISTANCE_MAP = new HashMap<>();

    static {
        DISTANCE_MAP.put(State.AL, 0.0);
        DISTANCE_MAP.put(State.SE, 279.0);
        DISTANCE_MAP.put(State.BA, 580.0);
        DISTANCE_MAP.put(State.PI, 1202.0);
        DISTANCE_MAP.put(State.PE, 258.0);
        DISTANCE_MAP.put(State.PB, 382.0);
        DISTANCE_MAP.put(State.RN, 540.0);
        DISTANCE_MAP.put(State.CE, 1035.0);
        DISTANCE_MAP.put(State.MA, 1631.0);
        DISTANCE_MAP.put(State.TO, 1899.0);
        DISTANCE_MAP.put(State.PA, 1679.0);
        DISTANCE_MAP.put(State.AP, 2010.0);
        DISTANCE_MAP.put(State.RR, 3096.0);
        DISTANCE_MAP.put(State.AM, 2780.0);
        DISTANCE_MAP.put(State.AC, 3515.0);
        DISTANCE_MAP.put(State.RO, 3093.0);
        DISTANCE_MAP.put(State.MT, 2307.0);
        DISTANCE_MAP.put(State.GO, 1663.0);
        DISTANCE_MAP.put(State.DF, 1962.0);
        DISTANCE_MAP.put(State.MS, 2355.0);
        DISTANCE_MAP.put(State.MG, 1807.0);
        DISTANCE_MAP.put(State.ES, 1672.0);
        DISTANCE_MAP.put(State.RJ, 2128.0);
        DISTANCE_MAP.put(State.SP, 1937.0);
        DISTANCE_MAP.put(State.PR, 2264.0);
        DISTANCE_MAP.put(State.SC, 2407.0);
        DISTANCE_MAP.put(State.RS, 2780.0);
    }

    public Double calculateFreight(State clientState) {

        Double distance = DISTANCE_MAP.get(clientState);
        if (distance == null) {
            throw new IllegalArgumentException("Estado desconhecido");
        }
        return Math.round(distance * STANDARD_RATE * 100.0) / 100.0;
    }
}
