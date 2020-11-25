CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) NOT NULL COMMENT '姓名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(32) NOT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `id_number` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT NULL COMMENT ' 性别，1-男 2-女',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '头像图片地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除，0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表'


