/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : my-boke

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 11/03/2021 17:52:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_blog
-- ----------------------------
DROP TABLE IF EXISTS `m_blog`;
CREATE TABLE `m_blog`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `u_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `status` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_blog
-- ----------------------------
INSERT INTO `m_blog` VALUES ('20181106144432824983121', '2018110615541619824983321', '优惠大放送', '有时会碰到前端接口请求了，浏览器开发者工具上也显示参数传过去了，可后端同学却拿不到传过去的数据。可能原因是，我们请求的数据格式与后端同学所定义的接收数据格式不一致而导致的', '清仓大处理，大处理', '2021-03-11 15:15:00', 1);
INSERT INTO `m_blog` VALUES ('20181106144432824983321', '2018110615541619824983321', '庆功宴', '当 data 是 FormData 时自动设置（严格来说是强制删除）content-type 的值，让浏览器自己设置。当 data 为 URLSearchParams 对象时', '项目分红aaaa山东省', '2021-03-11 15:15:18', 1);
INSERT INTO `m_blog` VALUES ('2021031116441467172699406', '2018110615541619824983321', 'sfsfs', 'fsdfsdfsd', NULL, '2021-03-11 16:44:15', 1);
INSERT INTO `m_blog` VALUES ('2021031116484356450238986', '2018110615541619824983321', 'ee445', 'eeeeetr', NULL, '2021-03-11 16:48:44', 1);
INSERT INTO `m_blog` VALUES ('2021031117170106355195978', '2018110615541619824983321', 'ggr453', 'gg', NULL, '2021-03-11 17:17:01', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int(10) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2018110615541619824983321', 'admin', '', '2319554402@qq.com', '81dc9bdb52d04dc20036dbd8313ed055', 1, '2021-03-09 09:06:40');

SET FOREIGN_KEY_CHECKS = 1;
