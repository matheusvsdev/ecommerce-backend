package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Address;
import com.example.matheusvsdev.ecommerce_backend.entities.State;

public class AddressDTO {

    private Long id;

    private State state;

    private String neighborhood, street, number, city, cep, complement;

    public AddressDTO() {
    }

    public AddressDTO(Long id, String neighborhood, String street, String number, String city, State state, String cep, String complement) {
        this.id = id;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.cep = cep;
        this.complement = complement;
    }

    public AddressDTO(Address entity) {
        id = entity.getId();
        neighborhood = entity.getNeighborhood();
        street = entity.getStreet();
        number = entity.getNumber();
        city = entity.getCity();
        state = entity.getState();
        cep = entity.getCep();
        complement = entity.getComplement();
    }

    public Long getId() {
        return id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getCep() {
        return cep;
    }

    public String getComplement() {
        return complement;
    }
}
