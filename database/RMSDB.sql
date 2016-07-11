/*
SQLyog v10.2 
MySQL - 5.6.31 : Database - resourcemanagementdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`resourcemanagementdb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `resourcemanagementdb`;

/*Table structure for table `application` */

DROP TABLE IF EXISTS `application`;

CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自建主键',
  `rid` int(11) NOT NULL COMMENT '申请资产编号',
  `owner` varchar(255) NOT NULL DEFAULT '' COMMENT '资产当前拥有者',
  `receiver` varchar(255) NOT NULL DEFAULT '' COMMENT '资产获得者',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `time` varchar(255) NOT NULL DEFAULT '' COMMENT '申请时间',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '申请类型（0：在库资产分配；1：个人资产转移；2：个人资产归还）',
  `flow` int(11) NOT NULL COMMENT '申请流程',
  `step` int(11) NOT NULL DEFAULT '1' COMMENT '申请所处流程步骤（申请创建时位于流程的第一步）',
  `finished` int(11) NOT NULL DEFAULT '0' COMMENT '申请是否处理结束（0：未结束；1：结束）',
  `refused` int(11) NOT NULL DEFAULT '0' COMMENT '申请是否被拒绝（0：未被拒绝，1：被拒绝）',
  PRIMARY KEY (`id`),
  KEY `application_ibfk_1` (`rid`),
  KEY `application_ibfk_2` (`flow`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `resource` (`id`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`flow`) REFERENCES `flow` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `application_approval` */

DROP TABLE IF EXISTS `application_approval`;

CREATE TABLE `application_approval` (
  `application_id` int(11) NOT NULL COMMENT '申请标识',
  `step` int(11) NOT NULL COMMENT '所处步骤',
  `reviewer` varchar(255) NOT NULL COMMENT '审核人',
  `approve` int(11) NOT NULL COMMENT '是否同意（0：同意；1：不同意）',
  `time` varchar(255) NOT NULL DEFAULT '' COMMENT '审核时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`application_id`,`step`),
  CONSTRAINT `application_approval_ibfk_1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `flow` */

DROP TABLE IF EXISTS `flow`;

CREATE TABLE `flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程标识',
  `name` varchar(255) NOT NULL COMMENT '流程名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `flow_step` */

DROP TABLE IF EXISTS `flow_step`;

CREATE TABLE `flow_step` (
  `flowId` int(11) NOT NULL COMMENT '流程标识',
  `step` int(11) NOT NULL COMMENT '所处步骤',
  `userGroup` int(11) NOT NULL COMMENT '用户群组',
  `remark` varchar(255) DEFAULT '' COMMENT '步骤描述',
  PRIMARY KEY (`flowId`,`step`),
  KEY `userGroup` (`userGroup`),
  CONSTRAINT `flow_step_ibfk_1` FOREIGN KEY (`flowId`) REFERENCES `flow` (`id`),
  CONSTRAINT `flow_step_ibfk_2` FOREIGN KEY (`userGroup`) REFERENCES `user_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rid` int(11) NOT NULL COMMENT '资产标识',
  `owner` varchar(255) DEFAULT '' COMMENT '资产拥有者',
  `startTime` varchar(255) DEFAULT '' COMMENT '开始使用时间',
  `endTime` varchar(255) DEFAULT '' COMMENT '结束使用时间',
  `remark` varchar(255) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `purchase_application` */

DROP TABLE IF EXISTS `purchase_application`;

CREATE TABLE `purchase_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `applicant` varchar(255) NOT NULL COMMENT '申请人',
  `name` varchar(255) NOT NULL COMMENT '申购物品名',
  `number` int(11) DEFAULT '1' COMMENT '申购物品数量',
  `remark` varchar(255) DEFAULT '' COMMENT '备注信息',
  `time` varchar(255) DEFAULT '' COMMENT '申购时间',
  `refused` int(11) DEFAULT '0' COMMENT '申请是否被拒绝（0表示未被拒绝；1表示被拒绝）',
  `status` int(11) DEFAULT '0' COMMENT '申请状态：0：未审核；1：已审核；2：已处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) DEFAULT '' COMMENT '资产名',
  `model` varchar(255) DEFAULT '' COMMENT '资产型号',
  `trackingNo` varchar(255) DEFAULT '' COMMENT '追踪码',
  `IMEI` varchar(255) DEFAULT '' COMMENT '移动设备标识',
  `serialNo` varchar(255) DEFAULT '' COMMENT '设备序列号',
  `entryDate` varchar(255) DEFAULT '' COMMENT '入库日期',
  `owner` varchar(255) DEFAULT '' COMMENT '资产当前拥有者',
  `status` int(11) DEFAULT '0' COMMENT '资产状态：0：正常；1：损坏',
  `remark` varchar(255) DEFAULT '' COMMENT '资产备注',
  `type` int(11) NOT NULL COMMENT '资产类型',
  PRIMARY KEY (`id`),
  KEY `TYPE` (`type`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`type`) REFERENCES `type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `type` */

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键 ',
  `name` varchar(255) NOT NULL COMMENT '类别名称',
  `fatherType` int(11) NOT NULL DEFAULT '0' COMMENT '父类目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `user_group` */

DROP TABLE IF EXISTS `user_group`;

CREATE TABLE `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) NOT NULL COMMENT '用户群组名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
