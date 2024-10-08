package com.example.matheusvsdev.ecommerce_backend.docs;

import com.example.matheusvsdev.ecommerce_backend.dto.UpdateUserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface UserControllerDocs {

    @Operation(summary = "Cria um novo usuário", description = "Forneça os detalhes do usuário para criar um novo",
            requestBody = @RequestBody(description = "Detalhes do novo usuário a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Usuário",
                                    value = """
                {
                    "id": 1,
                    "firstName": "João",
                    "lastName": "Silva",
                    "birthDate": "1990-01-01",
                    "cpf": "123.456.789-00",
                    "phone": "(11) 91234-5678",
                    "email": "joao.silva@example.com",
                    "roles": [
                        {
                            "id": 1,
                            "authority": "ROLE_USER"
                        }
                    ]
                }
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))
            ),

            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: e-mail ou CPF já está em uso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArgumentAlreadyExistsException.class))
            ),

            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity: Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MethodArgumentNotValidException.class))
            ),
    })
    ResponseEntity<UserDTO> createUser(@Valid @org.springframework.web.bind.annotation.RequestBody UserDTO client);


    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista paginada de usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<Page<UserDTO>> findAll(Pageable pageable);

    @Operation(summary = "Busca um usuário por ID", description = "Retorna os detalhes de um usuário específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class),
                    examples = @ExampleObject(
                            name = "Exemplo de Usuário",
                            value = "{ \"id\": 1, \"firstName\": \"João\", \"lastName\": \"Silva\", \"birthDate\": \"1990-01-01\", \"cpf\": \"123.456.789-00\", \"phone\": \"(11) 91234-5678\", \"email\": \"joao.silva@example.com\", \"roles\": [ { \"id\": 1, \"authority\": \"ROLE_USER\" } ] }"
                    ))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Erro 404",
                                    value = "{ \"status\": 404, \"error\": \"Not Found\", \"message\": \"Usuário com o ID 1 não encontrado\", \"path\": \"/users/1\" }"
                            )))
    })
    ResponseEntity<UserDTO> findById(
            @Parameter(description = "ID do usuário", example = "1", required = true) Long id);

    @Operation(summary = "Atualiza um usuário por ID", description = "Atualiza as informações do usuário especificado pelo ID.",
            requestBody = @RequestBody(description = "Detalhes do usuário a serem atualizados", required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UserDTO> update(
            @Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true) Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody UserDTO dto);

    @Operation(summary = "Atualiza o próprio perfil", description = "Permite que o usuário atualize seu próprio perfil.",
            requestBody = @RequestBody(description = "Detalhes do usuário a serem atualizados", required = true,
                    content = @Content(schema = @Schema(implementation = UpdateUserDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    ResponseEntity<UserDTO> updateSelf(@Valid @org.springframework.web.bind.annotation.RequestBody UpdateUserDTO dto);

    @Operation(summary = "Deleta um usuário por ID", description = "Permite a exclusão de um usuário especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> delete(@Parameter(description = "ID do usuário a ser deletado", example = "1", required = true) Long id);

    @Operation(summary = "Desativa a própria conta", description = "Permite que o usuário atual desative a própria conta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta desativada com sucesso")
    })
    ResponseEntity<Void> deleteSelf();
}