    drop table if exists post;

    drop table if exists role;

    drop table if exists user;

    drop table if exists user_role;
    
    drop table if exists log;

    create table post (
        id varchar(16),
    	dtype varchar(32) not null,
        content clob,
        modify_time timestamp,
        title varchar(255) not null,
        user_id varchar(16),
        subject_id varchar(16),
        primary key (id)
    );

    create table role (
        id varchar(16),
    	name varchar(255) not null unique,
        primary key (id)
    );

    create table users (
        id varchar(16),
    	create_by varchar(255),
        create_time timestamp,
        last_modify_by varchar(255),
        last_modify_time timestamp,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        plain_password varchar(255),
        sha_password varchar(255),
        status varchar(255),
        version integer,
        primary key (id)
    );

    create table user_role (
        user_id varchar(16) not null,
        role_id varchar(16) not null,
        primary key (user_id, role_id)
    );
    
    create table log (
    	thread_name varchar(255),
    	logger_name varchar(255),
    	log_time timestamp,
    	level varchar(20),
    	message varchar(255)
    );

    alter table post 
        add constraint post_subject_id_fk 
        foreign key (subject_id) 
        references post;

    alter table post 
        add constraint post_user_id_fk 
        foreign key (user_id) 
        references users;

    alter table user_role 
        add constraint user_role_uid_fk 
        foreign key (user_id) 
        references users;

    alter table user_role 
        add constraint user_role_rid_pk 
        foreign key (role_id) 
        references role;
