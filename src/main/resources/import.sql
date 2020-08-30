insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 90.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk tuk Comida Indiana', 15, 2);

insert into permissao (nome, descricao) values ('consultar', 'permição para consultar');
insert into permissao (nome, descricao) values ('escrever', 'permição para criar e alterar');
insert into permissao (nome, descricao) values ('excluir', 'permição para excluir');

insert into estado (nome) values ('Minas Gerais');
insert into estado (nome) values ('São Paulo');
