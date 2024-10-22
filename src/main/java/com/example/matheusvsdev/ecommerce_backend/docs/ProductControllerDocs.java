package com.example.matheusvsdev.ecommerce_backend.docs;

import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.projection.ProductProjection;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @Operation(summary = "Lista todos os produtos paginados", description = "Retorna uma lista paginada de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Busca Paginada de Produtos",
                                    value = """
                                    {
                                             "content": [
                                                 {
                                                     "id": 1,
                                                     "name": "Box Livros Harry Potter Scholastic - Castelo",
                                                     "description": "Esse é o box de Harry Potter mais vendido do Brasil. A caixa vem com sete livros e dois marcadores especiais.",
                                                     "img": "https://imageurl.com/harrypotter",
                                                     "price": 289.9,
                                                     "categories": [
                                                         {
                                                             "id": 1,
                                                             "name": "Livros"
                                                         }
                                                     ],
                                                     "available": true,
                                                     "inventory": {
                                                         "id": 1,
                                                         "productId": 1,
                                                         "quantity": 30,
                                                         "outputQuantity": 0,
                                                         "updateTime": "2024-10-18T13:28:24.268293"
                                                     }
                                                 },
                                                 {
                                                     "id": 2,
                                                     "name": "O Senhor dos Anéis - Volume Único",
                                                     "description": "O épico de fantasia mais famoso de todos os tempos em uma única edição.",
                                                     "img": "https://imageurl.com/senhoraneis",
                                                     "price": 199.9,
                                                     "categories": [
                                                         {
                                                             "id": 1,
                                                             "name": "Livros"
                                                         }
                                                     ],
                                                     "available": true,
                                                     "inventory": {
                                                         "id": 2,
                                                         "productId": 2,
                                                         "quantity": 30,
                                                         "outputQuantity": 0,
                                                         "updateTime": "2024-10-18T13:28:24.268741"
                                                     }
                                                 },
                                                 {
                                                     "id": 3,
                                                     "name": "1984 - George Orwell",
                                                     "description": "Uma obra clássica de ficção distópica.",
                                                     "img": "https://imageurl.com/1984",
                                                     "price": 39.9,
                                                     "categories": [
                                                         {
                                                             "id": 1,
                                                             "name": "Livros"
                                                         }
                                                     ],
                                                     "available": true,
                                                     "inventory": {
                                                         "id": 3,
                                                         "productId": 3,
                                                         "quantity": 30,
                                                         "outputQuantity": 0,
                                                         "updateTime": "2024-10-18T13:28:24.268984"
                                                     }
                                                 },
                                                 {
                                                     "id": 4,
                                                     "name": "O Código Da Vinci - Dan Brown",
                                                     "description": "Um thriller eletrizante com mistérios e segredos históricos.",
                                                     "img": "https://imageurl.com/codigodavinci",
                                                     "price": 49.9,
                                                     "categories": [
                                                         {
                                                             "id": 1,
                                                             "name": "Livros"
                                                         }
                                                     ],
                                                     "available": true,
                                                     "inventory": {
                                                         "id": 4,
                                                         "productId": 4,
                                                         "quantity": 30,
                                                         "outputQuantity": 0,
                                                         "updateTime": "2024-10-18T13:28:24.26923"
                                                     }
                                                 },
                                                 {
                                                     "id": 5,
                                                     "name": "A Revolução dos Bichos - George Orwell",
                                                     "description": "Uma fábula distópica sobre poder e política.",
                                                     "img": "https://imageurl.com/revolucaobichos",
                                                     "price": 29.9,
                                                     "categories": [
                                                         {
                                                             "id": 1,
                                                             "name": "Livros"
                                                         }
                                                     ],
                                                     "available": true,
                                                     "inventory": {
                                                         "id": 5,
                                                         "productId": 5,
                                                         "quantity": 30,
                                                         "outputQuantity": 0,
                                                         "updateTime": "2024-10-18T13:28:24.269467"
                                                     }
                                                 }
                                             ],
                                             "pageable": {
                                                 "pageNumber": 0,
                                                 "pageSize": 5,
                                                 "sort": {
                                                     "empty": true,
                                                     "sorted": false,
                                                     "unsorted": true
                                                 },
                                                 "offset": 0,
                                                 "paged": true,
                                                 "unpaged": false
                                             },
                                             "last": false,
                                             "totalPages": 7,
                                             "totalElements": 32,
                                             "first": true,
                                             "size": 5,
                                             "number": 0,
                                             "sort": {
                                                 "empty": true,
                                                 "sorted": false,
                                                 "unsorted": true
                                             },
                                             "numberOfElements": 5,
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
                                "path": "/products/search"
                            }
                            """))),
    })
    @Parameter(description = "Número da página (0...N)", name = "page", example = "0", required = false)
    @Parameter(description = "Quantidade de elementos por página", name = "size", example = "10", required = false)
    @Parameter(description = "Critério de ordenação", name = "sort", example = "asc", required = false)
    ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable);

    @Operation(summary = "Busca um produto por ID", description = "Retorna os detalhes de um produto específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Produto",
                                    value = """
                            {
                                "id": 13,
                                "name": "Smart TV LG 55 OLED",
                                "description": "TV 4K com tecnologia OLED para cores mais vivas.",
                                "img": "https://imageurl.com/lg55oled",
                                "price": 4599.90,
                                "categories": [
                                    {
                                        "id": 3,
                                        "name": "Eletronicos"
                                    }
                                ],
                                "available": true,
                                "inventory": {
                                    "id": 3,
                                    "productId": 13,
                                    "quantity": 30,
                                    "outputQuantity": 0,
                                    "updateTime": "2024-10-06T23:45:19.771959"
                                }
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
                                "path": "/products/1"
                            }
                            """)))
    })
    ResponseEntity<ProductDTO> findById(
            @Parameter(description = "ID do produto", example = "1", required = true) Long id);

    @Operation(summary = "Busca customizada paginada de produtos, por categoria, nome", description = "Retorna uma lista customizada de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Busca Paginada Customizada de Produtos",
                                    value = """
                                    {
                                    "content": [
                                        {
                                            "name": "Box Livros Harry Potter Scholastic - Castelo",
                                            "id": 1,
                                            "description": "Esse é o box de Harry Potter mais vendido do Brasil. A caixa vem com sete livros e dois marcadores especiais.",
                                            "price": 289.9,
                                            "img": "https://imageurl.com/harrypotter"
                                        },
                                        {
                                            "name": "Xbox Series X",
                                            "id": 9,
                                            "description": "Console de última geração da Microsoft.",
                                            "price": 4499.9,
                                            "img": "https://imageurl.com/xboxseriesx"
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
                                        "paged": true,
                                        "unpaged": false
                                    },
                                    "last": true,
                                    "totalPages": 1,
                                    "totalElements": 3,
                                    "first": true,
                                    "size": 10,
                                    "number": 0,
                                    "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                    },
                                    "numberOfElements": 3,
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
                                "path": "/products/search"
                            }
                            """))),
    })
    @Parameter(description = "IDs das categorias dos produtos para filtragem", name = "categoryIds", required = false)
    @Parameter(description = "Nome do produto para busca parcial", name = "name", required = false, example = "box")
    @Parameter(description = "Número da página (0...N)", name = "page", example = "0", required = false)
    @Parameter(description = "Quantidade de elementos por página", name = "size", example = "10", required = false)
    @Parameter(description = "Critério de ordenação", name = "sort", example = "asc", required = false)
    ResponseEntity<Page<ProductProjection>> searchProducts(@RequestParam(required = false) List<Long> categoryIds,
                                                    @RequestParam(required = false) String name,Pageable pageable);

    @Operation(summary = "Atualiza um produto por ID", description = "Atualiza as informações do produto especificado pelo ID.",
            requestBody = @RequestBody(description = "Detalhes do produto a serem atualizados", required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Atualização de Produtos",
                                    value = """
                            {
                              "description": "The new generation PS5 video game",
                              "name": "PS5 Plus",
                              "img": "http://ps5.com",
                              "price": 3500.0,
                              "categories": [
                                {
                                    "id": 2
                                }
                              ]
                            }
                            """))))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de atualização de dados do Produto",
                                    value = """
                            {
                                 "id": 2,
                                 "name": "PS5 Plus",
                                 "description": "The new generation PS5 video game",
                                 "img": "http://ps5.com",
                                 "price": 1.0,
                                 "categories": [
                                     {
                                         "id": 1,
                                         "name": "Livros"
                                     }
                                 ],
                                 "available": true,
                                 "inventory": {
                                     "id": 2,
                                     "productId": 2,
                                     "quantity": 30,
                                     "outputQuantity": 0,
                                     "updateTime": "2024-10-21T17:42:07.614303"
                                 }
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
                                "path": "/products/2"
                            }
                            """))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: Já existe um produto cadastrado com esse nome",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArgumentAlreadyExistsException.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Conflito",
                                    value = """
                            {
                                "timestamp": "2024-10-21T20:50:29.264907Z",
                                "status": 409,
                                "error": "Já existe produto cadastrado com esse nome",
                                "path": "/products"
                            }
                            """)))
    })
    ResponseEntity<ProductDTO> update(
            @Parameter(description = "ID do produto a ser atualizado", example = "1", required = true) Long id,
            @Valid @RequestBody ProductDTO dto);

    @Operation(summary = "Deleta um produto por ID", description = "Permite a exclusão de um produto especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204 No Content", description = "Produto deletado com sucesso."),
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
            @ApiResponse(responseCode = "409", description = "Produto associado a outros registros.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de produto associado a outros registros",
                                    value = """
                            {
                                "timestamp": "2024-10-11T03:40:44.363563073Z",
                                "status": "409",
                                "error": "Falha de integridade referencial.",
                                "path": "/products/1"
                            }
                            """)))
    })
    ResponseEntity<Void> delete(@Parameter(description = "ID do Produto a ser deletado", example = "3", required = true) Long id);
}
