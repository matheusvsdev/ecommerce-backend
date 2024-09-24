package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    protected User autenthicated() {
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authenticator.getPrincipal();
        String username = jwt.getClaim("username");
        return userRepository.findByEmail(username);
    }
}
