
CREATE TABLE IF NOT EXISTS `message` (
  `id` varchar(255) NOT NULL  COMMENT '主键id',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `createTime` DATE DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `sharding` (
  `id` int(32) NOT NULL  COMMENT '分片id',
  `names` varchar(1024) DEFAULT NULL COMMENT '集团名集合',
  PRIMARY KEY (`id`)
);