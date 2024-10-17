package com.example.matheusvsdev.ecommerce_backend.controllers.it;

import com.example.matheusvsdev.ecommerce_backend.TokenUtil;
import com.example.matheusvsdev.ecommerce_backend.controller.UserController;
import com.example.matheusvsdev.ecommerce_backend.dto.InsertUserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.service.EmailService;
import com.example.matheusvsdev.ecommerce_backend.service.StripePaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.Assert.assertNotNull;
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
    private InsertUserDTO insertUserDTO;

    @BeforeEach
    void setUp() throws Exception {
        // Gerando os tokens
        generateTokens();

        // Inicializando o DTO de usuário
        initializeInsertUserDTO();
    }

    @Test
    public void insertShouldReturnCreatedWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(insertUserDTO);

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
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "",
                "",  // Sobrenome vazio
                LocalDate.parse("2000-01-01"),
                "",
                "",
                "",
                ""
        );

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'firstName')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'lastName')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'cpf')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'email')].message").value(Matchers.hasItem("Campo requerido")));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'password')].message").value(Matchers.hasItem("Campo requerido")));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenFirstNameOrLastNameIsLessThanThreeCharacters() throws Exception {
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "as",
                "ab",
                LocalDate.parse("2000-01-01"),
                "11111111111",
                "88888888888",
                "valence@gmail.com",
                "Abc123456"

        );

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
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "Jo4o",
                "Si1v4",
                LocalDate.parse("2000-01-01"),
                "11111111111",
                "88888888888",
                "valence@gmail.com",
                "Abc123456"
        );

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'firstName')].message").value("O nome deve conter apenas letras"));
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'lastName')].message").value("O nome deve conter apenas letras"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenDataIsNull() throws Exception {
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "Maria",
                "Cecília",
                null,
                "12345678901",
                "88888888888",
                "cecilia@gmail.com",
                "Abc123456"
        );

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
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "Maria",
                "Silva",
                LocalDate.parse("2000-01-01"),
                "11111111111",
                "88888888888",
                "invalid-email",
                "Abc123456"
        );

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[?(@.fieldName == 'email')].message").value("Email deve ter um domínio válido"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenBirthDateIsInTheFuture() throws Exception {
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "Marcos",
                "Valence",
                LocalDate.parse("2030-01-01"),
                "11111111111",
                "88888888888",
                "valence@gmail.com",
                "Abc123456"
        );

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
        InsertUserDTO dto = new InsertUserDTO(
                null,
                "Maria",
                "Miranda",
                LocalDate.parse("2000-01-01"),
                "11111111111",
                "88888888888",
                "valence@gmail.com",
                "password"  // Senha inválida (sem número e sem letra maiúscula)
        );

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].message").value("Senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula e um número"));
    }

    private void generateTokens() throws Exception {
        String adminUsername = "test1@gmail.com";
        String adminPassword = "123456";
        String clientUsername = "test2@outlook.com";
        String clientPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);

        // Validando se os tokens foram gerados corretamente
        Assertions.assertNotNull("Admin token não pode ser nulo", adminToken);
        Assertions.assertNotNull("Client token não pode ser nulo", clientToken);
    }

    private void initializeInsertUserDTO() {
        insertUserDTO = new InsertUserDTO(
                null,
                "John",
                "Doe",
                LocalDate.parse("2000-01-01"),
                "12345678901",
                "88888888888",
                "johndoe@gmail.com",
                "Abc123456"
        );
    }
}
