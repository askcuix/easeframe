
    alter table dws_user_role 
        drop foreign key dws_user_role_user_fk;

    alter table dws_user_role 
        drop foreign key dws_user_role_role_fk;

    drop table if exists dws_role;

    drop table if exists dws_user;

    drop table if exists dws_user_role;

    create table dws_role (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table dws_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table dws_user_role (
        user_id bigint not null,
        role_id bigint not null
    ) ENGINE=InnoDB;

    alter table dws_user_role 
        add constraint dws_user_role_user_fk 
        foreign key (user_id) 
        references dws_user (id);

    alter table dws_user_role 
        add constraint dws_user_role_role_fk 
        foreign key (role_id) 
        references dws_role (id);
