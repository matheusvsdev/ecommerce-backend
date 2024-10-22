package com.example.matheusvsdev.ecommerce_backend.docs;

import com.example.matheusvsdev.ecommerce_backend.dto.InventoryDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.InventoryMovementDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
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
import org.springframework.web.ErrorResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface InventoryControllerDocs {

    @Operation(summary = "Lista todos os produtos do estoque paginados", description = "Retorna uma lista paginada de todos os produtos do estoque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos do estoque retornada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = InventoryDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Busca Paginada de Produtos do Estoque",
                                    value = """
                                    {
                                        "content": [
                                            {
                                                "id": 1,
                                                "productId": 1,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.635626"
                                            },
                                            {
                                                "id": 2,
                                                "productId": 2,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.636042"
                                            },
                                            {
                                                "id": 3,
                                                "productId": 3,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.636266"
                                            },
                                            {
                                                "id": 4,
                                                "productId": 4,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.636503"
                                            },
                                            {
                                                "id": 5,
                                                "productId": 5,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.636727"
                                            },
                                            {
                                                "id": 6,
                                                "productId": 6,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.636966"
                                            },
                                            {
                                                "id": 7,
                                                "productId": 7,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.63719"
                                            },
                                            {
                                                "id": 8,
                                                "productId": 8,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.637415"
                                            },
                                            {
                                                "id": 9,
                                                "productId": 9,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.637637"
                                            },
                                            {
                                                "id": 10,
                                                "productId": 10,
                                                "quantity": 30,
                                                "outputQuantity": 0,
                                                "updateTime": "2024-10-21T23:28:12.637877"
                                            }
                                        ],
                                        "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 10,
                                            "sort": {
                                                "empty": true,
                                                "unsorted": true,
                                                "sorted": false
                                            },
                                            "offset": 0,
                                            "unpaged": false,
                                            "paged": true
                                        },
                                        "last": false,
                                        "totalElements": 30,
                                        "totalPages": 3,
                                        "first": true,
                                        "size": 10,
                                        "number": 0,
                                        "sort": {
                                            "empty": true,
                                            "unsorted": true,
                                            "sorted": false
                                        },
                                        "numberOfElements": 10,
                                        "empty": false
                                }
                                """))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: Parâmetro está mal formatado ou inválido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de JSON inválido",
                                    value = """
                            {
                                "timestamp": "2024-10-17T22:00:00.123456Z",
                                "status": 400,
                                "error": "Parâmetro mal formatado",
                                "message": "Parâmetro está mal formatado ou inválido",
                                "path": "/inventory"
                            }
                            """))),
    })
    @Parameter(description = "Número da página (0...N)", name = "page", example = "0", required = false)
    @Parameter(description = "Quantidade de elementos por página", name = "size", example = "10", required = false)
    @Parameter(description = "Critério de ordenação", name = "sort", example = "asc", required = false)
    ResponseEntity<Page<InventoryDTO>> findAll(Pageable pageable);

    @Operation(summary = "Lista paginada da movimentação do estoque", description = "Retorna uma lista paginada da movimentação do estoque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista da movimentação do estoque retornada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = InventoryMovementDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Busca Paginada de Movimentação do Estoque",
                                    value = """
                                    {
                                        "content": [
                                            {
                                                "id": 1,
                                                "productId": 28,
                                                "movementType": "SAIDA",
                                                "quantity": 1,
                                                "moment": "2024-02-12T15:32:27",
                                                "remainingStock": 29
                                            },
                                            {
                                                "id": 2,
                                                "productId": 11,
                                                "movementType": "SAIDA",
                                                "quantity": 1,
                                                "moment": "2024-02-12T15:32:27",
                                                "remainingStock": 29
                                            },
                                            {
                                                "id": 3,
                                                "productId": 1,
                                                "movementType": "SAIDA",
                                                "quantity": 1,
                                                "moment": "2024-02-27T19:02:11",
                                                "remainingStock": 29
                                            },
                                            {
                                                "id": 4,
                                                "productId": 7,
                                                "movementType": "SAIDA",
                                                "quantity": 1,
                                                "moment": "2024-02-03T09:01:38",
                                                "remainingStock": 29
                                            },
                                            {
                                                "id": 5,
                                                "productId": 30,
                                                "movementType": "SAIDA",
                                                "quantity": 1,
                                                "moment": "2024-07-23T10:12:47",
                                                "remainingStock": 29
                                            },
                                            {
                                                "id": 6,
                                                "productId": 28,
                                                "movementType": "ENTRADA",
                                                "quantity": 31,
                                                "moment": "2024-07-25T17:31:03",
                                                "remainingStock": 60
                                            }
                                        ],
                                        "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 10,
                                            "sort": {
                                                "empty": true,
                                                "sorted": false,
                                                "unsorted": true
                                            },
                                            "offset": 0,
                                            "unpaged": false,
                                            "paged": true
                                        },
                                        "last": true,
                                        "totalElements": 6,
                                        "totalPages": 1,
                                        "first": true,
                                        "size": 10,
                                        "number": 0,
                                        "sort": {
                                            "empty": true,
                                            "sorted": false,
                                            "unsorted": true
                                        },
                                        "numberOfElements": 6,
                                        "empty": false
                                    }
                                """))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: Parâmetro está mal formatado ou inválido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de JSON inválido",
                                    value = """
                            {
                                "timestamp": "2024-10-17T22:00:00.123456Z",
                                "status": 400,
                                "error": "Parâmetro mal formatado",
                                "message": "Parâmetro está mal formatado ou inválido",
                                "path": "/inventory/movement"
                            }
                            """))),
    })
    @Parameter(description = "Número da página (0...N)", name = "page", example = "0", required = false)
    @Parameter(description = "Quantidade de elementos por página", name = "size", example = "10", required = false)
    @Parameter(description = "Critério de ordenação", name = "sort", example = "asc", required = false)
    ResponseEntity<Page<InventoryMovementDTO>> findMovement(Pageable pageable);

    @Operation(summary = "Abastece o estoque do produto por ID", description = "Abastece o estoque do produto especificado pelo ID.",
            requestBody = @RequestBody(description = "Quantidade do produto que entrou em estoque", required = true,
                    content = @Content(
                            schema = @Schema(implementation = InventoryDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Abastecimento do Estoque de Produtos",
                                    value = """
                            {
                              "quantity": 80
                            }
                            """))))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto abastecido com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InventoryDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de abastecimento de Produto no Estoque",
                                    value = """
                            {
                                "id": 2,
                                "productId": 2,
                                "quantity": 110,
                                "outputQuantity": 0,
                                "updateTime": "2024-10-22T00:08:03.995971"
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
                            "path": "/users"
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de produto não encontrado",
                                    value = """
                            {
                                "timestamp": "2024-10-11T03:40:44.363563073Z",
                                "status": 404,
                                "error": "Produto não encontrado.",
                                "path": "inventory/products/2"
                            }
                            """)))
    })
    ResponseEntity<InventoryDTO> replenishStock(@Parameter(description = "ID do produto a ser atualizado", example = "1", required = true) Long id,
                                                @RequestBody InventoryDTO inventoryDTO);
}
