CREATE DATABASE IF NOT EXISTS `springtransactional`;

CREATE TABLE `notable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(200) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='名人表';



TRUNCATE TABLE notable;
