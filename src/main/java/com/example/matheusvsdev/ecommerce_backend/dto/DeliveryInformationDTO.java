package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Delivery;
import java.time.LocalDateTime;

public class DeliveryInformationDTO {

    private AddressDTO address;

    private LocalDateTime estimatedDeliveryDate;

    private Double freightCost;


    public DeliveryInformationDTO() {
    }

    public DeliveryInformationDTO(AddressDTO address, LocalDateTime estimatedDeliveryDate, Double freightCost) {
        this.address = address;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.freightCost = freightCost;
    }

    public DeliveryInformationDTO(Delivery entity, AddressDTO addressDTO) {
        this.address = addressDTO;
        estimatedDeliveryDate = entity.getEstimatedDeliveryDate();
        freightCost = entity.getFreightCost();
    }

    public AddressDTO getAddress() {
        return address;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public Double getFreightCost() {
        return freightCost;
    }
}
