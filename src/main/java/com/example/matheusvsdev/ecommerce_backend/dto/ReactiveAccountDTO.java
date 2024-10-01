package com.example.matheusvsdev.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ReactiveAccountDTO {

    private String token;

    private Boolean active;

    public ReactiveAccountDTO() {
    }

    public ReactiveAccountDTO(String token, Boolean active) {
        this.token = token;
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
