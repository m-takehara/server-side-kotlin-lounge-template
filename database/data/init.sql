create schema demo_schema;

create table demo_schema.users
(
    id varchar not null
        constraint users_pk
            primary key
);

alter table demo_schema.users
    owner to demo;

create unique index users_id_uindex
    on demo_schema.users (id);

create table demo_schema.accounts
(
    user_id    varchar not null
        constraint accounts_users_id_fk
            references demo_schema.users,
    account_id varchar not null,
    service    varchar not null
);

alter table demo_schema.accounts
    owner to demo;
