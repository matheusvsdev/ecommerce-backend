package com.example.matheusvsdev.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateUserDTO {

    private String phone;

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = ".+@.+\\..+", message = "Email deve ter um domínio válido")
    private String email;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public UpdateUserDTO(UserDTO userDTO) {
        phone = userDTO.getPhone();
        email = userDTO.getEmail();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
