-- Опрос
create table survey
(
    id                bigserial    not null primary key,
    name              varchar(200) not null,
    date_time_created timestamp    not null,
    user_created      varchar(200) not null
);
