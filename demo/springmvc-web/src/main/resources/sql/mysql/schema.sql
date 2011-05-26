
    alter table dsmvc_group_permission 
        drop foreign key dsmvc_group_permission_fk;

    alter table dsmvc_group_permission 
        drop foreign key dsmvc_permit_permission_fk;

    alter table dsmvc_user_group 
        drop foreign key dsmvc_user_group_group_fk;

    alter table dsmvc_user_group 
        drop foreign key dsmvc_user_group_user_fk;

    drop table if exists dsmvc_permission;

    drop table if exists dsmvc_group;

    drop table if exists dsmvc_group_permission;

    drop table if exists dsmvc_user;

    drop table if exists dsmvc_user_group;

    create table dsmvc_permission (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        display_name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table dsmvc_group (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table dsmvc_group_permission (
        group_id bigint not null,
        permission_id bigint not null
    ) ENGINE=InnoDB;

    create table dsmvc_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table dsmvc_user_group (
        user_id bigint not null,
        group_id bigint not null
    ) ENGINE=InnoDB;

    alter table dsmvc_group_permission 
        add constraint dsmvc_group_permission_fk 
        foreign key (group_id) 
        references dsmvc_group (id);

    alter table dsmvc_group_permission 
        add constraint dsmvc_permit_permission_fk 
        foreign key (permission_id) 
        references dsmvc_permission (id);

    alter table dsmvc_user_group 
        add constraint dsmvc_user_group_group_fk 
        foreign key (group_id) 
        references dsmvc_group (id);

    alter table dsmvc_user_group 
        add constraint dsmvc_user_group_user_fk 
        foreign key (user_id) 
        references dsmvc_user (id);
