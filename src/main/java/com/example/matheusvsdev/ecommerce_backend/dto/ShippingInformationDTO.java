package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Shipping;
import java.time.LocalDateTime;

public class ShippingInformationDTO {

    private AddressDTO address;

    private LocalDateTime deliveryTime;

    private Double freightCost;


    public ShippingInformationDTO() {
    }

    public ShippingInformationDTO(AddressDTO address, LocalDateTime deliveryTime, Double freightCost) {
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.freightCost = freightCost;
    }

    public ShippingInformationDTO(Shipping entity, AddressDTO addressDTO) {
        this.address = addressDTO;
        deliveryTime = entity.getDeliveryTime();
        freightCost = entity.getFreightCost();
    }

    public AddressDTO getAddress() {
        return address;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public Double getFreightCost() {
        return freightCost;
    }
}
