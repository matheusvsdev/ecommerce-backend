package com.example.matheusvsdev.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NewPasswordDTO {

    private String token;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
            , message = "Senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula e um número")
    private String password;

    public NewPasswordDTO() {
    }

    public NewPasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
