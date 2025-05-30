/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 90001 (9.0.1)
 Source Host           : localhost:3306
 Source Schema         : imall

 Target Server Type    : MySQL
 Target Server Version : 90001 (9.0.1)
 File Encoding         : 65001

 Date: 14/11/2024 13:58:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `icon` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (1, 'test', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'test@123456.com', '测试账号', NULL, '2018-09-29 13:55:30', '2018-09-29 13:55:39', 1);
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (3, 'admin', '$2a$10$.E1FokumK5GIXWgKlg.Hc.i/0/2.qdAwYFL1zc5QHdyzpXOr38RZO', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'admin@163.com', '系统管理员macro123', '系统管理员', '2018-10-08 13:32:47', '2019-04-20 12:45:16', 1);
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (4, 'macro', '$2a$10$Bx4jZPR7GhEpIQfefDQtVeS58GfT5n6mxs/b4nLLK65eMFa16topa', 'string', 'macro@qq.com', 'macro', 'macro专用', '2019-10-06 15:53:51', '2020-02-03 14:55:55', 1);
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (6, 'productAdmin', '$2a$10$6/.J.p.6Bhn7ic4GfoB5D.pGd7xSiD1a9M6ht6yO0fxzlKJPjRAGm', '', 'product@qq.com', '商品管理员', '只有商品权限', '2020-02-07 16:15:08', NULL, 1);
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (7, 'orderAdmin', '$2a$10$UqEhA9UZXjHHA3B.L9wNG.6aerrBjC6WHTtbv1FdvYPUI.7lkL6E.', NULL, 'order@qq.com', '订单管理员', '只有订单管理权限', '2020-02-07 16:15:50', NULL, 1);
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES (10, 'ceshi', '$2a$10$RaaNo9CC0RSms8mc/gJpCuOWndDT4pHH0u5XgZdAAYFs1Uq4sOPRi', NULL, 'ceshi@qq.com', 'ceshi', NULL, '2020-03-13 16:23:30', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_login_log`;
CREATE TABLE `ums_admin_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_agent` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '浏览器登录类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台用户登录日志表';

-- ----------------------------
-- Records of ums_admin_login_log
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin_login_log` (`id`, `admin_id`, `create_time`, `ip`, `address`, `user_agent`) VALUES (285, 3, '2020-08-24 14:05:21', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` (`id`, `admin_id`, `create_time`, `ip`, `address`, `user_agent`) VALUES (286, 10, '2020-08-24 14:05:39', '0:0:0:0:0:0:0:1', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (26, 3, 5);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (27, 6, 1);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (28, 7, 2);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (29, 1, 5);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (30, 4, 5);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (31, 8, 5);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (34, 12, 6);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (38, 13, 5);
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES (39, 10, 8);
COMMIT;

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单名称',
  `level` int DEFAULT NULL COMMENT '菜单级数',
  `sort` int DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '前端图标',
  `hidden` int DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
BEGIN;
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (1, 0, '2020-02-02 14:50:36', '商品', 0, 0, 'pms', 'product', 1);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (2, 1, '2020-02-02 14:51:50', '商品列表', 1, 0, 'product', 'product-list', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (3, 1, '2020-02-02 14:52:44', '添加商品', 1, 0, 'addProduct', 'product-add', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (4, 1, '2020-02-02 14:53:51', '商品分类', 1, 0, 'productCate', 'product-cate', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (5, 1, '2020-02-02 14:54:51', '商品类型', 1, 0, 'productAttr', 'product-attr', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (6, 1, '2020-02-02 14:56:29', '品牌管理', 1, 0, 'brand', 'product-brand', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (7, 0, '2020-02-02 16:54:07', '订单', 0, 0, 'oms', 'order', 1);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (8, 7, '2020-02-02 16:55:18', '订单列表', 1, 0, 'order', 'product-list', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (9, 7, '2020-02-02 16:56:46', '订单设置', 1, 0, 'orderSetting', 'order-setting', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (10, 7, '2020-02-02 16:57:39', '退货申请处理', 1, 0, 'returnApply', 'order-return', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (11, 7, '2020-02-02 16:59:40', '退货原因设置', 1, 0, 'returnReason', 'order-return-reason', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (12, 0, '2020-02-04 16:18:00', '营销', 0, 0, 'sms', 'sms', 1);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (13, 12, '2020-02-04 16:19:22', '秒杀活动列表', 1, 0, 'flash', 'sms-flash', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (14, 12, '2020-02-04 16:20:16', '优惠券列表', 1, 0, 'coupon', 'sms-coupon', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (16, 12, '2020-02-07 16:22:38', '品牌推荐', 1, 0, 'homeBrand', 'product-brand', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (17, 12, '2020-02-07 16:23:14', '新品推荐', 1, 0, 'homeNew', 'sms-new', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (18, 12, '2020-02-07 16:26:38', '人气推荐', 1, 0, 'homeHot', 'sms-hot', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (19, 12, '2020-02-07 16:28:16', '专题推荐', 1, 0, 'homeSubject', 'sms-subject', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (20, 12, '2020-02-07 16:28:42', '广告列表', 1, 0, 'homeAdvertise', 'sms-ad', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (21, 0, '2020-02-07 16:29:13', '权限', 0, 0, 'ums', 'ums', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (22, 21, '2020-02-07 16:29:51', '用户列表', 1, 0, 'admin', 'ums-admin', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (23, 21, '2020-02-07 16:30:13', '角色列表', 1, 0, 'role', 'ums-role', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (24, 21, '2020-02-07 16:30:53', '菜单列表', 1, 0, 'menu', 'ums-menu', 0);
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`) VALUES (25, 21, '2020-02-07 16:31:13', '资源列表', 1, 0, 'resource', 'ums-resource', 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '描述',
  `category_id` bigint DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
BEGIN;
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (1, '2020-02-04 17:04:55', '商品品牌管理', '/brand/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (2, '2020-02-04 17:05:35', '商品属性分类管理', '/productAttribute/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (3, '2020-02-04 17:06:13', '商品属性管理', '/productAttribute/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (4, '2020-02-04 17:07:15', '商品分类管理', '/productCategory/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (5, '2020-02-04 17:09:16', '商品管理', '/product/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (6, '2020-02-04 17:09:53', '商品库存管理', '/sku/**', NULL, 1);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (8, '2020-02-05 14:43:37', '订单管理', '/order/**', '', 2);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (9, '2020-02-05 14:44:22', ' 订单退货申请管理', '/returnApply/**', '', 2);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (10, '2020-02-05 14:45:08', '退货原因管理', '/returnReason/**', '', 2);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (11, '2020-02-05 14:45:43', '订单设置管理', '/orderSetting/**', '', 2);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (12, '2020-02-05 14:46:23', '收货地址管理', '/companyAddress/**', '', 2);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (13, '2020-02-07 16:37:22', '优惠券管理', '/coupon/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (14, '2020-02-07 16:37:59', '优惠券领取记录管理', '/couponHistory/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (15, '2020-02-07 16:38:28', '限时购活动管理', '/flash/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (16, '2020-02-07 16:38:59', '限时购商品关系管理', '/flashProductRelation/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (17, '2020-02-07 16:39:22', '限时购场次管理', '/flashSession/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (18, '2020-02-07 16:40:07', '首页轮播广告管理', '/home/advertise/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (19, '2020-02-07 16:40:34', '首页品牌管理', '/home/brand/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (20, '2020-02-07 16:41:06', '首页新品管理', '/home/newProduct/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (21, '2020-02-07 16:42:16', '首页人气推荐管理', '/home/recommendProduct/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (22, '2020-02-07 16:42:48', '首页专题推荐管理', '/home/recommendSubject/**', '', 3);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (23, '2020-02-07 16:44:56', ' 商品优选管理', '/prefrenceArea/**', '', 5);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (24, '2020-02-07 16:45:39', '商品专题管理', '/subject/**', '', 5);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (25, '2020-02-07 16:47:34', '后台用户管理', '/admin/**', '', 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (26, '2020-02-07 16:48:24', '后台用户角色管理', '/role/**', '', 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (27, '2020-02-07 16:48:48', '后台菜单管理', '/menu/**', '', 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (28, '2020-02-07 16:49:18', '后台资源分类管理', '/resourceCategory/**', '', 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (29, '2020-02-07 16:49:45', '后台资源管理', '/resource/**', '', 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (32, '2022-09-12 16:50:59', '测试', '/user/findPage', NULL, 4);
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`) VALUES (33, '2022-12-21 14:41:04', '测试', '/user/**', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '分类名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
BEGIN;
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (1, '2020-02-05 10:21:44', '商品模块', 0);
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (2, '2020-02-05 10:22:34', '订单模块', 0);
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (3, '2020-02-05 10:22:48', '营销模块', 0);
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (4, '2020-02-05 10:23:04', '权限模块', 0);
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (5, '2020-02-07 16:34:27', '内容模块', 0);
INSERT INTO `ums_resource_category` (`id`, `create_time`, `name`, `sort`) VALUES (6, '2020-02-07 16:35:49', '其他模块', 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '描述',
  `admin_count` int DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
BEGIN;
INSERT INTO `ums_role` (`id`, `name`, `description`, `admin_count`, `create_time`, `status`, `sort`) VALUES (1, '商品管理员', '只能查看及操作商品', 0, '2020-02-03 16:50:37', 1, 0);
INSERT INTO `ums_role` (`id`, `name`, `description`, `admin_count`, `create_time`, `status`, `sort`) VALUES (2, '订单管理员', '只能查看及操作订单', 0, '2018-09-30 15:53:45', 1, 0);
INSERT INTO `ums_role` (`id`, `name`, `description`, `admin_count`, `create_time`, `status`, `sort`) VALUES (5, '超级管理员', '拥有所有查看和操作功能', 0, '2020-02-02 15:11:05', 1, 0);
INSERT INTO `ums_role` (`id`, `name`, `description`, `admin_count`, `create_time`, `status`, `sort`) VALUES (8, '权限管理员', '用于权限模块所有操作功能', 0, '2020-08-24 10:57:35', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (33, 1, 1);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (34, 1, 2);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (35, 1, 3);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (36, 1, 4);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (37, 1, 5);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (38, 1, 6);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (53, 2, 7);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (54, 2, 8);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (55, 2, 9);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (56, 2, 10);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (57, 2, 11);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (72, 5, 1);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (73, 5, 2);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (74, 5, 3);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (75, 5, 4);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (76, 5, 5);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (77, 5, 6);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (78, 5, 7);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (79, 5, 8);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (80, 5, 9);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (81, 5, 10);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (82, 5, 11);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (83, 5, 12);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (84, 5, 13);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (85, 5, 14);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (86, 5, 16);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (87, 5, 17);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (88, 5, 18);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (89, 5, 19);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (90, 5, 20);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (91, 5, 21);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (92, 5, 22);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (93, 5, 23);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (94, 5, 24);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (95, 5, 25);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (96, 6, 21);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (97, 6, 22);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (98, 6, 23);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (99, 6, 24);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (100, 6, 25);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (101, 7, 21);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (102, 7, 22);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (103, 7, 23);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (104, 7, 24);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (105, 7, 25);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (106, 8, 21);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (107, 8, 22);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (108, 8, 23);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (109, 8, 24);
INSERT INTO `ums_role_menu_relation` (`id`, `role_id`, `menu_id`) VALUES (110, 8, 25);
COMMIT;

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (103, 2, 8);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (104, 2, 9);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (105, 2, 10);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (106, 2, 11);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (107, 2, 12);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (142, 5, 1);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (143, 5, 2);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (144, 5, 3);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (145, 5, 4);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (146, 5, 5);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (147, 5, 6);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (148, 5, 8);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (149, 5, 9);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (150, 5, 10);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (151, 5, 11);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (152, 5, 12);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (153, 5, 13);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (154, 5, 14);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (155, 5, 15);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (156, 5, 16);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (157, 5, 17);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (158, 5, 18);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (159, 5, 19);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (160, 5, 20);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (161, 5, 21);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (162, 5, 22);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (163, 5, 23);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (164, 5, 24);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (165, 5, 25);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (166, 5, 26);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (167, 5, 27);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (168, 5, 28);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (169, 5, 29);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (170, 1, 1);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (171, 1, 2);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (172, 1, 3);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (173, 1, 4);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (174, 1, 5);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (175, 1, 6);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (176, 1, 23);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (177, 1, 24);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (178, 6, 25);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (179, 6, 26);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (180, 6, 27);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (181, 6, 28);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (182, 6, 29);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (205, 7, 25);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (206, 7, 26);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (207, 7, 27);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (208, 7, 28);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (209, 7, 29);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (210, 7, 31);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (211, 8, 25);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (212, 8, 26);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (213, 8, 27);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (214, 8, 28);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (215, 8, 29);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (216, 5, 32);
INSERT INTO `ums_role_resource_relation` (`id`, `role_id`, `resource_id`) VALUES (217, 5, 33);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
