    drop table if exists ef_user_role;

    drop table if exists ef_role;

    drop table if exists ef_user;

    create table ef_role (
        id bigint generated by default as identity,
    	name varchar(255) not null unique,
    	permissions varchar(255),
        primary key (id)
    );

    create table ef_user (
       	id bigint generated by default as identity,
        login_name varchar(255) not null unique,
        name varchar(64),
        password varchar(255),
        salt varchar(64),
        email varchar(128),
        status varchar(32),
        primary key (id)
    );

    create table ef_user_role (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );
    