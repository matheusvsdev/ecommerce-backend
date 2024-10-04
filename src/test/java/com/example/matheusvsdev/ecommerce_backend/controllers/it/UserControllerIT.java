package com.example.matheusvsdev.ecommerce_backend.controllers.it;

import com.example.matheusvsdev.ecommerce_backend.TokenUtil;
import com.example.matheusvsdev.ecommerce_backend.controller.UserController;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.service.EmailService;
import com.example.matheusvsdev.ecommerce_backend.service.StripePaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StripePaymentService stripePaymentService;

    @MockBean
    private EmailService emailService;

    private String adminToken, clientToken;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() throws Exception {
        String adminUsername = "mvdigitalmarketing22@gmail.com";
        String adminPassword = "123456";
        String clientUsername = "matheusvaldevino1997@outlook.com";
        String clientPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);

        // Inicializando um UserDTO para os testes
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setCpf("12345678901");
        user.setPhone("88888888888");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        user.setPassword("Abc123456");
        userDTO = new UserDTO(user);
    }

    @Test
    public void insertShouldReturnCreatedWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(userDTO);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Doe"));
        result.andExpect(jsonPath("$.email").value("johndoe@gmail.com"));
        result.andExpect(jsonPath("$.birthDate").value("2000-01-01"));
        result.andExpect(jsonPath("$.cpf").value("12345678901"));
        result.andExpect(jsonPath("$.phone").value("88888888888"));

    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenRequiredFieldsAreMissing() throws Exception {
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        user.setCpf("");
        user.setEmail("");
        user.setPassword("");

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'firstName')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'lastName')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'cpf')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'email')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'password')].message").value(Matchers.hasItem("Campo requerido")));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenFirstNameOrLastNameIsLessThanThreeCharacters() throws Exception {
        User user = new User();
        user.setFirstName("as");
        user.setLastName("ab");
        user.setCpf("11111111111");
        user.setEmail("valence@gmail.com");
        user.setPassword("Abc123456");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].message").value("Mínimo 3 caracteres"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenNameDoesNotHaveOnlyLetters() throws Exception {
        User user = new User();
        user.setFirstName("Jo4o");
        user.setLastName("Si1v4");
        user.setCpf("11111111111");
        user.setEmail("valence@gmail.com");
        user.setPassword("Abc123456");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenDataIsNull() throws Exception {
        User user = new User();
        user.setFirstName("Maria");
        user.setLastName("Cecília");
        user.setCpf("12345678901");
        user.setEmail("cecilia@gmail.com");
        user.setPassword("Abc123456");
        user.setBirthDate(null);

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].message").value("A data de aniversário é obrigatória"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenEmailInvalid() throws Exception {
        User user = new User();
        user.setEmail("invalid-email"); // Email inválido

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenBirthDateIsInTheFuture() throws Exception {
        User user = new User();
        user.setFirstName("Marcos");
        user.setLastName("Valence");
        user.setCpf("11111111111");
        user.setEmail("valence@gmail.com");
        user.setPassword("Abc123456");
        user.setBirthDate(LocalDate.parse("2030-01-01")); // Data no futuro

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].message").value("A data de nascimento deve ser uma data passada"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenPasswordDoesNotContainEightCharactersUppercaseLowercaseAndNumber() throws Exception {
        User user = new User();
        user.setFirstName("Maria");
        user.setLastName("Miranda");
        user.setCpf("11111111111");
        user.setEmail("valence@gmail.com");
        user.setPassword("Abc123456");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        user.setPassword("password"); // Senha inválida

        UserDTO dto = new UserDTO(user);

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].message").value("Senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula e um número"));
    }
}
