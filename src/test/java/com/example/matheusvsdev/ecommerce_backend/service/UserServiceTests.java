package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.RoleDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Role;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.projection.UserDetailsProjection;
import com.example.matheusvsdev.ecommerce_backend.repository.RoleRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import com.example.matheusvsdev.ecommerce_backend.tests.UserDetailsFactory;
import com.example.matheusvsdev.ecommerce_backend.tests.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthService authService;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @MockBean
    private StripePaymentService stripePaymentService;

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

        UserDetailsProjection inactiveUserProjection = Mockito.mock(UserDetailsProjection.class);
        Mockito.when(inactiveUserProjection.getUsername()).thenReturn(existingUsername);
        Mockito.when(inactiveUserProjection.getPassword()).thenReturn("encodedPassword");
        Mockito.when(inactiveUserProjection.getActive()).thenReturn(false);

        List<UserDetailsProjection> inactiveUserList = List.of(inactiveUserProjection);
        Mockito.when(userRepository.searchUserAndRolesByEmail("inactiveUser@gmail.com")).thenReturn(inactiveUserList);

        Role defaultRole = new Role(1L, "ROLE_CLIENT");
        Mockito.when(roleRepository.findByAuthority("ROLE_CLIENT")).thenReturn(defaultRole);

        Mockito.doNothing().when(emailService).userCreationEmailBody(Mockito.any(User.class));

        Mockito.when(passwordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("encodedPassword");
    }

    @Test
    public void addUserRoleShouldAssignDefaultRoleWhenRolesAreNull() {
        // Cenário: UserDTO com roles como null
        UserDTO userDTO = new UserDTO();
        userDTO.setRoles(null); // Define roles como null

        // Simulando o comportamento do roleRepository para "ROLE_CLIENT"
        Mockito.when(roleRepository.findByAuthority("ROLE_CLIENT")).thenReturn(new Role(1L, "ROLE_CLIENT"));

        // Chama o método a ser testado
        userService.addUserRole(user, userDTO);

        // Verifica se o role padrão foi adicionado
        assertTrue(user.getRoles().contains(new Role(1L, "ROLE_CLIENT")));
    }

    @Test
    public void addUserRoleShouldAssignDefaultRoleWhenRolesAreEmpty() {
        // Cenário: UserDTO com uma lista de roles vazia
        UserDTO userDTO = new UserDTO();
        userDTO.setRoles(new HashSet<>()); // Define roles como uma coleção vazia

        // Simulando o comportamento do roleRepository para "ROLE_CLIENT"
        Mockito.when(roleRepository.findByAuthority("ROLE_CLIENT")).thenReturn(new Role(1L, "ROLE_CLIENT"));

        // Chama o método a ser testado
        userService.addUserRole(user, userDTO);

        // Verifica se o role padrão foi adicionado
        assertTrue(user.getRoles().contains(new Role(1L, "ROLE_CLIENT")));
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
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotActive() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("inactiveUser@gmail.com");
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

    @Test
    public void createUserShouldSaveNewUser() {
        User newUser = new User();
        newUser.setFirstName("Maria");
        newUser.setLastName("Silva");
        newUser.setCpf("11122233344");
        newUser.setPhone("88888888888");
        newUser.setBirthDate(LocalDate.parse("1985-01-01"));
        newUser.setEmail("maria@gmail.com");
        newUser.setPassword("Abc123456");

        Mockito.when(userRepository.existsByCpf(newUser.getCpf())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(UserFactory.createClientUser());

        UserDTO result = userService.createUser(new UserDTO());

        assertNotNull(result);
        assertEquals(newUser.getEmail(), result.getEmail());
    }

    @Test
    public void createUserShouldThrowExceptionWhenCpfAlreadyExists() {
        User newUser = new User();
        newUser.setCpf("12345678901");

        UserDTO result = new UserDTO(newUser);

        Mockito.when(userRepository.existsByCpf(newUser.getCpf())).thenReturn(true);

        assertThrows(ArgumentAlreadyExistsException.class, () -> {
            userService.createUser(result);
        });
    }

    @Test
    public void createUserShouldThrowExceptionWhenEmailAlreadyExists() {
        User newUser = new User();
        newUser.setEmail("existinguser@gmail.com");

        UserDTO result = new UserDTO(newUser);

        Mockito.when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);

        assertThrows(ArgumentAlreadyExistsException.class, () -> {
            userService.createUser(result);
        });
    }

    @Test
    public void findAllPagedShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = List.of(UserFactory.createClientUser());
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        Mockito.when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserDTO> result = userService.findAllPaged(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void findByIdShouldReturnUserWhenIdExists() {
        Long existingId = 1L;
        User user = UserFactory.createClientUser();

        Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(user));

        UserDTO result = userService.findById(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 100L;

        Mockito.when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldUpdateUserWhenIdExists() {
        Long existingId = 1L;
        User updateUser = new User();
        updateUser.setFirstName("Updated Name");

        UserDTO userDTO = new UserDTO(updateUser);

        User user = UserFactory.createClientUser();
        Mockito.when(userRepository.getReferenceById(existingId)).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDTO result = userService.update(existingId, userDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getFirstName());
    }

    @Test
    public void updateShouldThrowExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 100L;
        UserDTO dto = new UserDTO();

        Mockito.when(userRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.update(nonExistingId, dto);
        });
    }

    @Test
    public void deleteShouldDeleteUserWhenIdExists() {
        Long existingId = 1L;

        Mockito.when(userRepository.existsById(existingId)).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(existingId);

        assertDoesNotThrow(() -> {
            userService.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 100L;

        Mockito.when(userRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDataIntegrityViolationOccurs() {
        Long existingId = 1L;

        Mockito.when(userRepository.existsById(existingId)).thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(existingId);

        assertThrows(DatabaseException.class, () -> {
            userService.delete(existingId);
        });
    }
}
