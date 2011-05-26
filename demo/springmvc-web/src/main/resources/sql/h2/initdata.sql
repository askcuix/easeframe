-- initialize database
-- init authority
insert into dsmvc_authority(id, name, permission) values(1, '����û�', 'user:view');
insert into dsmvc_authority(id, name, permission) values(2, '�޸��û�', 'user:manage');
insert into dsmvc_authority(id, name, permission) values(3, '�����ɫ', 'role:view');
insert into dsmvc_authority(id, name, permission) values(4, '�޸Ľ�ɫ', 'role:manage');

-- init role
insert into dsmvc_role(id, name) values(1, '����Ա');
insert into dsmvc_role(id, name) values(2, '�û�');

-- init role_authority
insert into dsmvc_role_authority(role_id, authority_id) values(1, 1);
insert into dsmvc_role_authority(role_id, authority_id) values(1, 2);
insert into dsmvc_role_authority(role_id, authority_id) values(1, 3);
insert into dsmvc_role_authority(role_id, authority_id) values(1, 4);
insert into dsmvc_role_authority(role_id, authority_id) values(2, 1);
insert into dsmvc_role_authority(role_id, authority_id) values(2, 3);

-- init user
insert into dsmvc_user(id, email, login_name, name, password) values(1, 'admin@test.com', 'admin', 'Admin', 'admin');
insert into dsmvc_user(id, email, login_name, name, password) values(2, 'user@test.com', 'user', 'User', 'user');

-- init user_role
insert into dsmvc_user_role(user_id, role_id) values(1, 1);
insert into dsmvc_user_role(user_id, role_id) values(1, 2);
insert into dsmvc_user_role(user_id, role_id) values(2, 2);