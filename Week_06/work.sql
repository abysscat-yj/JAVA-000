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


CREATE TABLE `product_spu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `spu_unique_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Spu 全局唯一id',
  `brand_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '品牌id',
  `category_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '类目id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `origin_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '原价 单位分',
  `price` bigint(20) NOT NULL DEFAULT '0' COMMENT '现价 单位分',
  `subtitle` varchar(1024) NOT NULL DEFAULT '' COMMENT '子标题',
  `poster_normal` varchar(2048) NOT NULL COMMENT '主图小图',
  `poster_hd` varchar(2048) NOT NULL COMMENT '主图-高清',
  `images` text COMMENT '轮播图组',
  `description` longtext COMMENT '描述',
  `label` varchar(255) NOT NULL COMMENT '标签',
  `channel` varchar(45) NOT NULL DEFAULT 'SJH' COMMENT '录入渠道',
  `uc_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'uc ID / 商家',
  `shop_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '店铺 ID',
  `phone_number` varchar(45) NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '3' COMMENT '状态 1 已发布，2 审核通过，3 草稿，4 审核拒绝，5审核中',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '2' COMMENT '0初始化状态，1上架， 2下架',
  `version` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '版本',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `ex_tags` varchar(64) NOT NULL DEFAULT ',0,' COMMENT '价格审核状态：0，未审核、1，待审核、2，审核通过、3，审核失败、4，审核通过且降价',
  PRIMARY KEY (`id`),
  UNIQUE KEY `spu_unique_id_UNIQUE` (`spu_unique_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品spu表'


CREATE TABLE `product_sku_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `sku_unique_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Sku 全局唯一id',
  `spu_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'spu全局唯一id',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '商品名称',
  `image` varchar(2048) NOT NULL DEFAULT '' COMMENT '主图',
  `stock` bigint(20) NOT NULL DEFAULT '0' COMMENT '库存',
  `price` bigint(20) NOT NULL DEFAULT '0' COMMENT '原价',
  `total_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '总价（预约金场合使用）',
  `settlement_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '计算价格',
  `price_unit` varchar(255) NOT NULL DEFAULT '' COMMENT '价格单位',
  `specification_option_ids` varchar(255) NOT NULL DEFAULT '' COMMENT '规格选项id',
  `detail` longtext COMMENT '详情描述',
  `status` tinyint(4) NOT NULL DEFAULT '3' COMMENT '状态 1 已发布，2 审核通过，3 草稿，4 审核拒绝，5审核中',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0初始化状态，1上架， 2下架',
  `sale_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '促销状态：-1结束，0 无促销，1 进行中，2未开始 ',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `shop_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '店铺 ID',
  `sale_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '售价 单位(分)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_sku` (`sku_unique_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品sku表'


CREATE TABLE `goods_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '订单id',
  `phone` varchar(20) NOT NULL DEFAULT '0' COMMENT '电话号码',
  `goods_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `pay_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付返回的 uid',
  `lemon_user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `goods_title` varchar(512) NOT NULL DEFAULT '' COMMENT '商品标题',
  `benefit_info` varchar(2000) DEFAULT '' COMMENT '优惠信息',
  `goods_snapshot` text NOT NULL COMMENT '快照',
  `order_state` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态.',
  `platform` varchar(20) NOT NULL DEFAULT '' COMMENT '下单平台',
  `source` varchar(255) NOT NULL DEFAULT '' COMMENT '来源',
  `pay_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '支付平台类型',
  `count` int(8) unsigned NOT NULL DEFAULT '1' COMMENT '购买数',
  `pay_time` datetime DEFAULT NULL COMMENT '新建时间timestamp',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `refund_succ_time` datetime DEFAULT NULL COMMENT ' 退款成功时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `verify_time` datetime DEFAULT NULL COMMENT '核销时间',
  `sell_price` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '售价  单位：分',
  `pay_price` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '实付金额  单位：分',
  `cancel_reason` varchar(1000) NOT NULL DEFAULT '' COMMENT '取消原因',
  `remark` varchar(1000) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '2018-01-01 00:00:00' COMMENT '新建时间timestamp',
  `update_time` datetime NOT NULL DEFAULT '2018-01-01 00:00:00' COMMENT '修改时间timestamp',
  `is_del` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表'







