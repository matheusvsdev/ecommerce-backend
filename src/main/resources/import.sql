INSERT INTO client (first_name, last_name, birth_date, cpf, phone, email, password) VALUES ('Matheus', 'Valdevino', '1997-03-15', '121.478.222.11', '82991668033', 'matheusvaldevino1997@gmail.com', '$2a$10$hPMeOCmAfe3aT1P3mD11AOBfA.aGemQIdKsySHevGbbJL1on1Bi1i');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
