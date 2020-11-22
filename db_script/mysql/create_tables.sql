DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions`
(
    `id`            BIGINT NOT NULL,
    `perm_name`     varchar(50)   COLLATE utf8mb4_bin  NOT NULL COMMENT '权限名称',
    `perm_describe` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限描述',
	`enable`        varchar(1)   NOT NULL DEFAULT 'Y' COMMENT '是否禁用：Y为正常，N为禁用',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
   
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles`
(
    `id`            BIGINT NOT NULL,
    `role_name`     varchar(50)   COLLATE utf8mb4_bin  NOT NULL COMMENT '角色名称',
    `role_describe` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色描述',
	`enable`        varchar(1)   NOT NULL DEFAULT 'Y' COMMENT '是否禁用：Y为正常，N为禁用',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;



DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users`
(
    `id`            BIGINT NOT NULL,
    `user_name`     varchar(50)   COLLATE utf8mb4_bin  NOT NULL COMMENT '用户名',
    `password`      varchar(2000) COLLATE utf8mb4_bin  NOT NULL COMMENT '密码',
	`enable`        varchar(1)   NOT NULL DEFAULT 'Y' COMMENT '是否禁用：Y为正常，N为禁用',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
	
	
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE IF NOT EXISTS `roles_permissions`
(
    `id`            BIGINT NOT NULL,
    `role_id`       BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
	
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles`
(
    `id`            BIGINT NOT NULL,
    `user_id`       BIGINT NOT NULL COMMENT '用户ID',
    `role_id`       BIGINT NOT NULL COMMENT '角色ID',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
	
DROP TABLE IF EXISTS `menus`;
CREATE TABLE IF NOT EXISTS `menus`
(
    `id`            BIGINT NOT NULL,
	`parent_id`     BIGINT DEFAULT NULL COMMENT '父级菜单',
    `menu_name`     varchar(50)  COLLATE utf8mb4_bin  NOT NULL COMMENT '菜单名称',
	`menu_describe` varchar(200)  COLLATE utf8mb4_bin  NOT NULL COMMENT '菜单描述',
    `menu_url`      varchar(200) COLLATE utf8mb4_bin  NOT NULL COMMENT '菜单URL',
	`menu_type`     varchar(50)  COLLATE utf8mb4_bin  NOT NULL COMMENT '菜单类型',
	`menu_level`    int          DEFAULT 0 COMMENT '菜单级别',
	`enable`        varchar(1)   NOT NULL DEFAULT 'Y' COMMENT '是否禁用：Y为正常，N为禁用',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
	

DROP TABLE IF EXISTS `menus_permissions`;
CREATE TABLE IF NOT EXISTS `menus_permissions`
(
    `id`            BIGINT NOT NULL,
	`menu_id`       BIGINT NOT NULL COMMENT '菜单ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

