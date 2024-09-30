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

O projeto está em andamento, e toda colaboração é bem-vinda!

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
