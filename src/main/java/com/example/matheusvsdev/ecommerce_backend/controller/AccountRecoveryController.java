package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.EmailDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.NewPasswordDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ReactiveAccountDTO;
import com.example.matheusvsdev.ecommerce_backend.service.AccountRecoveryService;
import com.example.matheusvsdev.ecommerce_backend.service.AuthService;
import com.example.matheusvsdev.ecommerce_backend.service.PasswordRecoveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountRecoveryController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AccountRecoveryService accountRecoveryService;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@RequestBody EmailDTO body) {
        accountRecoveryService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/reactive-account")
    public ResponseEntity<Void> reactive(@RequestBody ReactiveAccountDTO reactiveAccountDTO) {
        accountRecoveryService.reactiveAccount(reactiveAccountDTO);
        return ResponseEntity.noContent().build();
    }
}
