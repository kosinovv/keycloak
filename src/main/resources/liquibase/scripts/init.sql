create schema kosinov;

alter schema kosinov owner to postgres;

create table kosinov.users
(
    id          integer     not null,
    username    varchar(20) not null,
    longname    varchar(100),
    correctdate date
);

comment on table kosinov.users is 'Пользователи системы';

comment on column kosinov.users.id is 'Идентификатор пользователя';

comment on column kosinov.users.username is 'Кодовое имя пользователя';

comment on column kosinov.users.longname is 'Полное имя пользователя';

comment on column kosinov.users.correctdate is 'Дата создания/коррекции';

alter table kosinov.users
    owner to postgres;

create sequence kosinov.users_sequence
    as integer;

alter sequence kosinov.users_sequence
    owner to postgres;