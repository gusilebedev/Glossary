
create table glossaries
(
    regex varchar(255) not null,
    name  varchar(255) not null
        primary key
);

alter table glossaries
    owner to admin;

create table words
(
    glossary_name varchar(255) not null
        constraint glossary_name_constraint
            references glossaries,
    name          varchar(255) not null,
    value         varchar(255) not null,
    primary key (glossary_name, name)
);

alter table words
    owner to admin;




