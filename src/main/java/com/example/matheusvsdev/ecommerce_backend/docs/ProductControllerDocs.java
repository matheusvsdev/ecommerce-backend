package com.example.matheusvsdev.ecommerce_backend.docs;

import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductControllerDocs {

    @Operation(summary = "Cria um novo produto", description = "Insira os dados do produto para criar um novo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalhes do novo usuário a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO);

    @Operation(summary = "Busca um produto por ID", description = "Retorna os detalhes de um produto específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Produto",
                                    value = "{\n" +
                                            "  \"id\": 13,\n" +
                                            "  \"name\": \"Smart TV LG 55\\\" OLED\",\n" +
                                            "  \"description\": \"TV 4K com tecnologia OLED para cores mais vivas.\",\n" +
                                            "  \"img\": \"https://imageurl.com/lg55oled\",\n" +
                                            "  \"price\": 4599.90,\n" +
                                            "  \"categories\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 3,\n" +
                                            "      \"name\": \"Eletronicos\"\n" +
                                            "    }\n" +
                                            "  ],\n" +
                                            "  \"available\": true,\n" +
                                            "  \"inventory\": {\n" +
                                            "    \"id\": 13,\n" +
                                            "    \"productId\": 13,\n" +
                                            "    \"quantity\": 30,\n" +
                                            "    \"outputQuantity\": 0,\n" +
                                            "    \"updateTime\": \"2024-10-06T23:45:19.771959\"\n" +
                                            "  }\n" +
                                            "}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<ProductDTO> findById(
            @Parameter(description = "ID do produto", example = "1", required = true) Long id);

    @Operation(summary = "Lista todos os produtos paginados", description = "Retorna uma lista paginada de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable);
}
