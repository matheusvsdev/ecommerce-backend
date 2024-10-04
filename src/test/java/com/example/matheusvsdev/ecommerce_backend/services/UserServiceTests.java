package com.example.matheusvsdev.ecommerce_backend.services;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.projection.UserDetailsProjection;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import com.example.matheusvsdev.ecommerce_backend.service.AuthService;
import com.example.matheusvsdev.ecommerce_backend.service.UserService;
import com.example.matheusvsdev.ecommerce_backend.tests.UserDetailsFactory;
import com.example.matheusvsdev.ecommerce_backend.tests.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";

        user = UserFactory.createClientUser();
        userDetails = UserDetailsFactory.createCustomClientUser(existingUsername);

        Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(user);
        Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(null);

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        UserDetails result = userService.loadUserByUsername(existingUsername);

        assertNotNull(result);
        assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(nonExistingUsername);
        });
    }

    @Test
    public void authenticatedShouldReturnUserWhenUserExists() {
        // Configurar a simulação do método que retorna o usuário autenticado
        Mockito.when(authService.authenticated()).thenReturn(user);

        User result = authService.authenticated();

        assertNotNull(result);
        assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        // Simular exceção quando o usuário não for encontrado
        Mockito.when(authService.authenticated()).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.authenticated();
        });
    }
}
