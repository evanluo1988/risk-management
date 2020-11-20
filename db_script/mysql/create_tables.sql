DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission`
(
    `id`            BIGINT NOT NULL,
    `perm_name`     varchar(50)   COLLATE utf8mb4_bin  NOT NULL COMMENT '权限名称',
    `perm_describe` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限描述',
    `create_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  	timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_by` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
	`update_by` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;