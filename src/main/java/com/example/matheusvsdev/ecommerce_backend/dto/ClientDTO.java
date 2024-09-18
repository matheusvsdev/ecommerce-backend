package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.User;

public class ClientDTO {

    private Long id;

    private String name;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getFirstName() + " " + entity.getLastName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
