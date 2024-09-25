package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.EmailDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.NewPasswordDTO;
import com.example.matheusvsdev.ecommerce_backend.service.AuthService;
import com.example.matheusvsdev.ecommerce_backend.service.PasswordRecoveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/password")
public class PasswordRecoveryController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordRecoveryService passwordRecoveyService;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@RequestBody EmailDTO body) {
        passwordRecoveyService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid  @RequestBody NewPasswordDTO newPasswordDTO) {
        passwordRecoveyService.saveNewPassword(newPasswordDTO);
        return ResponseEntity.noContent().build();
    }
}
