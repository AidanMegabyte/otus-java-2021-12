-- Адрес
create sequence address_sequence start with 1 increment by 1;

create table address
(
    id     bigint not null primary key,
    street varchar(50)
);

-- Клиент
create sequence client_sequence start with 1 increment by 1;

create table client
(
    id         bigint not null primary key,
    name       varchar(50),
    address_id bigint references address (id)
);

-- Номер телефона
create sequence phone_sequence start with 1 increment by 1;

create table phone
(
    id        bigint not null primary key,
    number    varchar(12),
    client_id bigint not null references client (id)
);
