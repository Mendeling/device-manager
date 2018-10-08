/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.6.40-log : Database - u_admin_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`u_admin_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `u_admin_db`;

/*Table structure for table `t_admin_opera` */

DROP TABLE IF EXISTS `t_admin_opera`;

CREATE TABLE `t_admin_opera` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `url` varchar(256) DEFAULT NULL COMMENT '请求地址',
  `opera_type` varchar(256) DEFAULT NULL COMMENT '操作类型：menu；菜单；add：增肌按钮；delete：删除；update：修改；all：全部查询；one：查询单个；upload：上传',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='后台菜单';

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` int(1) DEFAULT NULL COMMENT '区分角色类型（0：系统管理员：1：其他）',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台角色';

/*Table structure for table `t_admin_role_opera` */

DROP TABLE IF EXISTS `t_admin_role_opera`;

CREATE TABLE `t_admin_role_opera` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` bigint(11) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `oper_id` varchar(32) DEFAULT NULL COMMENT '操作id',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色菜单';

/*Table structure for table `t_admin_user_role` */

DROP TABLE IF EXISTS `t_admin_user_role`;

CREATE TABLE `t_admin_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71460 DEFAULT CHARSET=utf8 COMMENT='后台用户';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
