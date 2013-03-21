insert into ef_user (id, login_name, name, email, password, salt, status) values(1,'admin','Administrator','admin@easeframe.com','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','enabled');
insert into ef_user (id, login_name, name, email, password, salt, status) values(2,'user','Chris','askcuix@gmail.com','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');
insert into ef_user (id, login_name, name, email, password, salt, status) values(3,'test','Tester','test@easeframe.com','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');

insert into ef_role (id, name, permissions) values(1,'Admin','user:view,user:edit');
insert into ef_role (id, name, permissions) values(2,'User','user:view');

insert into ef_user_role (user_id, role_id) values(1,1);
insert into ef_user_role (user_id, role_id) values(1,2);
insert into ef_user_role (user_id, role_id) values(2,2);
insert into ef_user_role (user_id, role_id) values(3,2);