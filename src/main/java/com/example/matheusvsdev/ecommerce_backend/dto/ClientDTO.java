package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.User;

public class ClientDTO {

    private Long id;

    private String name;

    private String cpf;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getFirstName() + " " + entity.getLastName();
        cpf = entity.getCpf();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }
}
