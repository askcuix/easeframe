
    alter table ds2_group_permission 
        drop foreign key ds2_group_permission_fk;

    alter table ds2_group_permission 
        drop foreign key ds2_permit_permission_fk;

    alter table ds2_user_group 
        drop foreign key ds2_user_group_group_fk;

    alter table ds2_user_group 
        drop foreign key ds2_user_group_user_fk;

    drop table if exists ds2_permission;

    drop table if exists ds2_group;

    drop table if exists ds2_group_permission;

    drop table if exists ds2_user;

    drop table if exists ds2_user_group;

    create table ds2_permission (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        display_name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ds2_group (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ds2_group_permission (
        group_id bigint not null,
        permission_id bigint not null
    ) ENGINE=InnoDB;

    create table ds2_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table ds2_user_group (
        user_id bigint not null,
        group_id bigint not null
    ) ENGINE=InnoDB;

    alter table ds2_group_permission 
        add constraint ds2_group_permission_fk 
        foreign key (group_id) 
        references ds2_group (id);

    alter table ds2_group_permission 
        add constraint ds2_permit_permission_fk 
        foreign key (permission_id) 
        references ds2_permission (id);

    alter table ds2_user_group 
        add constraint ds2_user_group_group_fk 
        foreign key (group_id) 
        references ds2_group (id);

    alter table ds2_user_group 
        add constraint ds2_user_group_user_fk 
        foreign key (user_id) 
        references ds2_user (id);
