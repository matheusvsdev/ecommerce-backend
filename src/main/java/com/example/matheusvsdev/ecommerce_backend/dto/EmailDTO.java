package com.example.matheusvsdev.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmailDTO {

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = ".+@.+\\..+", message = "Email deve ter um domínio válido")
    private String email;

    public EmailDTO() {
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
