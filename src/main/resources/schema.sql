create table if not exists user (
    id          bigint       not null   primary key,
    email       varchar(255) not null   unique,
    username    varchar(20)  not null,
    create_time bigint       not null,
    update_time bigint       not null
);

create table if not exists login_info (
    id           bigint         not null    primary key,
    login_method enum ('EMAIL') not null,
    login_key    varchar(255)   not null,
    password     varchar(255)   null,
    user_id      bigint         not null,
    create_time  bigint         not null,
    update_time  bigint         not null,
    unique(login_method, login_key)
);

create table if not exists session (
    id          bigint       not null   primary key,
    user_id     bigint       not null   unique,
    token       varchar(255) not null   unique,
    is_block    tinyint(1)   not null,
    expire_time bigint       not null,
    create_time bigint       not null
);

create table if not exists article (
    id          bigint       not null   primary key,
    writer_id   bigint       not null,
    title       varchar(50)  not null,
    content     text         not null,
    create_time bigint       not null,
    update_time bigint       not null
);
