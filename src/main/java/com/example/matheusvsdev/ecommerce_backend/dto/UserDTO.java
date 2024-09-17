package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.User;

import java.time.LocalDate;

public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String cpf;

    private String phone;

    private String email;

    private String password;

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
}
