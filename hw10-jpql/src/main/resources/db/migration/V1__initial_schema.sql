-- Клиент
create sequence client_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);

-- Адрес
create sequence address_sequence start with 1 increment by 1;

create table address
(
    id     bigint not null primary key,
    street varchar(50)
);

-- Номер телефона
create sequence phone_sequence start with 1 increment by 1;

create table phone
(
    id     bigint not null primary key,
    number varchar(12)
);
