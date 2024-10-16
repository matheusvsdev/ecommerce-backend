INSERT INTO tb_user (first_name, last_name, birth_date, cpf, phone, email, password, active) VALUES ('Matheus', 'Viccari', '1997-03-15', '721.838.222.11', '82991668033', 'test1@gmail.com', '$2a$10$hPMeOCmAfe3aT1P3mD11AOBfA.aGemQIdKsySHevGbbJL1on1Bi1i', true);
INSERT INTO tb_user (first_name, last_name, birth_date, cpf, phone, email, password, active) VALUES ('José', 'Vilar', '1985-07-02', '101.479.321.09', '82991366013', 'test2@outlook.com', '$2a$10$hPMeOCmAfe3aT1P3mD11AOBfA.aGemQIdKsySHevGbbJL1on1Bi1i', true);
INSERT INTO tb_user (first_name, last_name, birth_date, cpf, phone, email, password, active) VALUES ('Aline', 'Pinosa', '1998-05-10', '231.170.872.33', '82991668033', 'test3@gmail.com', '$2a$10$hPMeOCmAfe3aT1P3mD11AOBfA.aGemQIdKsySHevGbbJL1on1Bi1i', true);

INSERT INTO tb_address (cep, state, city, neighborhood, street, number, complement, client_id) VALUES ('57309610', 9, 'Arapiraca', 'Massaranduba', 'Avenida Amélia Nunes Correia', '1092', 'Condomínio Pedro Tertuliano', 1);
INSERT INTO tb_address (cep, state, city, neighborhood, street, number, complement, client_id) VALUES ('57077000', 0, 'São Paulo', 'Morumbi', 'Avenida Morumbi', '230', 'Próximo ao Estádio de Futebol', 2);
INSERT INTO tb_address (cep, state, city, neighborhood, street, number, complement, client_id) VALUES ('57011001',23, 'Manaus', 'Dom Pedro', 'Rua João Bosco', '737', 'Próximo ao posto de gasolina', 3);

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO tb_category (name) VALUES ('Livros');
INSERT INTO tb_category (name) VALUES ('Games e Consoles');
INSERT INTO tb_category (name) VALUES ('Eletronicos');
INSERT INTO tb_category (name) VALUES ('Eletrodomesticos');
INSERT INTO tb_category (name) VALUES ('Brinquedos');
INSERT INTO tb_category (name) VALUES ('Esporte e Fitness');

