package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.InsertUserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UpdateUserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOwnUserService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO updateSelf(UpdateUserDTO dto) {
        User user = authService.authenticated();

        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new ArgumentAlreadyExistsException("Email já está em uso por outro usuário.");
        }

        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactiveAccount() {
        User user = authService.authenticated();
        try {
            user.setActive(false);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
