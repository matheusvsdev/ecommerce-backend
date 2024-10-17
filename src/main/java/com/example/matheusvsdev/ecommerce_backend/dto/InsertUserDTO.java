package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class InsertUserDTO extends UserDTO {

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
            , message = "Senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula e um número")
    private String password;

    public InsertUserDTO(Long id, String firstName, String lastName, LocalDate birthDate, String cpf, String phone, String email, String password) {
        super(id, firstName, lastName, birthDate, cpf, phone, email);
        this.password = password;
    }

    public InsertUserDTO() {
        super();
    }

    public InsertUserDTO(User entity) {
        super(entity);
        this.password = password;
    }

    public InsertUserDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
