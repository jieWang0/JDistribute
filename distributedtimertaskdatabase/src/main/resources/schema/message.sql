
CREATE TABLE IF NOT EXISTS `message` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(1024) DEFAULT NULL COMMENT '部门名称',
  `userName` varchar(1024) DEFAULT NULL COMMENT '员工名称',
  `old` int(32) DEFAULT NULL COMMENT '开始数据',
  `new` int(32) DEFAULT NULL COMMENT '最新数据',
  `result` int(32) DEFAULT NULL COMMENT '结果数据',
  `createTime` DATE DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `sharding` (
  `id` int(32) NOT NULL  COMMENT '分片id',
  `names` varchar(1024) DEFAULT NULL COMMENT '部门名集合',
  PRIMARY KEY (`id`)
);