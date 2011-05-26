    drop table if exists ef_post;

    drop table if exists ef_role;

    drop table if exists ef_user;

    drop table if exists ef_user_role;
    
    drop table if exists ef_log;

    create table ef_post (
        id varchar(16),
    	dtype varchar(32) not null,
        content clob,
        modify_time timestamp,
        title varchar(255) not null,
        user_id varchar(16),
        subject_id varchar(16),
        primary key (id)
    );

    create table ef_role (
        id varchar(16),
    	name varchar(255) not null unique,
        primary key (id)
    );

    create table ef_user (
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

    create table ef_user_role (
        user_id varchar(16) not null,
        role_id varchar(16) not null,
        primary key (user_id, role_id)
    );
    
    create table ef_log (
    	thread_name varchar(255),
    	logger_name varchar(255),
    	log_time timestamp,
    	level varchar(20),
    	message varchar(255)
    );

    alter table ef_post 
        add constraint ef_post_subject_id_fk 
        foreign key (subject_id) 
        references ef_post;

    alter table ef_post 
        add constraint ef_post_user_id_fk 
        foreign key (user_id) 
        references ef_user;

    alter table ef_user_role 
        add constraint ef_user_role_uid_fk 
        foreign key (user_id) 
        references ef_user;

    alter table ef_user_role 
        add constraint ef_user_role_rid_pk 
        foreign key (role_id) 
        references ef_role;
