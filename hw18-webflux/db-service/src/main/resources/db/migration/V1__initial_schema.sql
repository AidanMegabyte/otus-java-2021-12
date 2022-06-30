-- Клиент
create table client
(
    id   bigserial   not null primary key,
    name varchar(50) not null
);

-- Адрес
create table address
(
    id        bigserial   not null primary key,
    street    varchar(50) not null,
    client_id bigint      not null references client (id) unique
);

-- Номер телефона
create table phone
(
    id        bigserial   not null primary key,
    number    varchar(12) not null,
    client_id bigint      not null references client (id)
);

-- Наполняем данными
insert into client (name) values ('Вагон Героев');
insert into address (street, client_id) select 'улица Сезам', max(id) from client;
insert into phone (number, client_id) select '12-23-34', max(id) from client;

insert into client (name) values ('Тояма Токанава');
insert into address (street, client_id) select 'улица Вязов', max(id) from client;
insert into phone (number, client_id) select '23-34-45', max(id) from client;
insert into phone (number, client_id) select '34-45-56', max(id) from client;

insert into client (name) values ('Бздашек Западловский');
insert into address (street, client_id) select 'улица Разбитых фонарей', max(id) from client;
insert into phone (number, client_id) select '45-56-67', max(id) from client;
insert into phone (number, client_id) select '56-67-78', max(id) from client;
insert into phone (number, client_id) select '67-78-89', max(id) from client;
