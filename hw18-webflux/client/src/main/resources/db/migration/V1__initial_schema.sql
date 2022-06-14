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
