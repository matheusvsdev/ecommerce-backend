package com.example.matheusvsdev.ecommerce_backend.docs;

import com.example.matheusvsdev.ecommerce_backend.dto.InsertUserDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface UserControllerDocs {

    @Operation(summary = "Cria um novo usuário", description = "Forneça os detalhes do usuário para criar um novo",
            requestBody = @RequestBody(description = "Detalhes do novo usuário a ser criado", required = true,
                    content = @Content(
                            schema = @Schema(implementation = InsertUserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Criação de Usuário",
                                    value = """
                    {
                        "firstName": "João",
                        "lastName": "Silva",
                        "birthDate": "1990-01-01",
                        "cpf": "123.456.789-00",
                        "phone": "(11) 91234-5678",
                        "email": "joao.silva@example.com",
                        "password": "senha123",
                        "roles": [
                            {
                                "id": 1,
                                "authority": "ROLE_USER"
                            }
                        ]
                    }
                    """))))
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
                """))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: O corpo da requisição contém um JSON mal formatado ou inválido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de JSON inválido",
                                    value = """
                            {
                                "timestamp": "2024-10-17T22:00:00.123456Z",
                                "status": 400,
                                "error": "JSON mal formatado",
                                "message": "O corpo da requisição contém um JSON mal formatado ou inválido",
                                "path": "/users"
                            }
                            """))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: Email ou CPF já está em uso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArgumentAlreadyExistsException.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Conflito",
                                    value = """
                            {
                                "timestamp": "2024-10-17T21:43:26.382164Z",
                                "status": 409,
                                "error": "Email já cadastrado",
                                "path": "/users"
                            }
                            """))),

            @ApiResponse(
                    responseCode = "422", description = "Unprocessable Entity: Dados inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MethodArgumentNotValidException.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de dados inválidos",
                                    value = """
                            {
                                "timestamp": "2024-10-17T20:52:24.428554Z",
                                "status": 422,
                                "error": "Dados inválidos",
                                "path": "/users",
                                "errors": [
                                    {
                                        "fieldName": "firstName",
                                        "message": "Mínimo 3 caracteres"
                                    }
                                ]
                            }
                            """)))
    })
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody InsertUserDTO client);


    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista paginada de usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não autenticado",
                                    value = """
                            {
                                "status": 401,
                                "error": "Unathorized",
                                "message": "Acesso não autorizado. Por favor, verifique se o token está presente e válido.",
                                "path": "/users/1"
                            }
                            """))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de acesso negado",
                                    value = """
                        {
                            "status": 403,
                            "error": "Forbidden",
                            "message": "Acesso negado. Confirme suas permissões.",
                            "path": "users"
                        }
                        """)))
    })
    ResponseEntity<Page<UserDTO>> findAll(Pageable pageable);

    @Operation(summary = "Busca um usuário por ID", description = "Retorna os detalhes de um usuário específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
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
                    """))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não autenticado",
                                    value = """
                            {
                                "status": 401,
                                "error": "Unathorized",
                                "message": "Acesso não autorizado. Por favor, verifique se o token está presente e válido.",
                                "path": "/users/1"
                            }
                            """))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Exemplo de acesso negado",
                                value = """
                        {
                            "status": 403,
                            "error": "Forbidden",
                            "message": "Acesso negado. Confirme suas permissões.",
                            "path": "users"
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Erro 404",
                                    value = """
                            {
                                "status": 404,
                                "error": "Not Found",
                                "message": "Usuário com o ID 1 não encontrado",
                                "path": "/users/1"
                            }
                            """)))
    })
    ResponseEntity<UserDTO> findById(
            @Parameter(description = "ID do usuário", example = "1", required = true) Long id);

    @Operation(summary = "Atualiza um usuário por ID", description = "Atualiza as informações do usuário especificado pelo ID.",
            requestBody = @RequestBody(description = "Detalhes do usuário a serem atualizados", required = true,
                    content = @Content(
                            schema = @Schema(implementation = InsertUserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Atualização de Usuário",
                                    value = """
                            {
                                "firstName": "Benjamim",
                                "lastName": "Brown",
                                "email": "mv@gmail.com",
                                "cpf": "12345678901",
                                "birthDate": "1998-02-12"
                            }
                            """))))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de atualização de dados do Usuário",
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
                            "authority": "ROLE_CLIENT"
                        }
                    ]
                }
                """))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: O corpo da requisição contém um JSON mal formatado ou inválido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de JSON inválido",
                                    value = """
                            {
                                "timestamp": "2024-10-17T22:00:00.123456Z",
                                "status": 400,
                                "error": "JSON mal formatado",
                                "message": "O corpo da requisição contém um JSON mal formatado ou inválido",
                                "path": "/users"
                            }
                            """))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não autenticado",
                                    value = """
                            {
                                "status": 401,
                                "error": "Unathorized",
                                "message": "Acesso não autorizado. Por favor, verifique se o token está presente e válido.",
                                "path": "/users/1"
                            }
                            """))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de acesso negado",
                                    value = """
                        {
                            "status": 403,
                            "error": "Forbidden",
                            "message": "Acesso negado. Confirme suas permissões.",
                            "path": "users"
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não encontrado",
                                    value = """
                            {
                                "timestamp": "2024-10-11T03:40:44.363563073Z",
                                "status": "404",
                                "error": "Usuário não encontrado.",
                                "path": "/users/1"
                            }
                            """)))
    })
    ResponseEntity<UserDTO> update(
            @Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true) Long id,
            @Valid @RequestBody UserDTO dto);

    @Operation(summary = "Atualiza o próprio perfil", description = "Permite que o usuário atualize seu próprio perfil.",
            requestBody = @RequestBody(description = "Detalhes do usuário a serem atualizados", required = true,
                    content = @Content(
                            schema = @Schema(implementation = InsertUserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Atualização do Próprio Usuário",
                                    value = """
                            {
                                "firstName": "Benjamim",
                                "lastName": "Brown",
                                "email": "mv@gmail.com",
                                "cpf": "12345678901",
                                "birthDate": "1998-02-12"
                            }
                            """))))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de atualização de dados do Usuário",
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
                            "authority": "ROLE_CLIENT"
                        }
                    ]
                }
                """))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: O corpo da requisição contém um JSON mal formatado ou inválido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de JSON inválido",
                                    value = """
                            {
                                "timestamp": "2024-10-17T22:00:00.123456Z",
                                "status": 400,
                                "error": "JSON mal formatado",
                                "message": "O corpo da requisição contém um JSON mal formatado ou inválido",
                                "path": "/users"
                            }
                            """))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não autenticado",
                                    value = """
                            {
                                "status": 401,
                                "error": "Unathorized",
                                "message": "Acesso não autorizado. Por favor, verifique se o token está presente e válido.",
                                "path": "/users/1"
                            }
                            """)))
    })
    ResponseEntity<UserDTO> updateSelf(@Valid @RequestBody UpdateUserDTO dto);

    @Operation(summary = "Deleta um usuário por ID", description = "Permite a exclusão de um usuário especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de acesso negado",
                                    value = """
                        {
                            "status": 403,
                            "error": "Forbidden",
                            "message": "Acesso negado. Confirme suas permissões.",
                            "path": "users"
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não encontrado",
                                    value = """
                            {
                                "timestamp": "2024-10-11T03:40:44.363563073Z",
                                "status": "404",
                                "error": "Usuário não encontrado.",
                                "path": "/users/1"
                            }
                            """))),
            @ApiResponse(responseCode = "409", description = "Usuário associado a outros registros.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário associado a outros registros",
                                    value = """
                            {
                                "timestamp": "2024-10-11T03:40:44.363563073Z",
                                "status": "409",
                                "error": "Falha de integridade referencial.",
                                "path": "/users/1"
                            }
                            """)))
    })
    ResponseEntity<Void> delete(@Parameter(description = "ID do usuário a ser deletado", example = "1", required = true) Long id);

    @Operation(summary = "Desativa a própria conta", description = "Permite que o usuário atual desative a própria conta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta desativada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário não autenticado",
                                    value = """
                            {
                                "status": 401,
                                "error": "Unathorized",
                                "message": "Acesso não autorizado. Por favor, verifique se o token está presente e válido.",
                                "path": "/users/1"
                            }
                            """)))
    })
    ResponseEntity<Void> deleteSelf();
}