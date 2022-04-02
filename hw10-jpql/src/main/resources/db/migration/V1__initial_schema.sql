-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);

create sequence address_sequence start with 1 increment by 1;

create table address
(
    id     bigint not null primary key,
    street varchar(50)
);

create sequence phone_sequence start with 1 increment by 1;

create table phone
(
    id     bigint not null primary key,
    number varchar(12)
);
