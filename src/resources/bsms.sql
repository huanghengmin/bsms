-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.6.13-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 bs 的数据库结构
DROP DATABASE IF EXISTS `bs`;
CREATE DATABASE IF NOT EXISTS `bs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bs`;


-- 导出  表 bs.access_address 结构
DROP TABLE IF EXISTS `access_address`;
CREATE TABLE IF NOT EXISTS `access_address` (
  `url` varchar(50) NOT NULL DEFAULT '',
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.access_address 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `access_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `access_address` ENABLE KEYS */;


-- 导出  表 bs.access_control 结构
DROP TABLE IF EXISTS `access_control`;
CREATE TABLE IF NOT EXISTS `access_control` (
  `status` int(1) NOT NULL DEFAULT '0',
  `control_url` varchar(50) COLLATE utf8_bin NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.access_control 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `access_control` DISABLE KEYS */;
/*!40000 ALTER TABLE `access_control` ENABLE KEYS */;


-- 导出  表 bs.account 结构
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `status` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `depart` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `start_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `end_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `start_hour` int(11) DEFAULT NULL,
  `end_hour` int(11) DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `remote_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mac` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ip_type` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账户表';

-- 正在导出表  bs.account 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `user_name`, `password`, `sex`, `phone`, `created_time`, `modified_time`, `status`, `depart`, `title`, `name`, `email`, `start_ip`, `end_ip`, `start_hour`, `end_hour`, `description`, `remote_ip`, `mac`, `ip_type`) VALUES
	(1, 'admin', 'S8W2gMnH8VWiT9pXRMPQxA==', '男', '0571-88888888', '2010-07-04 13:52:36', '2013-05-07 19:09:56', '有效', '信息中心', '主任', '初始化管理员', 'xiaom@hzih.net', '0.0.0.0', '192.168.254.254', 9, 18, '这是一个默认的初始化管理员信息', '192.168.2.176', '5C-63-BF-1D-72-07', 1),
	(2, 'authadmin', 'S8W2gMnH8VWiT9pXRMPQxA==', '男', '0571-88888888', '2012-04-12 14:22:35', '2013-05-07 19:08:57', '有效', '信息中心', '主任', '授权管理员', 'xiaom@hzih.net', '0.0.0.0', '192.168.200.254', 1, 22, '这是一个默认的授权管理员信息', '', NULL, 1),
	(3, 'configadmin', 'S8W2gMnH8VWiT9pXRMPQxA==', '男', '0571-88888888', '2012-06-12 18:04:01', '2013-05-07 19:09:15', '有效', '信息中心', '主任', '配置管理员', 'xiaom@hzih.net', '0.0.0.0', '192.168.200.254', 9, 21, '这是一个默认的配置管理员信息', '', NULL, 1),
	(4, 'auditadmin', 'S8W2gMnH8VWiT9pXRMPQxA==', '男', '0571-88888888', '2012-07-03 10:19:57', '2013-06-07 10:03:22', '有效', '信息中心', '主任', '审计管理员', 'xiaom@hzih.net', '0.0.0.0', '192.168.200.254', 7, 22, '这是一个默认的审计管理员信息', NULL, NULL, 1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- 导出  表 bs.account_role 结构
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE IF NOT EXISTS `account_role` (
  `account_id` bigint(20) NOT NULL DEFAULT '0',
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `FK410D03481FCE46BD` (`role_id`),
  KEY `FK410D034811351AF7` (`account_id`),
  KEY `FK410D0348D5FE8033` (`role_id`),
  KEY `FK410D03488723E5C1` (`account_id`),
  KEY `FK410D034851BABF58` (`role_id`),
  KEY `FK410D0348BE9C187C` (`account_id`),
  KEY `FK410D0348AE05B062` (`role_id`),
  KEY `FK410D0348F3A41332` (`account_id`),
  KEY `FK410D03481B95B13D` (`role_id`),
  KEY `FK410D0348DC77A077` (`account_id`),
  KEY `FK410D0348C12F0E1A` (`role_id`),
  KEY `FK410D0348CE753A7A` (`account_id`),
  CONSTRAINT `FK410D034811351AF7` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FK410D03481B95B13D` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.account_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` (`account_id`, `role_id`) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;


-- 导出  表 bs.allow_list 结构
DROP TABLE IF EXISTS `allow_list`;
CREATE TABLE IF NOT EXISTS `allow_list` (
  `processName` varchar(50) DEFAULT NULL,
  `processId` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`processId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.allow_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `allow_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `allow_list` ENABLE KEYS */;


-- 导出  表 bs.black_list 结构
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE IF NOT EXISTS `black_list` (
  `url` varchar(255) NOT NULL DEFAULT '',
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  bs.black_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `black_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `black_list` ENABLE KEYS */;


-- 导出  表 bs.business_log 结构
DROP TABLE IF EXISTS `business_log`;
CREATE TABLE IF NOT EXISTS `business_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(10) DEFAULT NULL COMMENT '日志等级',
  `log_time` datetime DEFAULT NULL COMMENT '产生时间',
  `business_name` varchar(60) DEFAULT NULL COMMENT '业务名称',
  `platform_name` varchar(255) DEFAULT NULL COMMENT '平台名称',
  `audit_info` text,
  `source_ip` varchar(255) DEFAULT NULL,
  `source_dest` varchar(255) DEFAULT NULL,
  `dest_ip` varchar(255) DEFAULT NULL,
  `dest_port` varchar(10) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `operation` varchar(100) DEFAULT NULL,
  `business_type` varchar(255) DEFAULT NULL,
  `business_desc` varchar(255) DEFAULT NULL,
  `audit_count` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `source_port` varchar(255) DEFAULT NULL,
  `source_jdbc` varchar(255) DEFAULT NULL,
  `dest_jdbc` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `json_id` int(11) DEFAULT NULL,
  `plugin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `ind_business_audit` (`level`,`business_name`,`log_time`,`platform_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务日志审计表';

-- 正在导出表  bs.business_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `business_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_log` ENABLE KEYS */;


-- 导出  表 bs.ca_permission 结构
DROP TABLE IF EXISTS `ca_permission`;
CREATE TABLE IF NOT EXISTS `ca_permission` (
  `id` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.ca_permission 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `ca_permission` DISABLE KEYS */;
INSERT INTO `ca_permission` (`id`, `url`) VALUES
	(2, '*'),
	(10, '*.jsp'),
	(11, '*.png'),
	(12, '*.jpg'),
	(13, '*RandomCodeCtrl');
/*!40000 ALTER TABLE `ca_permission` ENABLE KEYS */;


-- 导出  表 bs.ca_role 结构
DROP TABLE IF EXISTS `ca_role`;
CREATE TABLE IF NOT EXISTS `ca_role` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.ca_role 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `ca_role` DISABLE KEYS */;
INSERT INTO `ca_role` (`id`, `name`, `description`, `createdTime`, `modifiedTime`, `status`) VALUES
	(1, 'admin', '超级用户', '2013-04-28 15:26:31', '2013-04-28 15:25:03', 1);
/*!40000 ALTER TABLE `ca_role` ENABLE KEYS */;


-- 导出  表 bs.ca_role_permission 结构
DROP TABLE IF EXISTS `ca_role_permission`;
CREATE TABLE IF NOT EXISTS `ca_role_permission` (
  `ca_permission_id` int(11) NOT NULL,
  `ca_role_id` int(11) NOT NULL,
  PRIMARY KEY (`ca_permission_id`,`ca_role_id`),
  KEY `ca_permission_id` (`ca_permission_id`),
  KEY `ca_role_id` (`ca_role_id`),
  KEY `FK729BB5751C714F2` (`ca_permission_id`),
  KEY `FK729BB57D8C5D192` (`ca_role_id`),
  KEY `FK729BB57500B2230` (`ca_permission_id`),
  KEY `FK729BB57A9742550` (`ca_role_id`),
  KEY `FK729BB575840807A` (`ca_permission_id`),
  KEY `FK729BB57E378831A` (`ca_role_id`),
  CONSTRAINT `FK729BB57500B2230` FOREIGN KEY (`ca_permission_id`) REFERENCES `ca_permission` (`id`),
  CONSTRAINT `FK729BB5751C714F2` FOREIGN KEY (`ca_permission_id`) REFERENCES `ca_permission` (`id`),
  CONSTRAINT `FK729BB575840807A` FOREIGN KEY (`ca_permission_id`) REFERENCES `ca_permission` (`id`),
  CONSTRAINT `FK729BB57A9742550` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK729BB57D8C5D192` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK729BB57E378831A` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK_ca_role_permission_ca_permission` FOREIGN KEY (`ca_permission_id`) REFERENCES `ca_permission` (`id`),
  CONSTRAINT `FK_ca_role_permission_ca_role` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.ca_role_permission 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `ca_role_permission` DISABLE KEYS */;
INSERT INTO `ca_role_permission` (`ca_permission_id`, `ca_role_id`) VALUES
	(2, 1);
/*!40000 ALTER TABLE `ca_role_permission` ENABLE KEYS */;


-- 导出  表 bs.ca_user 结构
DROP TABLE IF EXISTS `ca_user`;
CREATE TABLE IF NOT EXISTS `ca_user` (
  `id` int(11) NOT NULL,
  `cn` varchar(50) NOT NULL,
  `serialNumber` varchar(255) DEFAULT NULL,
  `ipAddress` varchar(50) DEFAULT '0.0.0.0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.ca_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ca_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_user` ENABLE KEYS */;


-- 导出  表 bs.ca_user_role 结构
DROP TABLE IF EXISTS `ca_user_role`;
CREATE TABLE IF NOT EXISTS `ca_user_role` (
  `ca_user_id` int(11) NOT NULL,
  `ca_role_id` int(11) NOT NULL,
  PRIMARY KEY (`ca_role_id`,`ca_user_id`),
  KEY `ca_user_id` (`ca_user_id`),
  KEY `ca_role_id` (`ca_role_id`),
  KEY `FK1FD79DC97DF09572` (`ca_user_id`),
  KEY `FK1FD79DC9D8C5D192` (`ca_role_id`),
  KEY `FK1FD79DC94E9EE930` (`ca_user_id`),
  KEY `FK1FD79DC9A9742550` (`ca_role_id`),
  KEY `FK1FD79DC988A346FA` (`ca_user_id`),
  KEY `FK1FD79DC9E378831A` (`ca_role_id`),
  CONSTRAINT `FK1FD79DC94E9EE930` FOREIGN KEY (`ca_user_id`) REFERENCES `ca_user` (`id`),
  CONSTRAINT `FK1FD79DC97DF09572` FOREIGN KEY (`ca_user_id`) REFERENCES `ca_user` (`id`),
  CONSTRAINT `FK1FD79DC988A346FA` FOREIGN KEY (`ca_user_id`) REFERENCES `ca_user` (`id`),
  CONSTRAINT `FK1FD79DC9A9742550` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK1FD79DC9D8C5D192` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK1FD79DC9E378831A` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK_ca_user_role_ca_role` FOREIGN KEY (`ca_role_id`) REFERENCES `ca_role` (`id`),
  CONSTRAINT `FK_ca_user_role_ca_user` FOREIGN KEY (`ca_user_id`) REFERENCES `ca_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.ca_user_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ca_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_user_role` ENABLE KEYS */;


-- 导出  表 bs.his_location 结构
DROP TABLE IF EXISTS `his_location`;
CREATE TABLE IF NOT EXISTS `his_location` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `rauserid` int(11) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rauserid` (`rauserid`),
  KEY `FK65AB21C28FF1B49B` (`rauserid`),
  CONSTRAINT `FK65AB21C28FF1B49B` FOREIGN KEY (`rauserid`) REFERENCES `rauser` (`id`),
  CONSTRAINT `his_location_rauser_key1` FOREIGN KEY (`rauserid`) REFERENCES `rauser` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;

-- 正在导出表  bs.his_location 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `his_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `his_location` ENABLE KEYS */;


-- 导出  表 bs.https 结构
DROP TABLE IF EXISTS `https`;
CREATE TABLE IF NOT EXISTS `https` (
  `manager_ip` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `manager_port` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `proxy_ip` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `proxy_port` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `site_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `protocol` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `id` int(10) NOT NULL DEFAULT '0',
  `site_id_server` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.https 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `https` DISABLE KEYS */;
/*!40000 ALTER TABLE `https` ENABLE KEYS */;


-- 导出  表 bs.permission 结构
DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `ID` bigint(20) NOT NULL DEFAULT '0',
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `SEQ` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.permission 的数据：~36 rows (大约)
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`ID`, `CODE`, `NAME`, `DESCRIPTION`, `PARENT_ID`, `SEQ`) VALUES
	(100, 'TOP_QXGL', '权限管理', NULL, 0, 0),
	(101, 'SECOND_YHGL', '用户管理', NULL, 100, 1),
	(102, 'SECOND_JSGL', '角色管理', NULL, 100, 2),
	(103, 'SECOND_AQCL', '安全策略', NULL, 100, 3),
	(110, 'TOP_WLGL', '网络管理', NULL, 0, 0),
	(111, 'SECOND_JKGL', '接口管理', NULL, 110, 1),
	(112, 'SECOND_LTCS', '连通测试', NULL, 110, 2),
	(113, 'SECOND_LYGL', '路由管理', NULL, 110, 3),
	(114, 'SECOND_PZGL', '安全配置', NULL, 110, 4),
	(120, 'TOP_XTGL', '系统管理', NULL, 0, 0),
	(121, 'SECOND_PTSM', '平台说明', NULL, 120, 1),
	(122, 'SECOND_PTGL', '平台管理', NULL, 120, 2),
	(123, 'SECOND_ZSGL', '证书管理', NULL, 120, 3),
	(124, 'SECOND_RZXZ', '日志下载', NULL, 120, 4),
	(125, 'SECOND_BBSJ', '版本升级', NULL, 120, 5),
	(130, 'TOP_SJGL', '审计管理', NULL, 0, 0),
	(131, 'SECOND_YHRZ', '管理员日志', NULL, 130, 1),
	(140, 'TOP_CSGL', 'C/S服务管理', NULL, 0, 0),
	(141, 'SECOND_CSPZ', '初始化配置', NULL, 140, 1),
	(142, 'SECOND_CSFW', 'C/S服务配置', NULL, 140, 2),
	(150, 'TOP_BSGL', 'B/S服务管理', NULL, 0, 0),
	(151, 'SECOND_BSST', '服务状态', NULL, 150, 1),
	(152, 'SECOND_BSPA', '服务参数', NULL, 150, 2),
	(153, 'SECOND_BSCY', '服务策略', NULL, 150, 3),
	(154, 'SECOND_BSHMD', '黑名单', NULL, 150, 4),
	(155, 'SECOND_BSBMD', '白名单', NULL, 150, 5),
	(156, 'SECOND_BSYH', '终端管理', NULL, 150, 6),
	(157, 'SECOND_BSJS', '终端角色', NULL, 150, 7),
	(158, 'SECOND_BSZY', '终端资源', NULL, 150, 8),
	(160, 'TOP_XTPZ', '系统配置', NULL, 0, 0),
	(161, 'SECOND_JQFW', '鉴权服务器', NULL, 160, 1),
	(162, 'SECOND_DLFW', '代理服务器', NULL, 160, 2),
	(163, 'SECOND_LDAP', 'LDAP配置', NULL, 160, 3),
	(164, 'SECOND_SYSLOG', '日志服务器', NULL, 160, 4),
	(170, 'TOP_JKGL', '监控管理', NULL, 0, 0),
	(171, 'SECOND_ZJJK', '主机监控', NULL, 170, 1);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;


-- 导出  表 bs.power 结构
DROP TABLE IF EXISTS `power`;
CREATE TABLE IF NOT EXISTS `power` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `rights` int(11) DEFAULT '0',
  `resourcename` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.power 的数据：0 rows
/*!40000 ALTER TABLE `power` DISABLE KEYS */;
/*!40000 ALTER TABLE `power` ENABLE KEYS */;


-- 导出  表 bs.processentity 结构
DROP TABLE IF EXISTS `processentity`;
CREATE TABLE IF NOT EXISTS `processentity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `sourceip` varchar(15) COLLATE utf8_bin NOT NULL,
  `sourceport` varchar(11) COLLATE utf8_bin NOT NULL,
  `distip` varchar(15) COLLATE utf8_bin NOT NULL,
  `distport` varchar(11) COLLATE utf8_bin NOT NULL,
  `isrun` tinyint(4) DEFAULT '0',
  `flagrun` tinyint(4) DEFAULT '0',
  `protocol` varchar(4) COLLATE utf8_bin NOT NULL DEFAULT 'tcp',
  `info` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.processentity 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `processentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `processentity` ENABLE KEYS */;


-- 导出  表 bs.resource 结构
DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `description` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.resource 的数据：0 rows
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;


-- 导出  表 bs.resourceip 结构
DROP TABLE IF EXISTS `resourceip`;
CREATE TABLE IF NOT EXISTS `resourceip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceid` int(11) DEFAULT '0',
  `ipaddress` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `subnetmask` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.resourceip 的数据：0 rows
/*!40000 ALTER TABLE `resourceip` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourceip` ENABLE KEYS */;


-- 导出  表 bs.resourceweb 结构
DROP TABLE IF EXISTS `resourceweb`;
CREATE TABLE IF NOT EXISTS `resourceweb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceid` int(11) NOT NULL DEFAULT '0',
  `agreement` varchar(50) COLLATE utf8_bin NOT NULL,
  `ipaddress` varchar(50) COLLATE utf8_bin NOT NULL,
  `port` varchar(50) COLLATE utf8_bin NOT NULL,
  `url` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.resourceweb 的数据：0 rows
/*!40000 ALTER TABLE `resourceweb` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourceweb` ENABLE KEYS */;


-- 导出  表 bs.role 结构
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  bs.role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`, `description`, `createdTime`, `modifiedTime`) VALUES
	(1, '初始化管理员', '初始化管理员', '2010-07-04 15:07:08', '2016-09-18 17:35:18'),
	(2, '授权管理员', '授权管理员', '2012-07-03 10:06:20', '2012-07-03 10:06:20'),
	(3, '配置管理员', '配置管理员', '2012-03-14 12:33:05', '2012-03-14 12:33:05'),
	(4, '审计管理员', '审计管理员', '2012-06-12 18:37:24', '2013-06-07 10:04:10');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- 导出  表 bs.rolemanage 结构
DROP TABLE IF EXISTS `rolemanage`;
CREATE TABLE IF NOT EXISTS `rolemanage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.rolemanage 的数据：0 rows
/*!40000 ALTER TABLE `rolemanage` DISABLE KEYS */;
/*!40000 ALTER TABLE `rolemanage` ENABLE KEYS */;


-- 导出  表 bs.rolemanageandtime 结构
DROP TABLE IF EXISTS `rolemanageandtime`;
CREATE TABLE IF NOT EXISTS `rolemanageandtime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolemanageid` int(11) DEFAULT '0',
  `timetype` int(11) DEFAULT '0',
  `starttime` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.rolemanageandtime 的数据：0 rows
/*!40000 ALTER TABLE `rolemanageandtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `rolemanageandtime` ENABLE KEYS */;


-- 导出  表 bs.roleuser 结构
DROP TABLE IF EXISTS `roleuser`;
CREATE TABLE IF NOT EXISTS `roleuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT '0',
  `userid` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.roleuser 的数据：0 rows
/*!40000 ALTER TABLE `roleuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `roleuser` ENABLE KEYS */;


-- 导出  表 bs.role_permission 结构
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE IF NOT EXISTS `role_permission` (
  `permission_id` bigint(20) NOT NULL DEFAULT '0',
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FKBD40D53851BABF58` (`role_id`),
  KEY `FKBD40D53852A81638` (`permission_id`),
  KEY `FK9C6EC93851BABF58` (`role_id`),
  KEY `FK9C6EC93852A81638` (`permission_id`),
  KEY `FKBD40D53880878851` (`role_id`),
  KEY `FKBD40D5388AAE8071` (`permission_id`),
  KEY `FKBD40D5381FCE46BD` (`role_id`),
  KEY `FKBD40D5384E8FBDDD` (`permission_id`),
  KEY `FKBD40D538D5FE8033` (`role_id`),
  KEY `FKBD40D538461086D3` (`permission_id`),
  KEY `FKBD40D538AE05B062` (`role_id`),
  KEY `FKBD40D5389E3897C2` (`permission_id`),
  KEY `FKBD40D5381B95B13D` (`role_id`),
  KEY `FKBD40D538E0BD485D` (`permission_id`),
  KEY `FKBD40D538C12F0E1A` (`role_id`),
  KEY `FKBD40D53897A2CB7A` (`permission_id`),
  CONSTRAINT `FK9C6EC93851BABF58` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK9C6EC93852A81638` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D5381B95B13D` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D5381FCE46BD` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D538461086D3` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D5384E8FBDDD` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D53851BABF58` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D53852A81638` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D53880878851` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D5388AAE8071` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D53897A2CB7A` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D5389E3897C2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`),
  CONSTRAINT `FKBD40D538AE05B062` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D538C12F0E1A` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D538D5FE8033` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKBD40D538E0BD485D` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bs.role_permission 的数据：~33 rows (大约)
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` (`permission_id`, `role_id`) VALUES
	(100, 1),
	(101, 1),
	(102, 1),
	(103, 1),
	(110, 1),
	(111, 1),
	(112, 1),
	(113, 1),
	(114, 1),
	(120, 1),
	(121, 1),
	(122, 1),
	(123, 1),
	(124, 1),
	(125, 1),
	(130, 1),
	(131, 1),
	(140, 1),
	(141, 1),
	(142, 1),
	(150, 1),
	(151, 1),
	(152, 1),
	(153, 1),
	(154, 1),
	(155, 1),
	(156, 1),
	(157, 1),
	(158, 1),
	(160, 1),
	(164, 1),
	(170, 1),
	(171, 1);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;


-- 导出  表 bs.safe_policy 结构
DROP TABLE IF EXISTS `safe_policy`;
CREATE TABLE IF NOT EXISTS `safe_policy` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `timeout` int(11) DEFAULT NULL,
  `passwordLength` int(11) DEFAULT NULL,
  `errorLimit` int(11) DEFAULT NULL,
  `remoteDisabled` tinyint(1) DEFAULT NULL,
  `macDisabled` tinyint(1) DEFAULT NULL,
  `passwordRules` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `lockTime` int(10) NOT NULL DEFAULT '24' COMMENT '锁定时间(小时)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='安全策略表';

-- 正在导出表  bs.safe_policy 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `safe_policy` DISABLE KEYS */;
INSERT INTO `safe_policy` (`id`, `timeout`, `passwordLength`, `errorLimit`, `remoteDisabled`, `macDisabled`, `passwordRules`, `lockTime`) VALUES
	(1, 600, 0, 3, 0, 0, '^[0-9a-zA-Z!$#%@^&amp;amp;amp;amp;amp;amp;amp;*()~_+]{8,20}$', 1);
/*!40000 ALTER TABLE `safe_policy` ENABLE KEYS */;


-- 导出  表 bs.site 结构
DROP TABLE IF EXISTS `site`;
CREATE TABLE IF NOT EXISTS `site` (
  `id` int(10) NOT NULL,
  `site_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `key_path` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `cert_path` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `site_name` (`site_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.site 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `site` DISABLE KEYS */;
/*!40000 ALTER TABLE `site` ENABLE KEYS */;


-- 导出  表 bs.source 结构
DROP TABLE IF EXISTS `source`;
CREATE TABLE IF NOT EXISTS `source` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `port` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `source_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `source_port` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `proxy_type` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.source 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `source` DISABLE KEYS */;
/*!40000 ALTER TABLE `source` ENABLE KEYS */;


-- 导出  表 bs.sys_log 结构
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE IF NOT EXISTS `sys_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `log_time` datetime DEFAULT NULL COMMENT '产生时间',
  `level` varchar(10) DEFAULT NULL COMMENT '日志等级',
  `audit_module` varchar(40) DEFAULT NULL COMMENT '审计模块',
  `audit_action` varchar(40) DEFAULT NULL COMMENT '审计行为',
  `audit_info` varchar(255) DEFAULT NULL COMMENT '审计内容',
  PRIMARY KEY (`Id`),
  KEY `log_time` (`log_time`,`level`,`audit_module`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志审计表';

-- 正在导出表  bs.sys_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;


-- 导出  表 bs.usermanage 结构
DROP TABLE IF EXISTS `usermanage`;
CREATE TABLE IF NOT EXISTS `usermanage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cacn` varchar(50) COLLATE utf8_bin NOT NULL,
  `province` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `department` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `policestation` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `tel` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `idcard` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  bs.usermanage 的数据：0 rows
/*!40000 ALTER TABLE `usermanage` DISABLE KEYS */;
/*!40000 ALTER TABLE `usermanage` ENABLE KEYS */;


-- 导出  表 bs.user_oper_log 结构
DROP TABLE IF EXISTS `user_oper_log`;
CREATE TABLE IF NOT EXISTS `user_oper_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `log_time` datetime DEFAULT NULL COMMENT '审计时间',
  `level` varchar(10) DEFAULT NULL COMMENT '日志级别',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `audit_module` varchar(255) DEFAULT NULL COMMENT '审计模块',
  `audit_info` varchar(255) DEFAULT NULL COMMENT '审计内容',
  PRIMARY KEY (`Id`),
  KEY `log_time` (`log_time`,`level`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='用户操作审计表';

-- 正在导出表  bs.user_oper_log 的数据：~53 rows (大约)
/*!40000 ALTER TABLE `user_oper_log` DISABLE KEYS */;
INSERT INTO `user_oper_log` (`Id`, `log_time`, `level`, `username`, `audit_module`, `audit_info`) VALUES
	(1, '2016-08-18 15:17:54', 'INFO', 'test', ' 用户登录 ', ' IP地址不允许访问 '),
	(2, '2016-08-18 15:18:00', 'INFO', 'test', ' 用户登录 ', ' IP地址不允许访问 '),
	(3, '2016-08-18 15:18:49', 'INFO', 'test', '用户登录', '用户登录成功'),
	(4, '2016-08-18 15:19:05', 'INFO', 'test', 'accessAddress', '新增白名单失败！fdsfdsfs'),
	(5, '2016-08-18 15:21:56', 'INFO', 'test', 'accessAddress', '新增白名单失败！fdsfdsfdsf'),
	(6, '2016-08-18 15:22:02', 'INFO', 'test', 'accessAddress', '删除记录失败！'),
	(7, '2016-08-18 15:22:06', 'INFO', 'test', 'accessAddress', '删除记录失败！'),
	(8, '2016-08-18 15:22:13', 'INFO', 'test', 'accessAddress', '新增黑名单成功！fdfdsfsd'),
	(9, '2016-08-18 15:22:18', 'INFO', 'test', 'accessAddress', '删除记录失败！'),
	(10, '2016-08-18 15:22:21', 'INFO', 'test', 'accessAddress', '新增黑名单成功！fdsfdsafsd'),
	(11, '2016-08-18 15:22:24', 'INFO', 'test', 'accessAddress', '更新记录失败！'),
	(12, '2016-08-18 15:22:28', 'INFO', 'test', 'accessAddress', '更新记录失败！'),
	(13, '2016-08-18 15:22:33', 'INFO', 'test', 'accessAddress', '删除记录失败！'),
	(14, '2016-08-18 15:22:40', 'INFO', 'test', 'accessAddress', '新增白名单失败！fdsfdsf'),
	(15, '2016-08-18 15:22:48', 'INFO', 'test', 'accessAddress', '更新记录失败！'),
	(16, '2016-08-18 15:23:03', 'INFO', 'test', 'accessAddress', '新增黑名单成功！fdsfdsf'),
	(17, '2016-08-18 15:23:07', 'INFO', 'test', 'accessAddress', '更新记录失败！'),
	(18, '2016-08-18 15:24:23', 'ERROE', 'test', '权限控制', '用户删除资源信息失败'),
	(19, '2016-09-18 17:16:29', 'INFO', 'admin', '用户登录', '用户登录成功'),
	(20, '2016-09-18 17:19:19', 'INFO', 'admin', 'accessAddress', '删除记录失败！'),
	(21, '2016-09-18 17:19:24', 'INFO', 'admin', 'accessAddress', '删除记录失败！'),
	(22, '2016-09-18 17:19:51', 'ERROE', 'admin', '权限控制', '用户删除资源信息失败'),
	(23, '2016-09-18 17:19:54', 'ERROE', 'admin', '权限控制', '用户删除资源信息失败'),
	(24, '2016-09-18 17:19:57', 'ERROE', 'admin', '权限控制', '用户删除资源信息失败'),
	(25, '2016-09-18 17:34:13', 'INFO', 'admin', '用户登录', '用户登录成功'),
	(26, '2016-09-18 17:34:18', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(27, '2016-09-18 17:34:21', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(28, '2016-09-18 17:34:23', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(29, '2016-09-18 17:34:25', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(30, '2016-09-18 17:34:28', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(31, '2016-09-18 17:34:32', 'INFO', 'admin', '安全策略', '用户获取安全策略信息成功'),
	(32, '2016-09-18 17:34:33', 'INFO', 'admin', '用户管理', '用户获取所有角色名成功'),
	(33, '2016-09-18 17:34:33', 'INFO', 'admin', '用户管理', '用户获取所有账号信息成功'),
	(34, '2016-09-18 17:34:33', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(35, '2016-09-18 17:34:55', 'ERROE', 'admin', '角色管理', '用户删除角色信息失败'),
	(36, '2016-09-18 17:34:56', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(37, '2016-09-18 17:34:57', 'INFO', 'admin', '用户管理', '用户获取所有角色名成功'),
	(38, '2016-09-18 17:34:58', 'INFO', 'admin', '用户管理', '用户获取所有账号信息成功'),
	(39, '2016-09-18 17:35:00', 'INFO', 'admin', '用户管理', '用户删除账户test信息成功'),
	(40, '2016-09-18 17:35:01', 'INFO', 'admin', '用户管理', '用户获取所有账号信息成功'),
	(41, '2016-09-18 17:35:02', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(42, '2016-09-18 17:35:04', 'INFO', 'admin', '角色管理', '用户删除角色信息成功'),
	(43, '2016-09-18 17:35:05', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(44, '2016-09-18 17:35:08', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(45, '2016-09-18 17:35:10', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(46, '2016-09-18 17:35:10', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(47, '2016-09-18 17:35:13', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(48, '2016-09-18 17:35:15', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(49, '2016-09-18 17:35:15', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(50, '2016-09-18 17:35:16', 'INFO', 'admin', '角色管理', '用户获取角色权限信息用于修改成功'),
	(51, '2016-09-18 17:35:21', 'INFO', 'admin', '角色管理', '用户更新角色信息成功'),
	(52, '2016-09-18 17:35:22', 'INFO', 'admin', '角色管理', '用户获取角色信息成功'),
	(53, '2016-09-18 17:35:39', 'INFO', 'admin', '用户登录', '用户登录成功');
/*!40000 ALTER TABLE `user_oper_log` ENABLE KEYS */;


-- 导出  表 bs.white_list 结构
DROP TABLE IF EXISTS `white_list`;
CREATE TABLE IF NOT EXISTS `white_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  bs.white_list 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `white_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `white_list` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
