
create table regular_expression
(
    id    serial not null
        primary key,
    regex varchar(255) not null
);

alter table regular_expression
    owner to admin;

alter table glossaries
DROP column regex;

alter table glossaries
add regex integer not null
        constraint regex_id_constraint
            references regular_expression;







