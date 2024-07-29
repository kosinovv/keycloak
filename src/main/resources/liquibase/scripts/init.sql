create table users
(
    id          integer     not null,
    username    varchar(20) not null,
    longname    varchar(100),
    correctdate date
);

comment on table users is 'Пользователи системы';

comment on column users.id is 'Идентификатор пользователя';

comment on column users.username is 'Кодовое имя пользователя';

comment on column users.longname is 'Полное имя пользователя';

comment on column users.correctdate is 'Дата создания/коррекции';

alter table users
    owner to postgres;

