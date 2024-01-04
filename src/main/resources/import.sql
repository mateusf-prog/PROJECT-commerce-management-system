INSERT INTO tb_category(name) VALUES ('Livros');
INSERT INTO tb_category(name) VALUES ('Eletrônicos');
INSERT INTO tb_category(name) VALUES ('Roupas');

INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Computador', 5500.00, 500, 2);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Monitor', 1500.00, 1000, 2);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Teclado', 100.00, 2000, 2);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('TV', 3500.00, 700, 2);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Harry Potter', 80.00, 50, 1);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Pai Rico Pai Pobre', 50.00, 100, 1);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('O Poder do Hábito', 75.00, 100, 1);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Camiseta', 40.00, 150, 3);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Calça', 100.00, 500, 3);
INSERT INTO tb_product(name, price, quantity, category_id) VALUES ('Short', 70.00, 200, 3);

INSERT INTO tb_customer(name, cpf, email, phone_number, birthdate, address) VALUES('Benedita Amanda Baptista', '17487085031', 'benedita_baptista@life.com', '8325825896', '1999-01-01', 'Rua Antônio Gama, 523, Expedicionários, João Pessoa, PB, 58041-110');
INSERT INTO tb_customer(name, cpf, email, phone_number, birthdate, address) VALUES('Vanessa Jennifer Alves', '75143595088', 'vanessa_jennifer_alves@metalplasma.com.br', '3137399604', '1948-01-01', 'Rua Três, 627, Novo Riacho, Contagem, MG, 32280-640');
INSERT INTO tb_customer(name, cpf, email, phone_number, birthdate, address) VALUES('Márcio Sérgio Pedro Assis', '08529116372', 'marciosergioassis@andressamelo.com.br', '8136475596', '1975-01-01', 'Rua Papueira, 852, Centro, Jaboatão dos Guararapes, PE, 54090-570');
INSERT INTO tb_customer(name, cpf, email, phone_number, birthdate, address) VALUES('Jorge Nathan Barros', '67895765094', 'jorge.nathan.barros@novotempo.com', '6135116754', '1947-01-02', 'Quadra Central Conjunto C Bloco C, 454, Sobradinho, Brasília, DF, 73010-650');
INSERT INTO tb_customer(name, cpf, email, phone_number, birthdate, address) VALUES('Elias Yuri Bernardes', '86190621740', 'elias_yuri_bernardes@fcfar.unesp.br', '2129378175', '1980-01-01', 'Rua Leôncio Correia, 987, Leblon, Rio de Janeiro, RJ, 22450-120');

INSERT INTO tb_order(date, customer_id, total_value, status) VALUES(TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', '75143595088', 11000.0, 0);
INSERT INTO tb_order(date, customer_id, total_value, status) VALUES(TIMESTAMP WITH TIME ZONE '2020-03-23T15:00:00Z', '67895765094', 300.0, 3);
INSERT INTO tb_order(date, customer_id, total_value, status) VALUES(TIMESTAMP WITH TIME ZONE '2018-09-25T16:00:00Z', '86190621740', 14000.0, 2);

INSERT INTO tb_order_item (order_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO tb_order_item (order_id, product_id, quantity) VALUES (2, 3, 3);
INSERT INTO tb_order_item (order_id, product_id, quantity) VALUES (3, 4, 4);