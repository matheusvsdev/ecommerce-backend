# Ecommerce Backend

Este projeto de e-commerce foi desenvolvido para estudo e para compor meu portfólio. Ele abrange toda a lógica e API, incluindo:

- **Usuários**: Gerenciamento de clientes e administradores, com envio de e-mail na criação de usuários.
- **Produtos**: Cadastro, visualização e gerenciamento de produtos.
- **Carrinho de Compras**: Funcionalidade para adicionar, remover e visualizar itens no carrinho.
- **Endereços**: Gerenciamento de endereços de entrega.
- **Frete e Entregas**: Cálculo de frete e gerenciamento de datas de entrega.
- **Pedidos**: Funcionalidade para efetuar e gerenciar pedidos, com envio de e-mail de confirmação ao realizar um pedido.
- **Controle de Estoque**: O sistema realiza a diminuição do estoque a cada pedido. Caso o estoque de um produto chegue a zero, ele não estará mais disponível para pedidos até que haja uma nova entrada no sistema. Também há controle de movimentação de estoque, registrando entradas e saídas, a quantidade que entrou, a quantidade disponível em estoque e horários das transações.
- **Integração com API de Pagamento**: Implementação com Stripe para processamento de pagamentos.
- **Segurança**: Implementação de segurança com OAuth2 e BCrypt.
- **Documentação com Swagger**: A API está documentada com o Swagger, permitindo a interação direta com os endpoints pelo navegador.

O projeto está em andamento, e toda colaboração é bem-vinda!

### Exemplo de Consulta Personalizada no Repositório

Abaixo está um exemplo de como utilizamos a anotação `@Query` para criar uma consulta personalizada no repositório de produtos. Essa consulta permite buscar produtos com base em categorias e em uma string de pesquisa, realizando a filtragem de forma eficiente.
```
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = """
            SELECT tb_product.id, tb_product.img, tb_product.name, tb_product.description, tb_product.price
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND LOWER(tb_product.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """, countQuery = """
            SELECT COUNT(DISTINCT tb_product.id)
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND LOWER(tb_product.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);
}
```
- **@Query:** Utiliza-se a anotação @Query para definir uma consulta SQL nativa, permitindo a busca de produtos de forma flexível.**
- **Parâmetros:**
  
  - **:categoryIds:** filtra produtos por uma lista de IDs de categorias. Se for NULL, essa condição é ignorada.
  
  - **:name:** faz uma busca no nome do produto que não é sensível a maiúsculas e minúsculas, permitindo a pesquisa parcial.
  - **Paginação:** O método retorna uma Page<ProductProjection>, possibilitando a implementação de paginação para uma melhor experiência do usuário ao navegar pelos produtos.
  
## Documentação da API com Swagger

Esta aplicação utiliza o Swagger para fornecer uma interface de documentação e testes da API.

### Acessando a Documentação da API
- **Swagger UI: http://54.233.15.78:8080/ecommerce-backend/swagger-ui.html**

A documentação do Swagger oferece uma visão detalhada dos endpoints disponíveis, dos parâmetros que eles aceitam e das respostas esperadas, tudo em uma interface gráfica acessível diretamente pelo navegador.

#### Exemplo de Requisição ####

- **Endpoint:** /users/{id}
- **Parâmetros:** 
  - id: ID do usuário que você deseja buscar.

A resposta esperada para um usuário existente seria algo como:
    
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

Em caso de erro, por exemplo, se o usuário não for encontrado, a API retornará:

    {
      "status": 404,
      "error": "Not Found",
      "message": "Usuário com o ID 1 não encontrado",
      "path": "/users/1"
    }

### Tecnologias de Documentação Utilizadas

- **Swagger:** Para documentação automática e testes da API.
- **Springdoc OpenAPI:** Integração com Spring Boot para gerar a documentação automaticamente.

## Processo de Deploy na AWS EC2

O deploy desta aplicação foi realizado utilizando a infraestrutura da AWS EC2. Aqui está um resumo do processo:

- **Criação da Instância EC2:** Criei uma instância na AWS EC2 usando o Amazon Linux 2.
- **Instalação do Java 21:** Instalei o Amazon Corretto 21 na instância para rodar o aplicativo Java.
- **Transferência do Arquivo .jar para a Instância:** Usei o comando scp para transferir o arquivo .jar gerado localmente para a instância EC2.
- **Execução da Aplicação:** Rodei a aplicação usando o comando nohup para manter o processo rodando em segundo plano:

      nohup java -jar /home/ec2-user/ecommerce-backend-0.0.1-SNAPSHOT.jar > log.out 2>&1 &

### Acessar a Aplicação

A aplicação pode ser acessada pelo seguinte endereço no Postman:

    http://54.233.15.78:8080

Basta completar com os endpoints para testar.

Se você quiser acessar a documentação do Swagger para testar os endpoints, use o seguinte link:

    http://54.233.15.78:8080/ecommerce-backend/swagger-ui.html

## Tecnologias Utilizadas no Deploy

- **Amazon EC2:** Para hospedar a aplicação.
- **Amazon Corretto (Java 21):** Ambiente de execução Java utilizado na instância.
- **AWS CLI:** Para configuração de portas e gerenciamento da instância.
- **SSH e SCP:** Para acessar a instância EC2 e transferir arquivos.

## Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Security** (OAuth2)
- **BCrypt**
- **Banco de Dados**: H2
- **Gerenciador de Dependências**: Maven

## Pré-requisitos

- **JDK**: 17 ou superior
- **Maven**: Certifique-se de que o Maven está instalado e configurado corretamente.

## Configuração do Ambiente

### Variáveis de Ambiente

Antes de executar o projeto, você precisará configurar as seguintes variáveis de ambiente:

1. **Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo**:

   ```plaintext
   SPRING_APPLICATION_NAME=ecommerce-backend
   SPRING_PROFILES_ACTIVE=test

   CLIENT_ID=your_client_id_here
   CLIENT_SECRET=your_client_secret_here

   JWT_DURATION=86400

   CORS_ORIGINS=http://localhost:3000,http://localhost:5173

   EMAIL_HOST=smtp.gmail.com
   EMAIL_PORT=587
   EMAIL_USERNAME=your_email@gmail.com
   EMAIL_PASSWORD=your_email_password

   PASSWORD_RECOVER_TOKEN_MINUTES=30
   PASSWORD_RECOVER_URI=http://localhost:5173/recover-password/

   STRIPE_SECRET_KEY=your_stripe_secret_key_here

2. **Substitua os valores acima pelos seus próprios,** garantindo que todas as credenciais e chaves sejam inseridas corretamente.

# Contribuição

Se você deseja colaborar com o projeto, fique à vontade para abrir uma issue ou um pull request. Todas as sugestões e melhorias são bem-vindas!