INSERT INTO tb_product (name, description, img, price, available) VALUES ('Box Livros Harry Potter Scholastic - Castelo', 'Esse é o box de Harry Potter mais vendido do Brasil. A caixa vem com sete livros e dois marcadores especiais.', 'https://imageurl.com/harrypotter', 289.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('O Senhor dos Anéis - Volume Único', 'O épico de fantasia mais famoso de todos os tempos em uma única edição.', 'https://imageurl.com/senhoraneis', 199.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('1984 - George Orwell', 'Uma obra clássica de ficção distópica.', 'https://imageurl.com/1984', 39.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('O Código Da Vinci - Dan Brown', 'Um thriller eletrizante com mistérios e segredos históricos.', 'https://imageurl.com/codigodavinci', 49.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('A Revolução dos Bichos - George Orwell', 'Uma fábula distópica sobre poder e política.', 'https://imageurl.com/revolucaobichos', 29.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Demons Souls - PlayStation 5', 'Remake de um clássico da PlayStation, Demon’s Souls.', 'https://imageurl.com/demonsouls', 269.10, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('The Last of Us Part II - PlayStation 4', 'Aclamado jogo de aventura e sobrevivência.', 'https://imageurl.com/lastofus', 199.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Nintendo Switch - Console', 'Console híbrido da Nintendo para jogos portáteis e de mesa.', 'https://imageurl.com/switch', 2299.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Xbox Series X', 'Console de última geração da Microsoft.', 'https://imageurl.com/xboxseriesx', 4499.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('FIFA 24 - PlayStation 5', 'O mais recente lançamento do simulador de futebol mais popular do mundo.', 'https://imageurl.com/fifa24', 299.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('iPhone 14 Pro Max', 'Smartphone de última geração da Apple.', 'https://imageurl.com/iphone14', 8499.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Samsung Galaxy S23', 'Smartphone com tecnologia de ponta e câmera avançada.', 'https://imageurl.com/galaxys23', 5999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Smart TV LG 55" OLED', 'TV 4K com tecnologia OLED para cores mais vivas.', 'https://imageurl.com/lg55oled', 4599.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Apple Watch Series 8', 'Relógio inteligente com monitoramento de saúde.', 'https://imageurl.com/applewatch8', 2999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Kindle Paperwhite', 'Leitor de livros digitais com tela antirreflexo.', 'https://imageurl.com/kindle', 499.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Geladeira Brastemp Frost Free 400L', 'Geladeira com tecnologia Frost Free e compartimentos inteligentes.', 'https://imageurl.com/geladeirabrastemp', 3999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Máquina de Lavar Electrolux 12kg', 'Máquina de lavar roupas com 12kg de capacidade.', 'https://imageurl.com/lavadoraelectrolux', 2999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Microondas Panasonic 30L', 'Microondas com diversas funções e capacidade de 30 litros.', 'https://imageurl.com/microondaspanasonic', 399.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Cafeteira Nespresso Inissia', 'Cafeteira para cápsulas de café expresso.', 'https://imageurl.com/nespresso', 299.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Aspirador de Pó Philco', 'Aspirador de pó com filtro HEPA e potência de 1600W.', 'https://imageurl.com/aspiradorphilco', 499.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('LEGO City - Caminhão de Bombeiros', 'Conjunto LEGO para crianças montarem um caminhão de bombeiros.', 'https://imageurl.com/legobombeiros', 149.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Barbie Fashionista', 'Boneca Barbie com vários acessórios de moda.', 'https://imageurl.com/barbiefashionista', 79.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Hot Wheels - Pista Looping', 'Pista de carrinhos com looping e acessórios radicais.', 'https://imageurl.com/hotwheels', 99.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('PlayStation VR2', 'Headset de realidade virtual para PlayStation.', 'https://imageurl.com/psvr2', 2999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Quebra-cabeça 1000 peças - Paisagens', 'Quebra-cabeça com belas paisagens de 1000 peças.', 'https://imageurl.com/quebracabeca', 49.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Bicicleta Ergométrica', 'Bicicleta ergométrica com painel digital e regulagem de resistência.', 'https://imageurl.com/bicicletaergometrica', 1999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Esteira Elétrica Smart', 'Esteira elétrica com monitoramento de batimentos e conectividade Bluetooth.', 'https://imageurl.com/esteira', 2999.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Halteres Ajustáveis - 20kg', 'Par de halteres ajustáveis para treinos de musculação.', 'https://imageurl.com/halteres', 499.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Tapete de Yoga Antiderrapante', 'Tapete de yoga emborrachado para práticas seguras.', 'https://imageurl.com/tapeteyoga', 79.90, true);
INSERT INTO tb_product (name, description, img, price, available) VALUES ('Smartwatch Garmin Forerunner', 'Relógio esportivo com GPS integrado para corrida.', 'https://imageurl.com/garminforerunner', 1199.90, true);

INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (1, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (2, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (3, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (4, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (5, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (6, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (7, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (8, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (9, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (10, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (11, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (12, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (13, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (14, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (15, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (16, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (17, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (18, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (19, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (20, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (21, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (22, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (23, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (24, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (25, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (26, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (27, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (28, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (29, 30, CURRENT_TIMESTAMP, 0, true);
INSERT INTO tb_inventory (product_id, quantity, update_time, output_quantity, available) VALUES (30, 30, CURRENT_TIMESTAMP, 0, true);

INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (4, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (5, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (6, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (7, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (8, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (8, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (9, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (9, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (10, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (11,3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (12,3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (13,3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (14,3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (15,3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (16, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (17, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (18, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (19, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (20, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (21, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (22, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (23, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (24, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (25, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (26, 6);
INSERT INTO tb_product_category (product_id, category_id) VALUES (27, 6);
INSERT INTO tb_product_category (product_id, category_id) VALUES (28, 6);
INSERT INTO tb_product_category (product_id, category_id) VALUES (29, 6);
INSERT INTO tb_product_category (product_id, category_id) VALUES (30, 6);

INSERT INTO tb_order (freight_cost, status, sub_total, total, address_id, moment, user_id) VALUES (0.0, 2, 8999.8, 8999.8, 1, '2024-02-12T15:32:27', 1);
INSERT INTO tb_order (freight_cost, status, sub_total, total, address_id, moment, user_id) VALUES (29.06, 2, 289.9, 318.96, 2, '2024-02-27T19:02:11', 2);
INSERT INTO tb_order (freight_cost, status, sub_total, total, address_id, moment, user_id) VALUES (0.0, 3, 199.9, 199.9, 1, '2024-02-03T09:01:38', 1);
INSERT INTO tb_order (freight_cost, status, sub_total, total, address_id, moment, user_id) VALUES (0.0, 2, 1199.9, 1199.9, 2, '2024-07-23T10:12:47', 2);

INSERT INTO tb_shipping (freight_cost, delivery_time, order_id, order_update, status) VALUES (0.0, '2024-02-27T08:02:49', 1, '2024-02-27T08:02:49', 'ENTREGUE');
INSERT INTO tb_shipping (freight_cost, delivery_time, order_id, order_update, status) VALUES (29.06, '2024-03-14T09:32:19', 2, '2024-03-14T09:32:19', 'ENTREGUE');
INSERT INTO tb_shipping (freight_cost, delivery_time, order_id, order_update, status) VALUES (0.0, '2024-02-18T13:52:49', 3, '2024-02-18T13:52:49', 'ENTREGA_NAO_EFETUADA');
INSERT INTO tb_shipping (freight_cost, delivery_time, order_id, order_update, status) VALUES (29.06, '2024-08-07T17:12:49', 4, '2024-08-07T17:12:49', 'ENTREGUE');

INSERT INTO tb_payment (amount, payment_method, status, order_id, payment_date, token, transaction_id) VALUES (8999.8, 1, 1, 1, '2024-02-12T15:32:27', 'tok_visa', 'ch_3Q7IOoKTisW4hrZA1bPsSVdA');
INSERT INTO tb_payment (amount, payment_method, status, order_id, payment_date, token, transaction_id) VALUES (318.96, 1, 1, 2, '2024-02-27T19:02:11', 'tok_visa', 'ch_3Q7IOoKTisW8irZA1bPsSVdA');
INSERT INTO tb_payment (amount, payment_method, status, order_id, payment_date, token, transaction_id) VALUES (199.9, 1, 1, 3, '2024-02-03T09:01:38', 'tok_visa', 'ch_8Q7IO7KTisW8irZA1bPsSVdA');
INSERT INTO tb_payment (amount, payment_method, status, order_id, payment_date, token, transaction_id) VALUES (1199.9, 1, 1, 4, '2024-07-23T10:12:47', 'tok_visa', 'ch_8Q7aO7CTisW3irZA1bPsSVdA');

INSERT INTO tb_order_item (price, quantity, order_id, product_id) VALUES (499.9, 1, 1, 28);
INSERT INTO tb_order_item (price, quantity, order_id, product_id) VALUES (8499.9, 1, 1, 11);
INSERT INTO tb_order_item (price, quantity, order_id, product_id) VALUES (289.9, 1, 2, 1);
INSERT INTO tb_order_item (price, quantity, order_id, product_id) VALUES (199.9, 1, 3, 7);
INSERT INTO tb_order_item (price, quantity, order_id, product_id) VALUES (1199.9, 1, 4, 30);

