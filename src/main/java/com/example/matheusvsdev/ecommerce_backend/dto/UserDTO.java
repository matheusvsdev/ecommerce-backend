package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, message = "Mínimo 3 caracteres")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "O nome deve conter apenas letras")
    private String firstName, lastName;

    @NotNull(message = "A data de aniversário é obrigatória")
    @Past(message = "A data de nascimento deve ser uma data passada")
    private LocalDate birthDate;

    @NotBlank(message = "Campo requerido")
    private String cpf;

    private String phone;

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = ".+@.+\\..+", message = "Email deve ter um domínio válido")
    private String email;

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
            , message = "Senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula e um número")
    private String password;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(Long id,
                   String firstName,
                   String lastName,
                   LocalDate birthDate,
                   String cpf,
                   String phone,
                   String email,
                   String password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        birthDate = entity.getBirthDate();
        cpf = entity.getCpf();
        phone = entity.getPhone();
        email = entity.getEmail();
        password = entity.getPassword();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
}
