/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : manager

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-05-12 12:11:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auid` varchar(100) DEFAULT NULL,
  `receivername` varchar(20) DEFAULT NULL,
  `receiveraddress` varchar(50) DEFAULT NULL,
  `receiverphone` char(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', '0001', '张三', '西安1号', '15854672389');
INSERT INTO `address` VALUES ('2', '0001', '张小三', '西安2号', '18923732451');
INSERT INTO `address` VALUES ('3', '0002', '李四', '宝鸡1号', '13267835170');
INSERT INTO `address` VALUES ('4', '0002', '李小四', '宝鸡2号', '15267341974');
INSERT INTO `address` VALUES ('5', '0003', '王五', '西安3号', '15226395481');

-- ----------------------------
-- Table structure for balance
-- ----------------------------
DROP TABLE IF EXISTS `balance`;
CREATE TABLE `balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auid` varchar(100) DEFAULT NULL,
  `balance` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of balance
-- ----------------------------
INSERT INTO `balance` VALUES ('8', '0001', '17270');
INSERT INTO `balance` VALUES ('9', '0002', '399');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auid` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `rank` varchar(10) DEFAULT NULL,
  `customername` varchar(50) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `age` tinyint(3) unsigned DEFAULT NULL,
  `idcard` char(18) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `qq` char(12) DEFAULT NULL,
  `weixin` varchar(20) DEFAULT NULL,
  `state` char(2) NOT NULL DEFAULT '注册',
  `isupgrade` char(3) NOT NULL DEFAULT '否',
  `createtime` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '0001', '593690386', '特约', '张三', '男', '27', '421281199001021236', '西安1号', '15263984521', '932967963', 'djgs4534', '注册', '否', '2017-05-12 12:01:31', '第一个用户');
INSERT INTO `customer` VALUES ('2', '0002', '34593067', 'VIP', '李四', '男', '26', '421281199110029875', '西安3号', '15698741256', '8239669', '15698741256', '注册', '否', '2017-05-12 11:48:59', '第二个用户');

-- ----------------------------
-- Table structure for manageruser
-- ----------------------------
DROP TABLE IF EXISTS `manageruser`;
CREATE TABLE `manageruser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `reallyname` varchar(255) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `typeuser` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manageruser
-- ----------------------------
INSERT INTO `manageruser` VALUES ('1', 'admin', '刘备', '898199c1e468b4583e15776a72b747b1ed0b37461730317d', '超级用户');
INSERT INTO `manageruser` VALUES ('2', 'admin1', '关羽', 'd75c1c60f27be6792c092b7e83bc30b6e366830b15d30929', '普通用户');

-- ----------------------------
-- Table structure for orderid
-- ----------------------------
DROP TABLE IF EXISTS `orderid`;
CREATE TABLE `orderid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` varchar(20) DEFAULT NULL,
  `counter` int(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderid
-- ----------------------------
INSERT INTO `orderid` VALUES ('1', '20170512', '4');

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auid` varchar(100) DEFAULT NULL,
  `rank` varchar(10) DEFAULT NULL,
  `deliverytime` datetime DEFAULT NULL,
  `deliveryname` varchar(20) DEFAULT NULL,
  `orderid` varchar(50) DEFAULT NULL,
  `productid` varchar(50) DEFAULT NULL,
  `productnum` int(11) DEFAULT NULL,
  `productprice` int(11) unsigned DEFAULT NULL,
  `productsum` int(11) DEFAULT NULL,
  `receivername` varchar(20) DEFAULT NULL,
  `receiverphone` char(11) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `receiveraddress` varchar(50) DEFAULT NULL,
  `waybillnumber` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES ('2', '0001', 'VIP', '2017-05-12 11:46:57', '关羽', 'YW20170512-0001', '西湖龙井', '3', '100', '300', '张小三', '18923732451', '[中国邮政]', '西安2号', '654756879');
INSERT INTO `orderinfo` VALUES ('3', '0002', 'VIP', '2017-05-12 11:55:11', '关羽', 'YW20170512-0002', '药王茶', '3', '133', '399', '李四', '13267835170', '[中通快递]', '宝鸡1号', '34536546');
INSERT INTO `orderinfo` VALUES ('4', '0001', '特约', '2017-05-12 12:01:27', '关羽', 'YW20170512-0003', '药王茶', '10', '97', '970', '张三', '15854672389', '[中国邮政]', '西安1号', '457586798');
INSERT INTO `orderinfo` VALUES ('5', '0001', '特约', '2017-05-12 12:04:03', '关羽', 'YW20170512-0004', '西湖龙井', '200', '80', '16000', '张三', '15854672389', '[中国邮政]', '西安1号', '735422568');

-- ----------------------------
-- Table structure for producttype
-- ----------------------------
DROP TABLE IF EXISTS `producttype`;
CREATE TABLE `producttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `producttype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producttype
-- ----------------------------
INSERT INTO `producttype` VALUES ('1', '药王茶');
INSERT INTO `producttype` VALUES ('2', '西湖龙井');

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rank` varchar(20) DEFAULT NULL,
  `productnum` int(11) DEFAULT NULL,
  `productprice` int(11) DEFAULT NULL,
  `productSum` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `producttype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES ('1', 'VIP', '3', '133', '399', '2017-05-12 09:02:39', '2017-05-12 09:02:39', '药王茶');
INSERT INTO `rank` VALUES ('2', '特约', '10', '97', '970', '2017-05-12 09:04:01', '2017-05-12 11:23:49', '药王茶');
INSERT INTO `rank` VALUES ('3', '区域', '240', '77', '18480', '2017-05-12 09:06:42', '2017-05-12 09:06:42', '药王茶');
INSERT INTO `rank` VALUES ('4', '总代', '1200', '67', '80400', '2017-05-12 09:10:54', '2017-05-12 09:10:54', '药王茶');
INSERT INTO `rank` VALUES ('5', '联创', '2400', '57', '136800', '2017-05-12 09:11:22', '2017-05-12 09:11:22', '药王茶');
INSERT INTO `rank` VALUES ('6', 'VIP', '3', '100', '300', '2017-05-12 09:11:58', '2017-05-12 09:11:58', '西湖龙井');
INSERT INTO `rank` VALUES ('7', '特约', '12', '80', '960', '2017-05-12 09:12:17', '2017-05-12 11:15:03', '西湖龙井');

-- ----------------------------
-- Table structure for updateinfo
-- ----------------------------
DROP TABLE IF EXISTS `updateinfo`;
CREATE TABLE `updateinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auid` varchar(100) DEFAULT NULL,
  `rank` varchar(10) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `updatereason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of updateinfo
-- ----------------------------
INSERT INTO `updateinfo` VALUES ('1', '0001', 'VIP', '注册', '2017-05-12 11:25:56', '注册');
INSERT INTO `updateinfo` VALUES ('2', '0002', 'VIP', '注册', '2017-05-12 11:48:59', '注册');
INSERT INTO `updateinfo` VALUES ('3', '0001', '特约', '注册', '2017-05-12 12:01:31', '提货量为：10，由VIP升级为特约');
SET FOREIGN_KEY_CHECKS=1;
