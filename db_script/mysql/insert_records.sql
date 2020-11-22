INSERT INTO `permissions` (`id`, `perm_name`, `perm_describe`, `enable`,`create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (100, 'perm.list','权限列表','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (101, 'perm.view','查看权限','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (102, 'perm.add','新增权限','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (103, 'perm.update','更改权限','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (104, 'perm.delete','删除权限','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (200, 'user.list','用户列表','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (201, 'user.view','查看用户','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (202, 'user.add','新增用户','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (203, 'user.update','更改用户','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (204, 'user.delete','删除用户','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');
			 
INSERT INTO `roles` (`id`, `role_name`, `role_describe`, `enable`,`create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, 'ADMIN_ROLE','管理员','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');
			 
INSERT INTO `roles_permissions` (`id`, `role_id`, `permission_id`, `create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, 1, 100, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (2, 1, 101, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (3, 1, 102, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (4, 1, 103, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (5, 1, 104, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (6, 1, 200, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (7, 1, 201, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (8, 1, 202, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (9, 1, 203, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
	   (10, 1, 204, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');
	   
INSERT INTO `users` (`id`, `user_name`, `password`, `enable`,`create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, 'admin','123456','Y','2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');

INSERT INTO `users_roles` (`id`, `user_id`, `role_id`, `create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, 1, 1,'2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');

INSERT INTO `menus` (`id`, `parent_id`, `menu_name`,`menu_describe`, `menu_url`, `menu_type`, `menu_level`,`enable`,`create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, null, 'USER_LIST', '用户列表', '/users/list', 'MENU', 1, 'Y', '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (2, 1, 'USER_VIEW', '查询用户', '/users/view', 'BUTTON', 0, 'Y', '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (3, 1, 'USER_ADD', '新增用户', '/users/add', 'BUTTON', 0, 'Y', '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
		(4, 1, 'USER_UPDATE', '编辑用户', '/users/update', 'BUTTON', 0, 'Y', '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
		(5, 1, 'USER_DEL', '删除用户', '/users/del', 'BUTTON', 0, 'Y', '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');
			 
	   
INSERT INTO `menus_permissions` (`id`, `menu_id`, `permission_id`,`create_time`, `update_time`,`create_by`,`update_by`) 
VALUES (1, 1, 200, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
       (2, 2, 201, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
		(3, 3, 202, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
		(4, 4, 203, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin'),
		(5, 5, 204, '2020-11-23 00:00:01','2020-11-23 00:00:01','admin','admin');