
CREATE TABLE IF NOT EXISTS `message` (
  `id` varchar(255) NOT NULL  COMMENT '主键id',
  `topic` varchar(255) DEFAULT NULL COMMENT '主题',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `createTime` BIGINT DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);