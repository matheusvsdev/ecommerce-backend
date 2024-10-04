package com.example.matheusvsdev.ecommerce_backend.tests;

import com.example.matheusvsdev.ecommerce_backend.entities.Role;
import com.example.matheusvsdev.ecommerce_backend.entities.User;

import java.time.LocalDate;

public class UserFactory {

    public static User createClientUser() {
        User user = new User(1L, "Maria", "Silva", LocalDate.parse("2000-01-01"), "12345678901", "999999999", "maria@gmail.com", "password");
        user.setActive(true);
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createAdminUser() {
        User user = new User(2L, "João", "Santos", LocalDate.parse("1985-05-15"), "98765432109", "888888888", "joao@gmail.com", "password");
        user.setActive(true);
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }
}
