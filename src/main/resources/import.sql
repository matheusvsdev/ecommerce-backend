INSERT INTO tb_user (first_name, last_name, birth_date, cpf, phone, email, password) VALUES ('Matheus', 'Valdevino', '1997-03-15', '121.478.222.11', '82991668033', 'matheusvaldevino1997@gmail.com', '$2a$10$hPMeOCmAfe3aT1P3mD11AOBfA.aGemQIdKsySHevGbbJL1on1Bi1i');

INSERT INTO tb_address (cep, state, city, neighborhood, street, number, complement, client_id) VALUES ('57038008', 9, 'Maceió', 'Cruz das Almas', 'Lot. Santo Onofre, Rua B', '23', 'Próximo a UPA Jacintinho', 1);

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO categories (name) VALUES ('Livros');
INSERT INTO categories (name) VALUES ('Games e Consoles');

INSERT INTO products (name, description, img, quantity, price) VALUES ('Box Livros Harry Potter Scholastic - Castelo', 'Esse é o box de Harry Potter mais vendido do Brasil. A caixa vem com sete livros e dois marcadores especiais.', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.com.br%2FCole%25C3%25A7%25C3%25A3o-Harry-Potter-7-volumes%2Fdp%2F8532512941&psig=AOvVaw0Z9I5zyXi9i0bzozZRhwOx&ust=1726713046801000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMCY9b-5y4gDFQAAAAAdAAAAABAE', 30, 289.90);
INSERT INTO products (name, description, img, quantity, price) VALUES ('Demons Souls - PlayStation 5', 'Da Bluepoint Games, chega o remake de um clássico da PlayStation, Demon’s Souls. Inteiramente refeito do zero e aprimorado com maestria, esse remake introduz o horror de uma terra obscura, fantasiosa e carregada de névoa para uma nova geração de jogadores.', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.com.br%2FPlayStation-jogo-Demons-Souls-5%2Fdp%2FB08JDZ3223&psig=AOvVaw11EWKlYLSoMNn2UDk8ukD0&ust=1726714650579000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMCwwLy_y4gDFQAAAAAdAAAAABAE', 100, 269.10);

INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);
