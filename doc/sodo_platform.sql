/*
 Navicat Premium Data Transfer

 Source Server         : DockerMySQL
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : sodo_platform

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 16/09/2021 16:58:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for access_token
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token`  (
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `identity` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `expire_at` timestamp(0) NULL DEFAULT NULL,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of access_token
-- ----------------------------
INSERT INTO `access_token` VALUES ('0fd058f3050a55de94f716f67924d0dd', 'admin', '1', '2021-09-22 17:21:21', '2021-09-15 17:21:20');
INSERT INTO `access_token` VALUES ('2bf45843fed64fa3b53e1525ed57e4e2', 'admin', '1', '2021-09-10 22:12:32', '2021-09-03 22:12:33');
INSERT INTO `access_token` VALUES ('fe6d9b36c3f1ec33abed7aed5e35840e', 'admin', '1', '2021-09-11 11:12:19', '2021-09-04 11:12:18');

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `school_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `area_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `detail` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `gender` int(0) NULL DEFAULT NULL,
  `default_address` bit(1) NULL DEFAULT b'0',
  `create_at` timestamp(0) NULL DEFAULT NULL,
  `update_at` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for catkin_info
-- ----------------------------
DROP TABLE IF EXISTS `catkin_info`;
CREATE TABLE `catkin_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `biz_type` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `begin_id` bigint(0) NULL DEFAULT NULL,
  `max_id` bigint(0) NULL DEFAULT NULL,
  `step` int(0) NULL DEFAULT NULL,
  `delta` int(0) NULL DEFAULT NULL,
  `remainder` int(0) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `version` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'id信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of catkin_info
-- ----------------------------
INSERT INTO `catkin_info` VALUES (1, 'system', 1, 2000000000000000015, 1, 1, 0, '2018-07-22 07:52:58', '2021-09-10 17:01:58', 1);
INSERT INTO `catkin_info` VALUES (2, 'test_odd', 1, 143260, 30, 3, 1, '2018-07-22 07:52:58', '2021-09-10 17:23:06', 816);

-- ----------------------------
-- Table structure for catkin_token
-- ----------------------------
DROP TABLE IF EXISTS `catkin_token`;
CREATE TABLE `catkin_token`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `token` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `biz_type` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `remark` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'token信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of catkin_token
-- ----------------------------
INSERT INTO `catkin_token` VALUES (1, '0f673adf80504e2eaa552f5d791b644c', 'system', '1', '2017-12-15 00:36:46', '2017-12-15 00:36:48');
INSERT INTO `catkin_token` VALUES (2, '0f673adf80504e2eaa552f5d791b644c', 'test_odd', '1', '2017-12-15 00:36:46', '2017-12-15 00:36:48');

-- ----------------------------
-- Table structure for client_to_api
-- ----------------------------
DROP TABLE IF EXISTS `client_to_api`;
CREATE TABLE `client_to_api`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `api_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client_to_api
-- ----------------------------
INSERT INTO `client_to_api` VALUES ('01ee801536e47a4c67639ca2212e4707', '1', '16279554735924c30291c0b9a4c458412e48ec8c1cce1', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('036823b1c3a46901cfbc99c897e162a3', '1', '4828213d6e397101a428b6024bc49480', '2021-09-02 23:02:40', '1');
INSERT INTO `client_to_api` VALUES ('03dd1cf19927b20517c4b9b1792486b4', '2', 'd5e0c36f5f7b017183c48e70aa5e241d', '2021-08-14 19:34:10', '1');
INSERT INTO `client_to_api` VALUES ('07b3318871275b5e5cc664cf5f155ad0', '1', '9903d37b23e08dab02ff228395dd9ba4', '2021-09-02 23:31:53', '1');
INSERT INTO `client_to_api` VALUES ('0d098934fb5af64be39eb89a4c60e9eb', '1', 'a1936e98e1593acf32da9f7710b9e73c', '2021-08-21 13:08:55', '1');
INSERT INTO `client_to_api` VALUES ('128986a2d031ad26664456c997f59d33', '2', '15', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('1290b8f5c75c6c54580dc053010a9de8', '1', 'fbb054c60e420b0b537790a4be0552d8', '2021-08-20 15:01:00', '1');
INSERT INTO `client_to_api` VALUES ('16327b4dbb91092e18298c7fcf0f8836', '2', '763359a79889bb8a1f4eeceb3d5f7868', '2021-08-14 19:35:06', '1');
INSERT INTO `client_to_api` VALUES ('16d135a35e751465d21e4c6a22b4633a', '1', '53ca87d7df73e1b60e20f88580a87572', '2021-08-18 15:15:43', '1');
INSERT INTO `client_to_api` VALUES ('174849dc73e75411b1a380f99c26f703', '2', '77465c1137b94002a55c3e7ba8e6cfe1', '2021-08-18 15:03:25', '1');
INSERT INTO `client_to_api` VALUES ('17d2e9a0f7db79b462a1a901a0c5f228', '1627964614747bc868edca3b446c5bcdce58b18209195', '3', '2021-08-03 20:23:35', '1');
INSERT INTO `client_to_api` VALUES ('1b5859207912862342e520e4eca1a94b', '1', '11', '2021-08-04 07:37:33', '1');
INSERT INTO `client_to_api` VALUES ('1b5bef32a9d24c7406102a8931db7ded', '2', '6', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('1bdef786fe13bb10acc40d2b5bc4a967', '3', '1628924908003d5d60e9039cb4fb7a6b6035171e90aea', '2021-08-14 15:08:28', '1');
INSERT INTO `client_to_api` VALUES ('1c04669877d8258e3cf5247577be1aa8', '2', '0cf25ea0f8de562855429d0ff51ebfdc', '2021-08-19 08:36:18', '1');
INSERT INTO `client_to_api` VALUES ('1e2f0781c59834588a80ee02429034d6', '1', 'c57c1543c51a957086f452e38397cb5e', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('1f4a5b722da817408ec9bb7d2aba5ee4', '1', 'e29ae2f0c5cfac8848e9c2a8e23f69d0', '2021-08-20 14:57:53', '1');
INSERT INTO `client_to_api` VALUES ('209fb8e24386105666c348e43b594834', '3', '763359a79889bb8a1f4eeceb3d5f7868', '2021-08-14 19:35:06', '1');
INSERT INTO `client_to_api` VALUES ('22285eab9b5c8351ecd0018ab2fcfce6', '2', '16278743822909568f7adc26a45b3bf72c187ff31dd7d', '2021-08-04 09:14:08', '1');
INSERT INTO `client_to_api` VALUES ('22df958d1b2fb6967d9677e07af2a0e9', '1', '763359a79889bb8a1f4eeceb3d5f7868', '2021-08-14 19:35:06', '1');
INSERT INTO `client_to_api` VALUES ('25c96ecc721eb7a437bb6cfe88e6eb29', '1', '06fe9b9203c43a4b2dc988ae669bb5a8', '2021-09-02 23:32:43', '1');
INSERT INTO `client_to_api` VALUES ('25d3211cf56f97752fa69cd5fd24097f', '1', '4a695c037d7947f589ba14899a19d0e7', '2021-09-02 23:33:08', '1');
INSERT INTO `client_to_api` VALUES ('260906b84a2f7088d09a5d9848c4bbea', '2', 'e3a0e0c30b93a704b9c48489a96b60fa', '2021-08-19 10:03:02', '1');
INSERT INTO `client_to_api` VALUES ('277c4185cc0726e87e568fa6d6d8a880', '1', '71dc5d866852882e01c36fa1fd37d155', '2021-08-27 19:27:09', '1');
INSERT INTO `client_to_api` VALUES ('29e98711476490105565c620a1154c07', '1', 'fe5b4ae73d76be0fea7df0739da82951', '2021-08-20 14:57:28', '1');
INSERT INTO `client_to_api` VALUES ('2bf7ce14bc6d01b1a3f3db1eccb2d86e', '1', '13', '2021-08-04 07:37:33', '1');
INSERT INTO `client_to_api` VALUES ('2ccfad0f4d7940df4f0d38df2eb70c39', '3', 'd5e0c36f5f7b017183c48e70aa5e241d', '2021-08-14 19:34:11', '1');
INSERT INTO `client_to_api` VALUES ('2f5fa16a0635215c4304991a21dd7d31', '1', 'ccde3be8f929766c1f38a6eb777a4666', '2021-08-20 15:23:26', '1');
INSERT INTO `client_to_api` VALUES ('30f4f425ebf1a232dd87636ec688fc50', '2', 'bf3095e47dc29a6e1e54fcfcbfa6602b', '2021-08-14 19:34:48', '1');
INSERT INTO `client_to_api` VALUES ('349329ceb1f3820ceacaf0cd71becef2', '2', 'c57c1543c51a957086f452e38397cb5e', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('36e97e04936c8fab0c4e646863d9be85', '1', '14', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('37a855c2715f621967a806d45cb02e88', '1', 'e4f8b9827d253b58c9ea5f0fb60924d1', '2021-08-20 14:57:04', '1');
INSERT INTO `client_to_api` VALUES ('3c48746fdf244cc52ca0203e5ddb4cbd', '1', '3', '2021-08-04 07:34:05', '1');
INSERT INTO `client_to_api` VALUES ('400a6b4af958d2278915d7995b1a47a9', '1', 'a8b37daf4091a11bc5b353112f4b0ab4', '2021-09-02 11:02:57', '1');
INSERT INTO `client_to_api` VALUES ('42a30ac43a5f1d5395e4b9b906069515', '1', '4', '2021-08-04 07:34:04', '1');
INSERT INTO `client_to_api` VALUES ('47ed63753d0c6fe9a1d8c041c1792704', '1', 'dfa086102bae621046cbe3b9bc358974', '2021-08-22 17:04:31', '1');
INSERT INTO `client_to_api` VALUES ('483c147fa040c67bad4ca116736c3387', '1', 'fa78e5cc7eeea4de0eb523b43021946b', '2021-08-21 12:07:52', '1');
INSERT INTO `client_to_api` VALUES ('51f7b5f410f43ce38e279998f57edd4f', '2', '153728bfdbbf7cbca691367babf784f3', '2021-08-20 14:55:55', '1');
INSERT INTO `client_to_api` VALUES ('52536c97d8ea0408f6c1dd84bfcad892', '1', '9903d9a05546cc9889e0ccef12117ce2', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('5324c2d78f4b6f15dd74d33d62ea76b2', '1', '16278743822909568f7adc26a45b3bf72c187ff31dd7d', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('56080f4da0bf99763a06b69e50d7b9d4', '2', '9903d9a05546cc9889e0ccef12117ce2', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('5923cc807eb89a406283df516f4489e7', '2', '2', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('5f4e06daec2895ebb82a1453820cd1bd', '1', 'd5e0c36f5f7b017183c48e70aa5e241d', '2021-08-14 19:34:10', '1');
INSERT INTO `client_to_api` VALUES ('6488358ab59fafe66843423c5150ab5c', '1', '77465c1137b94002a55c3e7ba8e6cfe1', '2021-08-18 15:03:25', '1');
INSERT INTO `client_to_api` VALUES ('64e11d5b22ad48bb56cfe389df4d1176', '1', '153728bfdbbf7cbca691367babf784f3', '2021-08-20 14:55:55', '1');
INSERT INTO `client_to_api` VALUES ('6595044afdc003d4925c389e2b77ec81', '1', '6', '2021-08-04 07:32:13', '1');
INSERT INTO `client_to_api` VALUES ('6a6d43c03c3b4bd9aa989ecee89e334a', '2', '1', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('6ba24db7cc9463e132ce4ac5a12208bf', '1', 'bf3095e47dc29a6e1e54fcfcbfa6602b', '2021-08-14 19:34:48', '1');
INSERT INTO `client_to_api` VALUES ('709113e96be097408b5ecb97aa922aa6', '1', 'eff7c94a239eadccbceeef08aeadaeb4', '2021-08-19 10:03:31', '1');
INSERT INTO `client_to_api` VALUES ('71def8521d1ab536ee4c24129ec6c174', '1', '15', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('731c9516f363d44c5b1ba831f0854e1b', '1', '0cf25ea0f8de562855429d0ff51ebfdc', '2021-08-19 08:36:18', '1');
INSERT INTO `client_to_api` VALUES ('75b473060759bf854eec9448cbd44586', '1', '12', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('76b5548a69d7aba7b36359d2e7412425', '1', '300cc0282aa8ee4f27af35bd8a3b7dc7', '2021-09-03 18:28:29', '1');
INSERT INTO `client_to_api` VALUES ('780bcbbf53de3c78506e50c556e8a24f', '1', '8', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('7b63780034241160d7c2ff509055fc3f', '2', '4', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('83a0d3dedf5d4627d211739b35bee79f', '2', '3', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('873299f09c30420b43bc57bd7c21a1ab', '2', '1628924908003d5d60e9039cb4fb7a6b6035171e90aea', '2021-08-14 15:08:28', '1');
INSERT INTO `client_to_api` VALUES ('87be94afe2246cbfa9b9dcd2670b3c42', '1', '1', '2021-08-04 07:32:13', '1');
INSERT INTO `client_to_api` VALUES ('8b9c8818c841e9573b5ccddf596e86f2', '1', '10', '2021-08-04 07:37:33', '1');
INSERT INTO `client_to_api` VALUES ('8f3d99f4ed74fa6c2e6de150a76d81c9', '1', 'e3a0e0c30b93a704b9c48489a96b60fa', '2021-08-19 10:03:02', '1');
INSERT INTO `client_to_api` VALUES ('970c99c66c16b3beefeb3f8b6126468b', '1', '162892435789588b041733be54d17b12db72d4e4550e3', '2021-08-14 14:59:18', '1');
INSERT INTO `client_to_api` VALUES ('9782961137348816773d8761d23136e8', '1', '5147322535f3a07fded0eb5aaa2316ac', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('98f400c2a749b0b6823b5ca56441c8e7', '1', '7', '2021-08-04 07:32:43', '1');
INSERT INTO `client_to_api` VALUES ('9a8e1f7a4ccec00f3a967a45fee75309', '2', '5', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('9e652b1c4f56326deeb9d47b81dde771', '1', '5', '2021-08-04 07:32:43', '1');
INSERT INTO `client_to_api` VALUES ('9f7a834cd6ad20efb9d0b5cebfa1921c', '1', '1627964535741db038891da1b4c3f9a451177823fddc7', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('a2255163f5e2bca7ae260933ca330097', '1', '73ae35b904b2e9bc4bd11e64a82267d4', '2021-09-02 23:32:10', '1');
INSERT INTO `client_to_api` VALUES ('a6f616e2c208069e69409b5c7cffedf0', '1', '1628924908003d5d60e9039cb4fb7a6b6035171e90aea', '2021-08-14 15:08:28', '1');
INSERT INTO `client_to_api` VALUES ('b1d93f9d71afb2fad79a25d365699191', '1', '16', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('b4a24edf7221747c270bbe7fa59fd57f', '1627964614747bc868edca3b446c5bcdce58b18209195', '4', '2021-08-03 20:23:35', '1');
INSERT INTO `client_to_api` VALUES ('b8187c341a33d78d17531d902d5d751a', '1', '9441d3aed9a90ec90bf3e3e29dcf55ad', '2021-08-14 19:34:30', '1');
INSERT INTO `client_to_api` VALUES ('bd5df22717a6af09459f3ee82aa83436', '1', '9094f1b70b556bfa4161b83a7523d67d', '2021-08-22 17:39:00', '1');
INSERT INTO `client_to_api` VALUES ('c037f6d79685a9614ffd68da6b2ee60c', '2', '9441d3aed9a90ec90bf3e3e29dcf55ad', '2021-08-14 19:34:30', '1');
INSERT INTO `client_to_api` VALUES ('c38be493d8d20d75180b39ef28253ca2', '1', '055104c016e212c5140457f16e401540', '2021-08-22 17:47:34', '1');
INSERT INTO `client_to_api` VALUES ('c516408b35c59e13894f8478c819d005', '1', '9', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('c56660bed2c297c6222b5f252b67fbd0', '1', '15e4992678c6fe5bccd440249c9acaee', '2021-09-02 23:32:25', '1');
INSERT INTO `client_to_api` VALUES ('c631b3688b74cc31fd633c86eb369575', '1', '74b07ed92cdf6c28fe8a23eb1cba8700', '2021-08-20 15:46:26', '1');
INSERT INTO `client_to_api` VALUES ('c9b33c20c401c3ec00dc82d95dfb1413', '2', '16', '2021-08-04 09:14:08', '1');
INSERT INTO `client_to_api` VALUES ('cc415593967ea178df985eb323279a0f', '3', '9441d3aed9a90ec90bf3e3e29dcf55ad', '2021-08-14 19:34:30', '1');
INSERT INTO `client_to_api` VALUES ('cc716aab58ac48e34687442093311ef3', '1', '4c518017a5b20a4876277ff770dc7bdc', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('d057650e7663dcd7b1ca2e18273fea41', '2', '38b269b60aa1ae74ffb3bc5bf0cf030a', '2021-08-19 09:06:02', '1');
INSERT INTO `client_to_api` VALUES ('d32c6c05196cc9b45e91ccb29c4f51e2', '1', '16279822542199135051f678f4aecba420862d7917f31', '2021-08-04 07:32:14', '1');
INSERT INTO `client_to_api` VALUES ('d7d241c136884db133c9e92bde4085e9', '1', 'e085e4d1d34e343e51e333d5f8538988', '2021-08-20 15:23:48', '1');
INSERT INTO `client_to_api` VALUES ('da020e4340b5bccbc8f70c22a50f511b', '2', '53ca87d7df73e1b60e20f88580a87572', '2021-08-18 15:15:43', '1');
INSERT INTO `client_to_api` VALUES ('dbc4e5c95d85617de8ad7b5c5f18b7fc', '1', '2', '2021-08-04 07:34:05', '1');
INSERT INTO `client_to_api` VALUES ('df5281f31ee06b436479ef6f06097208', '2', 'eff7c94a239eadccbceeef08aeadaeb4', '2021-08-19 10:03:31', '1');
INSERT INTO `client_to_api` VALUES ('df9bc1e54eef68c433725b45dd52144f', '1', '38b269b60aa1ae74ffb3bc5bf0cf030a', '2021-08-19 09:06:02', '1');
INSERT INTO `client_to_api` VALUES ('e8d7022b35b3e2926c65c811c2215104', '2', '7', '2021-08-04 09:14:07', '1');
INSERT INTO `client_to_api` VALUES ('ee5286d47c61625ca01c0d38c72a9bcc', '1', '53d71930ecd03e4238f311749aa09b05', '2021-08-27 17:37:06', '1');
INSERT INTO `client_to_api` VALUES ('f48bb138482dad84eac38f23abcc6026', '1', '16279555314682307d27d27f94bc0a0328c922eef6bea', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('f59495f0a2a3f58727c5485a38985675', '1', '1627964567437fa002f671a37473cbaecca14c2e6b9e7', '2021-08-04 07:32:15', '1');
INSERT INTO `client_to_api` VALUES ('f5e1ad82e129600e2553cfa17a986df2', '3', 'bf3095e47dc29a6e1e54fcfcbfa6602b', '2021-08-14 19:34:48', '1');
INSERT INTO `client_to_api` VALUES ('f9143c8d66490f3cc8dd88c48d875e74', '1', '28abcfc585a1577f97d334ca78283ede', '2021-08-27 18:08:33', NULL);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `weight` int(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `price_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `original_price` decimal(10, 2) NULL DEFAULT NULL,
  `discount_price` decimal(10, 2) NULL DEFAULT NULL,
  `packing_price` decimal(10, 2) NULL DEFAULT NULL,
  `discount_limit` int(0) NULL DEFAULT 0,
  `discount` bit(1) NULL DEFAULT b'0',
  `score` double(4, 2) NULL DEFAULT 0.00,
  `orders_all` int(0) NULL DEFAULT 0,
  `orders_month` int(0) NULL DEFAULT 0,
  `orders_week` int(0) NULL DEFAULT 0,
  `orders_day` int(0) NULL DEFAULT 0,
  `income_all` decimal(10, 2) NULL DEFAULT 0.00,
  `income_month` decimal(10, 2) NULL DEFAULT 0.00,
  `income_week` decimal(10, 2) NULL DEFAULT 0.00,
  `income_day` decimal(10, 2) NULL DEFAULT 0.00,
  `stock_num` int(0) NULL DEFAULT 0,
  `stock` bit(1) NULL DEFAULT b'0',
  `sort` int(0) NULL DEFAULT NULL,
  `sell` bit(1) NULL DEFAULT b'0',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  INDEX `shop_id`(`shop_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods_choice
-- ----------------------------
DROP TABLE IF EXISTS `goods_choice`;
CREATE TABLE `goods_choice`  (
  `choice_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `set_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `choice_price` decimal(10, 2) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`choice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods_set
-- ----------------------------
DROP TABLE IF EXISTS `goods_set`;
CREATE TABLE `goods_set`  (
  `set_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`set_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods_to_set
-- ----------------------------
DROP TABLE IF EXISTS `goods_to_set`;
CREATE TABLE `goods_to_set`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `goods_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `set_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_api
-- ----------------------------
DROP TABLE IF EXISTS `log_api`;
CREATE TABLE `log_api`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `env` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `system_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `browser_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_body` longblob NULL,
  `response_status` int(0) NULL DEFAULT NULL,
  `response_body` longblob NULL,
  `api_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_business
-- ----------------------------
DROP TABLE IF EXISTS `log_business`;
CREATE TABLE `log_business`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `env` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `system_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `browser_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `business_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `business_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `business_data` longblob NULL,
  `message` longblob NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_business
-- ----------------------------
INSERT INTO `log_business` VALUES ('b79c6a9da128e37458320e25dfed02dd', 'housekeeper', '192.168.1.10:10011', 'XS-20201205QUPM', 'dev', '1', NULL, '1630749419794f6ed428cd19c4811a97cbffe642bb5e2', '127.0.0.1', 'Windows 10', 'Chrome 9', 'http://localhost:9511/housekeeper/api/403094d4a5aa178182315f40a041ab08', 'DELETE', NULL, NULL, 'DELETE', '403094d4a5aa178182315f40a041ab08', NULL, 0xE588A0E999A4204F61757468417069, '2021-09-04 17:56:59');
INSERT INTO `log_business` VALUES ('73d21271714a5c6cf4da93b6b7d928b0', 'housekeeper', '192.168.1.10:10011', 'XS-20201205QUPM', 'dev', '1', NULL, '1630751231802b01d408854c64acb8aa7f979f1ce18ae', '127.0.0.1', 'Windows 10', 'Chrome 9', 'http://localhost:9511/housekeeper/api/f1d411b4b4ba6823238ad7e9efa0f0ed', 'DELETE', 'cool.sodo.housekeeper.controller.OauthApiController', 'deleteOauthApi', 'DELETE', 'f1d411b4b4ba6823238ad7e9efa0f0ed', NULL, 0xE588A0E999A4204F61757468417069, '2021-09-04 18:27:11');
INSERT INTO `log_business` VALUES ('73f4fb0a6e3cbcf407dcd6276bd95260', 'housekeeper', '192.168.1.10:10011', 'XS-20201205QUPM', 'dev', '1', '1', '1630751433912040d3560d589447293f7f9072bbe4db0', '127.0.0.1', 'Windows 10', 'Chrome 9', 'http://localhost:9511/housekeeper/api/4f50422c6deeb7a43a420b302858d21e', 'DELETE', 'cool.sodo.housekeeper.controller.OauthApiController', 'deleteOauthApi', 'DELETE', '4f50422c6deeb7a43a420b302858d21e', NULL, 0xE588A0E999A4204F61757468417069, '2021-09-04 18:30:33');

-- ----------------------------
-- Table structure for log_error
-- ----------------------------
DROP TABLE IF EXISTS `log_error`;
CREATE TABLE `log_error`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service_host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `env` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `system_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `browser_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `stack_trace` longblob NULL,
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `message` longblob NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `line_num` int(0) NULL DEFAULT NULL,
  `params` longblob NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `menu_type` int(0) NULL DEFAULT NULL,
  `button_type` int(0) NULL DEFAULT NULL,
  `new_window` bit(1) NULL DEFAULT b'0',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '1123598815738675203', 'api', '1', '接口管理', '/system/api', 'iconfont icon-jiekou', '1', 2, 1, 0, b'1', '2021-07-27 07:23:33', '2021-08-14 19:15:43', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('11', '1123598815738675210', 'eureka', '1', '注册中心', 'http://localhost:9501/', 'iconfont iconicon_compile', '', 2, 1, NULL, b'0', '2021-08-19 11:09:25', '2021-09-13 16:29:09', '1', NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675201', '0', 'desk', '1', '工作台', '/desk', 'iconfont iconicon_airplay', '1', 1, 1, 0, b'0', '2021-07-27 07:23:27', '2021-08-20 15:59:59', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675202', '1123598815738675201', 'notice', '1', '通知公告', '/desk/notice', '', NULL, 1, 1, 0, b'0', '2021-07-27 07:23:27', '2021-07-27 21:48:17', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675203', '0', 'system', '1', '系统管理', '/system', 'iconfont iconicon_work', NULL, 6, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:21', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675204', '13', 'user', '1', '用户管理', '/user/user', 'iconfont icon-yonghu', '1', 1, 1, 0, b'1', '2021-07-27 07:23:28', '2021-09-13 16:30:45', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675205', '13', 'dept', '1', '部门管理', '/user/dept', '', '1', 3, 1, 0, b'1', '2021-07-27 07:23:28', '2021-09-13 16:30:45', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675206', '1123598815738675203', 'dict', '1', '字典管理', '/system/dict', '', '1', 4, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:18', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675207', '1123598815738675203', 'menu', '1', '菜单管理', '/system/menu', 'iconfont icon-caidanguanli', '1', 3, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:16', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675208', '13', 'role', '1', '角色管理', '/user/role', 'iconfont iconicon_group', NULL, 2, 1, 0, b'1', '2021-07-27 07:23:28', '2021-09-13 16:30:45', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675210', '0', 'monitor', '1', '系统监控', '/monitor', 'iconfont iconicon_glass', NULL, 2, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:17', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675211', '1123598815738675210', 'doc', '1', '接口文档', 'http://localhost:9511/doc.html', 'iconfont icon-wendang', '1', 1, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:18', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675212', '1123598815738675210', 'prometheus', '1', 'Prometheus 监控', 'http://localhost:9090', 'iconfont icon-jiankong', '1', 3, 1, 0, b'1', '2021-07-27 07:23:28', '2021-07-27 21:48:23', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675213', NULL, 'log', '1', '日志管理', '/log', 'iconfont icon-rizhi', NULL, 3, 1, 0, b'1', '2021-07-27 07:23:29', '2021-09-13 16:38:26', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675214', '1123598815738675213', 'log_business', '1', '业务日志', '/log/business', 'iconfont icon-yewu', NULL, 1, 1, 0, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:20', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675215', '1123598815738675213', 'log_api', '1', '接口日志', '/log/api', 'iconfont icon-jiekou', NULL, 2, 1, 0, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:20', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675216', '1123598815738675213', 'log_error', '1', '错误日志', '/log/error', 'iconfont icon-cuowu', NULL, 3, 1, 0, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:16', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675217', '0', 'tool', '1', '研发工具', '/tool', 'iconfont icongithub', NULL, 3, 1, 0, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:22', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675218', '1123598815738675217', 'code', '1', '代码生成', '/tool/code', NULL, NULL, 1, 1, 0, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675219', '1123598815738675202', 'notice_add', '1', '新增', '/desk/notice/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675220', '1123598815738675202', 'notice_edit', '1', '修改', '/desk/notice/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:22', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675221', '1123598815738675202', 'notice_delete', '1', '删除', '/api/blade-desk/notice/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:29', '2021-07-27 21:48:21', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675222', '1123598815738675202', 'notice_view', '1', '查看', '/desk/notice/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675223', '1123598815738675204', 'user_add', '1', '新增', '/system/user/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675224', '1123598815738675204', 'user_edit', '1', '修改', '/system/user/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675225', '1123598815738675204', 'user_delete', '1', '删除', '/api/blade-user/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675226', '1123598815738675204', 'user_role', '1', '角色配置', NULL, 'user-add', NULL, 4, 2, 1, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:18', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675227', '1123598815738675204', 'user_reset', '1', '密码重置', '/api/blade-user/reset-password', 'retweet', NULL, 5, 2, 1, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:24', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675228', '1123598815738675204', 'user_view', '1', '查看', '/system/user/view', 'file-text', NULL, 6, 2, 2, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675229', '1123598815738675205', 'dept_add', '1', '新增', '/system/dept/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:24', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675230', '1123598815738675205', 'dept_edit', '1', '修改', '/system/dept/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:22', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675231', '1123598815738675205', 'dept_delete', '1', '删除', '/api/blade-system/dept/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:30', '2021-07-27 21:48:17', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675232', '1123598815738675205', 'dept_view', '1', '查看', '/system/dept/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:18', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675233', '1123598815738675206', 'dict_add', '1', '新增', '/system/dict/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675234', '1123598815738675206', 'dict_edit', '1', '修改', '/system/dict/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675235', '1123598815738675206', 'dict_delete', '1', '删除', '/api/blade-system/dict/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675236', '1123598815738675206', 'dict_view', '1', '查看', '/system/dict/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675237', '1123598815738675207', 'menu_add', '1', '新增', '/system/menu/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:21', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675238', '1123598815738675207', 'menu_edit', '1', '修改', '/system/menu/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675239', '1123598815738675207', 'menu_delete', '1', '删除', '/api/blade-system/menu/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:19', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675240', '1123598815738675207', 'menu_view', '1', '查看', '/system/menu/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675241', '1123598815738675208', 'role_add', '1', '新增', '/system/role/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:31', '2021-07-27 21:48:24', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675242', '1123598815738675208', 'role_edit', '1', '修改', '/system/role/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:15', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675243', '1123598815738675208', 'role_delete', '1', '删除', '/api/blade-system/role/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:21', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675244', '1123598815738675208', 'role_view', '1', '查看', '/system/role/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:15', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675249', '1123598815738675214', 'log_business_view', '1', '查看', '/log/business/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:17', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675250', '1123598815738675215', 'log_api_view', '1', '查看', '/log/api/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:17', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675251', '1123598815738675216', 'log_error_view', '1', '查看', '/log/error/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:32', '2021-07-27 21:48:19', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675252', '1123598815738675218', 'code_add', '1', '新增', '/tool/code/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:19', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675253', '1123598815738675218', 'code_edit', '1', '修改', '/tool/code/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:21', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675254', '1123598815738675218', 'code_delete', '1', '删除', '/api/blade-system/code/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:19', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675255', '1123598815738675218', 'code_view', '1', '查看', '/tool/code/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:21', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675261', '1123598815738675203', 'client', '1', '应用管理', '/system/client', 'iconfont icon-shouji', NULL, 1, 1, 0, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:22', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1123598815738675262', '1123598815738675261', 'client_add', '1', '新增', '/system/client/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:22', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675263', '1123598815738675261', 'client_edit', '1', '修改', '/system/client/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675264', '1123598815738675261', 'client_delete', '1', '删除', '/api/blade-system/client/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:19', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675265', '1123598815738675261', 'client_view', '1', '查看', '/system/client/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:18', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675266', '1123598815738675217', 'datasource', '1', '数据源管理', '/tool/datasource', '', NULL, 2, 1, 0, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675267', '1123598815738675266', 'datasource_add', '1', '新增', '/tool/datasource/add', 'plus', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:17', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675268', '1123598815738675266', 'datasource_edit', '1', '修改', '/tool/datasource/edit', 'form', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:16', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675269', '1123598815738675266', 'datasource_delete', '1', '删除', '/api/blade-develop/datasource/remove', 'delete', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:18', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1123598815738675270', '1123598815738675266', 'datasource_view', '1', '查看', '/tool/datasource/view', 'file-text', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:34', '2021-07-27 21:48:19', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962201', '0', 'base', '1', '基础配置', '/base', 'iconfont iconicon_setting', NULL, 5, 1, 0, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:23', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1164733399668962202', '1164733399668962201', 'region', '1', '行政区划', '/base/region', '', NULL, 1, 1, 0, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962203', '1164733399668962202', 'region_add', '1', '新增下级', '', '', NULL, 1, 2, 1, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:22', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962204', '1164733399668962202', 'region_delete', '1', '删除', '/api/blade-system/region/remove', '', NULL, 2, 2, 2, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:17', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962205', '1164733399668962202', 'region_import', '1', '导入', '', '', NULL, 3, 2, 3, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:20', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962206', '1164733399668962202', 'region_export', '1', '导出', '', '', NULL, 4, 2, 2, b'1', '2021-07-27 07:23:35', '2021-07-27 21:48:22', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399668962207', '1164733399668962202', 'region_debug', '1', '调试', '', '', NULL, 5, 2, 2, b'1', '2021-07-27 07:23:36', '2021-07-27 21:48:18', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399669962301', '0', 'report', '1', '报表管理', '/report', 'iconfont iconicon_savememo', NULL, 4, 1, 0, b'1', '2021-07-27 07:23:36', '2021-07-27 21:48:18', NULL, '1', b'0');
INSERT INTO `menu` VALUES ('1164733399669962302', '1164733399669962301', 'report_setting', '1', '报表配置', 'http://localhost:8108/ureport/designer', '', NULL, 1, 1, 0, b'1', '2021-07-27 07:23:36', '2021-07-27 21:48:23', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('1164733399669962303', '1164733399669962301', 'report_list', '1', '报表列表', '/report/reportlist', '', NULL, 2, 1, 0, b'1', '2021-07-27 07:23:36', '2021-07-27 21:48:17', NULL, NULL, b'0');
INSERT INTO `menu` VALUES ('12', '0', 'ip', '1', 'IP 管理', '/ip', 'iconfont icon-ip', '', 8, 1, 0, b'0', '2021-09-02 18:18:41', '2021-09-13 16:29:40', '1', '1', b'0');
INSERT INTO `menu` VALUES ('13', NULL, 'user_center', '1', '用户管理', '/user', 'iconfont iconicon_boss', '', 7, 1, NULL, b'0', '2021-08-19 18:01:54', '2021-09-13 16:38:26', '1', '1', b'0');
INSERT INTO `menu` VALUES ('15', '12', 'ip_black', '1', '黑名单', '/ip/black', 'iconfont icon-lock', '', 2, 1, NULL, b'0', '2021-09-02 18:20:33', '2021-09-13 16:35:25', '1', '1', b'0');
INSERT INTO `menu` VALUES ('16', '12', 'ip_white', '1', '白名单', '/ip/white', 'iconfont icon-unlock', '', 1, 1, NULL, b'0', '2021-09-02 18:20:00', '2021-09-13 16:35:42', '1', '1', b'0');
INSERT INTO `menu` VALUES ('2', '1', 'api_delete', '1', '删除', '/system/api', NULL, NULL, 1, 2, 3, b'1', '2021-07-27 07:23:33', '2021-07-27 21:48:22', NULL, NULL, b'0');

-- ----------------------------
-- Table structure for oauth_api
-- ----------------------------
DROP TABLE IF EXISTS `oauth_api`;
CREATE TABLE `oauth_api`  (
  `api_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `limit_num` int(0) NULL DEFAULT NULL,
  `limit_period` int(0) NULL DEFAULT NULL,
  `request_day` int(0) NULL DEFAULT 0,
  `request_week` int(0) NULL DEFAULT 0,
  `request_month` int(0) NULL DEFAULT 0,
  `request_all` int(0) NULL DEFAULT 0,
  `request_limit` bit(1) NULL DEFAULT b'0',
  `log` bit(1) NULL DEFAULT b'0',
  `in_use` bit(1) NULL DEFAULT b'0',
  `auth` bit(1) NULL DEFAULT b'0',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`api_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_api
-- ----------------------------
INSERT INTO `oauth_api` VALUES ('055104c016e212c5140457f16e401540', '用户授权', '', '/housekeeper/user/grant', 'housekeeper', 'POST', '', NULL, NULL, 2, 2, 2, 2, b'0', b'0', b'1', b'1', '2021-08-22 17:47:33', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('06fe9b9203c43a4b2dc988ae669bb5a8', '更新 IP', '', '/housekeeper/ip', 'housekeeper', 'PATCH', '', NULL, NULL, 6, 6, 6, 6, b'0', b'0', b'1', b'1', '2021-09-02 23:32:42', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('0cf25ea0f8de562855429d0ff51ebfdc', '分页查询错误日志', NULL, '/log/error/page', 'log', 'POST', '', NULL, NULL, 156, 156, 156, 156, b'0', b'0', b'1', b'1', '2021-08-19 08:36:17', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('1', '图形验证码', NULL, '/auth/authenticate/captcha', 'auth', 'GET', NULL, 11, 11, 975, 975, 975, 975, b'0', b'0', b'1', b'0', '2021-07-13 23:14:00', '2021-07-31 04:29:04', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('10', '获取用户基本信息', NULL, '/user/user', 'user', 'GET', NULL, NULL, NULL, 124, 124, 124, 124, b'0', b'0', b'1', b'1', '2021-07-27 09:00:02', '2021-07-31 03:39:04', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('11', '获取用户详情信息', NULL, '/user/user/info', 'user', 'GET', NULL, NULL, NULL, 415, 415, 415, 415, b'0', b'0', b'1', b'1', '2021-07-27 09:00:02', '2021-08-02 20:07:15', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('12', '修改用户基本信息', NULL, '/user/user', 'user', 'PATCH', NULL, NULL, NULL, 31, 31, 31, 31, b'0', b'0', b'1', b'1', '2021-07-27 22:21:20', '2021-08-02 20:09:47', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('13', '修改密码', NULL, '/user/user/password', 'user', 'POST', NULL, NULL, NULL, 11, 11, 11, 11, b'0', b'0', b'1', b'1', '2021-07-28 01:19:15', '2021-08-02 20:09:41', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('14', '分页查询接口列表', NULL, '/housekeeper/api/page', 'housekeeper', 'POST', NULL, NULL, NULL, 1208, 1208, 1208, 1208, b'0', b'0', b'1', b'1', '2021-07-30 18:30:49', '2021-08-13 03:37:35', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('15', '更新接口', NULL, '/housekeeper/api', 'housekeeper', 'PATCH', NULL, NULL, NULL, 87, 87, 87, 87, b'0', b'0', b'1', b'1', '2021-07-31 03:28:53', '2021-08-02 20:09:28', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('153728bfdbbf7cbca691367babf784f3', '查询上级角色列表', NULL, '/housekeeper/role/tree', 'housekeeper', 'GET', '', NULL, NULL, 567, 567, 567, 567, b'0', b'0', b'1', b'1', '2021-08-20 14:55:54', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('15e4992678c6fe5bccd440249c9acaee', '删除 IP', '', '/housekeeper/ip', 'housekeeper', 'DELETE', '', NULL, NULL, 5, 5, 5, 5, b'0', b'0', b'1', b'1', '2021-09-02 23:32:24', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('16', '新增接口', NULL, '/housekeeper/api', 'housekeeper', 'POST', NULL, NULL, NULL, 60, 60, 60, 60, b'0', b'0', b'1', b'1', '2021-07-31 03:28:53', '2021-08-02 20:09:23', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('16278743822909568f7adc26a45b3bf72c187ff31dd7d', '批量删除接口', NULL, '/housekeeper/api/list', 'housekeeper', 'DELETE', '', NULL, NULL, 3, 3, 3, 3, b'0', b'0', b'1', b'1', '2021-08-02 19:19:41', '2021-08-04 01:22:09', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('16279213907327788fd45c83048ac8ccce27feba371e9', 'ttt', NULL, 'ttt', 'auth', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'1', b'0', '2021-08-03 08:23:11', '2021-08-03 08:37:25', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('1627922063607d137d13e4090483cb482003bc53323c3', 'ttt', NULL, 'ttt', 'auth', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'0', b'0', '2021-08-03 08:34:24', '2021-08-03 08:37:25', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('16279554735924c30291c0b9a4c458412e48ec8c1cce1', '查询可用接口列表', NULL, '/housekeeper/api/list/use', 'housekeeper', 'GET', '', NULL, NULL, 482, 482, 482, 482, b'0', b'0', b'1', b'1', '2021-08-03 17:51:13', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('16279555314682307d27d27f94bc0a0328c922eef6bea', '查询客户端详情信息', NULL, '/housekeeper/client', 'housekeeper', 'GET', '', NULL, NULL, 104, 104, 104, 104, b'0', b'0', b'1', b'1', '2021-08-03 17:52:11', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('1627964535741db038891da1b4c3f9a451177823fddc7', '更新客户端', NULL, '/housekeeper/client', 'housekeeper', 'PATCH', '', NULL, NULL, 59, 59, 59, 59, b'0', b'0', b'1', b'1', '2021-08-03 20:22:15', '2021-08-04 08:51:49', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('1627964567437fa002f671a37473cbaecca14c2e6b9e7', '新增客户端', NULL, '/housekeeper/client', 'housekeeper', 'POST', '', NULL, NULL, 6, 6, 6, 6, b'0', b'0', b'1', b'1', '2021-08-03 20:22:47', '2021-08-03 20:22:54', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('16279822542199135051f678f4aecba420862d7917f31', '删除客户端', NULL, '/housekeeper/client', 'housekeeper', 'DELETE', '', NULL, NULL, 3, 3, 3, 3, b'0', b'0', b'1', b'1', '2021-08-04 01:17:33', '2021-08-13 04:06:05', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('162877087814046c65553ac4e4f45976f20184365d6a5', 'test', NULL, 'test', 'auth', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'0', b'0', '2021-08-12 20:21:17', '2021-08-20 21:44:25', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('162892435789588b041733be54d17b12db72d4e4550e3', '查询上级菜单列表', NULL, '/housekeeper/menu/tree', 'housekeeper', 'GET', '', NULL, NULL, 661, 661, 661, 661, b'0', b'0', b'1', b'1', '2021-08-14 14:59:17', '2021-08-20 14:56:18', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('1628924908003d5d60e9039cb4fb7a6b6035171e90aea', '查询菜单列表', NULL, '/housekeeper/menu/list', 'housekeeper', 'POST', '', NULL, NULL, 688, 688, 688, 688, b'0', b'0', b'1', b'1', '2021-08-14 15:08:27', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('2', '密码加密公钥', NULL, '/auth/authenticate/key', 'auth', 'GET', NULL, 5, 60, 1102, 1102, 1102, 1102, b'0', b'0', b'1', b'0', '2021-07-14 22:38:37', '2021-08-02 20:07:41', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('28abcfc585a1577f97d334ca78283ede', '删除用户', '', '/housekeeper/user', 'housekeeper', 'DELETE', '', NULL, NULL, 2, 2, 2, 2, b'0', b'0', b'1', b'1', '2021-08-27 18:08:09', '2021-08-27 18:08:33', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('3', '认证', NULL, '/auth/authenticate', 'auth', 'POST', NULL, NULL, NULL, 366, 366, 366, 366, b'0', b'0', b'1', b'0', '2021-07-14 19:37:05', '2021-08-02 20:07:51', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('300cc0282aa8ee4f27af35bd8a3b7dc7', '退出登录', '', '/auth/authenticate/logout', 'auth', 'GET', '', NULL, NULL, 16, 16, 16, 16, b'0', b'0', b'1', b'1', '2021-09-03 18:28:28', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('38b269b60aa1ae74ffb3bc5bf0cf030a', '查询错误日志详情信息', NULL, '/log/error', 'log', 'GET', '', NULL, NULL, 31, 31, 31, 31, b'0', b'0', b'1', b'1', '2021-08-19 09:06:01', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('394ac8cba953fd77acae47f1cede35ce', '查询当前用户菜单树', '', '/housekeeper/menu/tree/grant', 'housekeeper', 'GET', '', NULL, NULL, 24, 24, 24, 24, b'0', b'0', b'1', b'1', '2021-08-21 12:06:16', '2021-08-21 17:46:27', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('4', '授权', NULL, '/auth/authorize', 'auth', 'POST', NULL, NULL, NULL, 185, 185, 185, 185, b'0', b'0', b'1', b'0', '2021-07-14 19:38:01', '2021-08-02 20:07:46', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('403094d4a5aa178182315f40a041ab08', 'test', 'test', 'test', 'auth', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'0', b'0', '2021-09-04 17:56:52', '2021-09-04 17:57:00', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('4828213d6e397101a428b6024bc49480', '分页查询 IP 列表', '', '/housekeeper/ip/page', 'housekeeper', 'POST', '', 5, 60, 166, 166, 166, 166, b'0', b'0', b'1', b'1', '2021-09-02 23:02:39', '2021-09-16 15:58:58', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('4a695c037d7947f589ba14899a19d0e7', '删除 IP 列表', '', '/housekeeper/ip/list', 'housekeeper', 'DELETE', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'1', b'1', '2021-09-02 23:33:07', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('4c518017a5b20a4876277ff770dc7bdc', '分页查询客户端列表', NULL, '/housekeeper/client/page', 'housekeeper', 'POST', '', NULL, NULL, 341, 341, 341, 341, b'0', b'0', b'1', b'1', '2021-07-31 03:32:41', '2021-08-02 20:09:17', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('4f50422c6deeb7a43a420b302858d21e', 'test', '', 'test', 'housekeeper', 'POST', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'0', b'0', '2021-09-04 18:30:31', '2021-09-04 18:30:34', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('5', '获取公钥', NULL, '/auth/signature/key', 'auth', 'GET', NULL, NULL, NULL, 187, 187, 187, 187, b'0', b'0', b'1', b'0', '2021-07-20 18:07:50', '2021-08-02 20:07:36', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('5147322535f3a07fded0eb5aaa2316ac', '查询可用客户端列表', NULL, '/housekeeper/client/list/use', 'housekeeper', 'GET', '', NULL, NULL, 2137, 2137, 2137, 2137, b'0', b'0', b'1', b'1', '2021-07-31 21:15:41', '2021-08-02 20:08:40', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('53ca87d7df73e1b60e20f88580a87572', '查询接口日志详情信息', NULL, '/log/api', 'log', 'GET', '', NULL, NULL, 112, 112, 112, 112, b'0', b'0', b'1', b'1', '2021-08-18 15:15:43', '2021-08-18 20:24:58', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('53d71930ecd03e4238f311749aa09b05', '新增用户', '', '/housekeeper/user', 'housekeeper', 'POST', '', NULL, NULL, 3, 3, 3, 3, b'0', b'0', b'1', b'1', '2021-08-27 17:37:06', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('6', '提交签名私钥', NULL, '/auth/signature/key', 'auth', 'POST', NULL, NULL, NULL, 173, 173, 173, 173, b'0', b'0', b'1', b'0', '2021-07-20 18:08:37', '2021-08-02 20:07:27', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('7', '校验签名是否失效', NULL, '/auth/signature/key/validate', 'auth', 'GET', NULL, NULL, NULL, 1210, 1211, 1211, 1211, b'0', b'0', b'1', b'0', '2021-07-21 08:58:12', '2021-08-02 20:07:21', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('71dc5d866852882e01c36fa1fd37d155', '更新用户', '', '/housekeeper/user', 'housekeeper', 'PATCH', '', NULL, NULL, 52, 52, 52, 52, b'0', b'0', b'1', b'1', '2021-08-27 19:27:09', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('73ae35b904b2e9bc4bd11e64a82267d4', '新增 IP', '', '/housekeeper/ip', 'housekeeper', 'POST', '', NULL, NULL, 8, 8, 8, 8, b'0', b'0', b'1', b'1', '2021-09-02 23:32:08', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('74b07ed92cdf6c28fe8a23eb1cba8700', '更新角色信息', NULL, '/housekeeper/role', 'housekeeper', 'PATCH', '', NULL, NULL, 2, 2, 2, 2, b'0', b'0', b'1', b'1', '2021-08-20 15:46:25', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('763359a79889bb8a1f4eeceb3d5f7868', '查询菜单', NULL, '/housekeeper/menu', 'housekeeper', 'GET', '', NULL, NULL, 153, 153, 153, 153, b'0', b'0', b'1', b'1', '2021-08-14 19:35:05', '2021-08-14 20:01:52', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('77465c1137b94002a55c3e7ba8e6cfe1', '分页查询接口日志', NULL, '/log/api/page', 'log', 'POST', '', NULL, NULL, 865, 865, 865, 865, b'0', b'0', b'1', b'1', '2021-08-18 15:03:25', '2021-08-18 15:12:58', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('8', '获取客户端菜单列表', NULL, '/user/menu/route', 'user', 'GET', NULL, NULL, NULL, 985, 985, 985, 985, b'0', b'0', b'1', b'1', '2021-07-27 09:00:02', '2021-08-02 20:07:09', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('9', '获取客户端按钮列表', NULL, '/user/menu/button', 'user', 'GET', NULL, NULL, NULL, 946, 946, 946, 946, b'0', b'0', b'1', b'1', '2021-07-27 09:00:02', '2021-08-02 20:07:03', NULL, '1', b'0');
INSERT INTO `oauth_api` VALUES ('9094f1b70b556bfa4161b83a7523d67d', '查询用户详情信息', '', '/housekeeper/user', 'housekeeper', 'GET', '', NULL, NULL, 139, 139, 139, 139, b'0', b'0', b'1', b'1', '2021-08-22 17:38:59', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('9441d3aed9a90ec90bf3e3e29dcf55ad', '删除菜单', NULL, '/housekeeper/menu', 'housekeeper', 'DELETE', '', NULL, NULL, 9, 9, 9, 9, b'0', b'0', b'1', b'1', '2021-08-14 19:34:29', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('9903d37b23e08dab02ff228395dd9ba4', '查询 IP 详情', '', '/housekeeper/ip', 'housekeeper', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'1', b'1', '2021-09-02 23:31:52', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('9903d9a05546cc9889e0ccef12117ce2', '删除接口', NULL, '/housekeeper/api', 'housekeeper', 'DELETE', '', NULL, NULL, 10, 10, 10, 10, b'0', b'0', b'1', b'1', '2021-08-02 07:50:59', '2021-08-02 20:08:35', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('a1936e98e1593acf32da9f7710b9e73c', '角色授权', '', '/housekeeper/role/grant', 'housekeeper', 'POST', '', NULL, NULL, 8, 8, 8, 8, b'0', b'0', b'1', b'1', '2021-08-21 13:08:54', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('a8b37daf4091a11bc5b353112f4b0ab4', '踢人下线', '', '/housekeeper/user/logout', 'housekeeper', 'GET', '', NULL, NULL, 3, 3, 3, 3, b'0', b'0', b'1', b'1', '2021-09-02 11:02:56', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('bf3095e47dc29a6e1e54fcfcbfa6602b', '更新菜单', NULL, '/housekeeper/menu', 'housekeeper', 'PATCH', '', NULL, NULL, 71, 71, 71, 71, b'0', b'0', b'1', b'1', '2021-08-14 19:34:48', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('c57c1543c51a957086f452e38397cb5e', '查询接口详情信息', NULL, '/housekeeper/api', 'housekeeper', 'GET', '', NULL, NULL, 178, 178, 178, 178, b'0', b'0', b'1', b'1', '2021-07-31 20:53:27', '2021-08-02 20:08:45', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('ccde3be8f929766c1f38a6eb777a4666', '删除角色列表', NULL, '/housekeeper/role/list', 'housekeeper', 'DELETE', '', NULL, NULL, 8, 8, 8, 8, b'0', b'0', b'1', b'1', '2021-08-20 15:23:25', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('d5e0c36f5f7b017183c48e70aa5e241d', '新增菜单', NULL, '/housekeeper/menu', 'housekeeper', 'POST', '', NULL, NULL, 20, 20, 20, 20, b'0', b'0', b'1', b'1', '2021-08-14 19:34:09', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('dfa086102bae621046cbe3b9bc358974', '分页查询用户列表', '', '/housekeeper/user/page', 'housekeeper', 'POST', '', NULL, NULL, 285, 285, 285, 285, b'0', b'1', b'1', b'1', '2021-08-22 17:04:30', '2021-09-15 22:27:51', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('e085e4d1d34e343e51e333d5f8538988', '查询角色详情信息', NULL, '/housekeeper/role', 'housekeeper', 'GET', '', NULL, NULL, 12, 12, 12, 12, b'0', b'0', b'1', b'1', '2021-08-20 15:23:48', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('e29ae2f0c5cfac8848e9c2a8e23f69d0', '删除角色', NULL, '/housekeeper/role', 'housekeeper', 'DELETE', '', NULL, NULL, 2, 2, 2, 2, b'0', b'0', b'1', b'1', '2021-08-20 14:57:53', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('e3a0e0c30b93a704b9c48489a96b60fa', '分页查询业务日志', NULL, '/log/business/page', 'log', 'POST', '', NULL, NULL, 83, 83, 83, 83, b'0', b'0', b'1', b'1', '2021-08-19 10:03:01', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('e4f8b9827d253b58c9ea5f0fb60924d1', '查询角色列表', '', '/housekeeper/role/list', 'housekeeper', 'POST', '', NULL, NULL, 277, 277, 277, 277, b'0', b'0', b'1', b'1', '2021-08-20 14:57:03', '2021-08-20 22:22:40', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('eff7c94a239eadccbceeef08aeadaeb4', '查询业务日志详情', '', '/log/business', 'log', 'GET', '', NULL, NULL, 11, 11, 11, 11, b'0', b'0', b'1', b'1', '2021-08-19 10:03:31', '2021-08-20 21:35:36', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('f1d411b4b4ba6823238ad7e9efa0f0ed', 'test', '', 'test', 'auth', 'GET', '', NULL, NULL, 0, 0, 0, 0, b'0', b'0', b'0', b'0', '2021-09-04 18:27:08', '2021-09-04 18:27:12', '1', '1', b'1');
INSERT INTO `oauth_api` VALUES ('fa78e5cc7eeea4de0eb523b43021946b', '查询角色对应菜单 ID 列表', '', '/housekeeper/menu/list', 'housekeeper', 'GET', '', NULL, NULL, 59, 59, 59, 59, b'0', b'0', b'1', b'1', '2021-08-21 12:07:50', '2021-08-21 17:45:59', '1', '1', b'0');
INSERT INTO `oauth_api` VALUES ('fbb054c60e420b0b537790a4be0552d8', '删除菜单列表', NULL, '/housekeeper/menu/list', 'housekeeper', 'DELETE', '', NULL, NULL, 3, 3, 3, 3, b'0', b'0', b'1', b'1', '2021-08-20 15:01:00', NULL, '1', NULL, b'0');
INSERT INTO `oauth_api` VALUES ('fe5b4ae73d76be0fea7df0739da82951', '新增角色', NULL, '/housekeeper/role', 'housekeeper', 'POST', '', NULL, NULL, 20, 20, 20, 20, b'0', b'0', b'1', b'1', '2021-08-20 14:57:28', NULL, '1', NULL, b'0');

-- ----------------------------
-- Table structure for oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE `oauth_client`  (
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `in_use` bit(1) NULL DEFAULT b'0',
  `register` bit(1) NULL DEFAULT b'0',
  `captcha` bit(1) NULL DEFAULT b'0',
  `signature` bit(1) NULL DEFAULT b'0',
  `concurrent_login` bit(1) NULL DEFAULT b'0',
  `share_token` bit(1) NULL DEFAULT b'0',
  `user_status` int(0) NULL DEFAULT NULL,
  `token_expire` int(0) NULL DEFAULT NULL,
  `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client
-- ----------------------------
INSERT INTO `oauth_client` VALUES ('1', '衙门', '1', 'HouseKeeper 后台管理！', b'1', b'1', b'1', b'1', b'1', b'0', 0, NULL, NULL, '2020-11-02 07:32:23', '2021-09-03 21:37:33', '1', '1', b'0');
INSERT INTO `oauth_client` VALUES ('2', '掌柜', '2', '交易中心后台管理', b'1', b'0', b'0', b'0', b'0', b'0', 1, NULL, '', '2021-08-04 05:26:15', '2021-08-15 14:31:37', '1', '1', b'0');
INSERT INTO `oauth_client` VALUES ('3', '驿站', '3', '配送中心后台管理', b'0', b'0', b'0', b'0', b'0', b'0', 1, NULL, '', '2021-08-04 07:21:22', '2021-08-15 14:31:22', '1', '1', b'0');

-- ----------------------------
-- Table structure for oauth_ip
-- ----------------------------
DROP TABLE IF EXISTS `oauth_ip`;
CREATE TABLE `oauth_ip`  (
  `ip_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `valid_num` int(0) NULL DEFAULT 0,
  `valid` bit(1) NULL DEFAULT b'0',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`ip_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `oauth_user`;
CREATE TABLE `oauth_user`  (
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `gender` int(0) NULL DEFAULT NULL,
  `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `language` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_user
-- ----------------------------
INSERT INTO `oauth_user` VALUES ('1', NULL, NULL, '👣', 'https://thirdwx.qlogo.cn/mmopen/vi_32/FrAfaQn9DMaicrITAMbT7Bl9aZ8pKtOYIB0HCkRDdPozBY7ATetpUXw0RG31QIg6ahaXON71sYsia4fHt0hsL7mw/132', NULL, NULL, NULL, NULL, NULL, NULL, '2020-11-18 05:15:18', '2020-11-18 05:15:18');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'c',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '0', '1', '超级管理员', 'administrator', '系统超级管理员', 1, '2021-07-23 19:19:10', '2021-08-19 16:02:50', '1', NULL, b'0');
INSERT INTO `role` VALUES ('1437356489200738306', NULL, '1', '研发', 'coder', '', 3, '2021-09-13 18:04:26', '2021-09-13 19:02:02', '1', '1', b'1');
INSERT INTO `role` VALUES ('1437356600391737346', NULL, '1', 'MenuVO', 'MenuVO', '', 4, '2021-09-13 18:04:52', '2021-09-13 19:01:42', '1', '1', b'1');
INSERT INTO `role` VALUES ('2', '0', '1', '用户', 'user', '系统用户', 2, '2021-07-23 19:21:23', '2021-08-20 15:48:16', NULL, '1', b'0');
INSERT INTO `role` VALUES ('2000000000000000001', NULL, '1', 'Primary1', 'Primary1', '', 5, '2021-09-13 18:12:41', '2021-09-13 19:01:42', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000002', NULL, '1', 'Primary2', 'Primary2', '', 5, '2021-09-13 18:14:14', '2021-09-13 19:01:42', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000003', NULL, '1', 'Primary3', 'Primary3', '', 1, '2021-09-13 18:14:22', '2021-09-13 19:01:42', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000004', NULL, '1', 'Primary4', 'Primary4', '', 1, '2021-09-13 18:14:28', '2021-09-13 19:01:42', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000005', '2000000000000000002', '1', 'ToStringSerializer', 'ToStringSerializer', '', 1, '2021-09-13 18:47:37', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000006', '2000000000000000005', '1', 'JSONField', 'JSONField', '', 2, '2021-09-13 18:54:57', '2021-09-13 19:01:52', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000007', '2000000000000000006', '1', 'JSONField', 'JSONField', '', 1, '2021-09-13 18:56:14', '2021-09-13 19:01:58', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000008', '2000000000000000002', '1', 'JSONField', 'JSONField', '', 1, '2021-09-13 18:56:25', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000009', '1437356600391737346', '1', 'JSONField', 'JSONField', '', 1, '2021-09-13 19:00:23', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000010', '2000000000000000004', '1', 'JSONField', 'JSONField', '', 1, '2021-09-13 19:00:48', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000011', '2000000000000000004', '1', 'JSONField', 'JSONField', '', 1, '2021-09-13 19:00:58', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000012', '2000000000000000004', '1', 'JSONField', 'JSONField', '', 2, '2021-09-13 19:01:31', '2021-09-13 19:01:48', '1', '1', b'1');
INSERT INTO `role` VALUES ('2000000000000000014', '', '1', 'sodo', 'sodo', '', 3, '2021-09-13 23:33:25', '2021-09-13 23:33:25', '1', NULL, b'0');

-- ----------------------------
-- Table structure for role_to_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_to_menu`;
CREATE TABLE `role_to_menu`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_to_menu
-- ----------------------------
INSERT INTO `role_to_menu` VALUES ('017ad48fd01320363918158985118ea9', '1', '1123598815738675227', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('06e9b29963b4969dec243cf7a9a3ffff', '1', '1123598815738675248', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('08aa4c6934f9987d9c9101f5d369fee6', '1', '1123598815738675239', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('0ab4b0239603438aff60d655f6cbf90e', '1', '1123598815738675213', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('0be46daf5a2d4bb670912015514b9303', '1', '1164733399668962203', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('0f1e0d3536402fbd4cf3614437e4c911', '2', '1123598815738675204', '2021-08-22 14:19:56');
INSERT INTO `role_to_menu` VALUES ('12b5ffb21e33ad2261420254bfc632b6', '1', '1123598815738675260', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('157592e3f08d303502d1d2ed62aa2bc7', '1', '1123598815738675201', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('15ae302660010b5d55b32a0f78a84d5c', '1', '1123598815738675244', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('1b40cd689ed3b622ba0ccdc6558f57cd', '2', '1123598815738675201', '2021-08-22 14:19:56');
INSERT INTO `role_to_menu` VALUES ('1d331d956770e8ccbc361c98c74102e6', '1', '1123598815738675204', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('1eb25bb6b68d76a95bd03c6361f9c6bd', '1', '1123598815738675206', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('1f1faa54058f51c7d25de187915c96f3', '1', '1123598815738675241', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('23435d17523f6731ee2967fd50468bfd', '1', '1123598815738675249', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('23a50e4b6153eb061a3d098513217437', '1', '1123598815738675202', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('23ffb03fcc15ba38860076f3c2b46c2b', '1', '1123598815738675215', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('2486f88aebebdfe706221462ee746682', '1', '1123598815738675228', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('251e0d5603f15ee7abbbb1500f855bba', '1', '1123598815738675257', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('2598ce8ddab2fc5074fd6992fc42ac86', '1', '1123598815738675261', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('262c6de2775daac599f58a3c651b27f4', '1', '1164733389668962252', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('26f95a09d3fa890b7aee458805f87af4', '1', '1', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('2744dae5423e91b02abd10e3ea5e924b', '1', '1164733389668962254', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('2a8b18c9b63fb03b9821ffd7928b26c0', '1', '1123598815738675223', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('2d7fe469bd56bd9692ae2beaefb2e54d', '1', '1123598815738675217', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('2f73e02f0f9ef7419b7b4ff48caa2aa8', '1', '1123598815738675262', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('35976fc488de7a617536c9ab63068f31', '1', '15', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('3718e88c0e0f04c015b3f382ad725bc9', '1', '1164733399669962303', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('37a15bab016e1471cb1700b587b2ff91', '1', '1123598815738675270', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('39e7c2bc7dac1a9d906ccfa68b1ffad9', '1', '1123598815738675233', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('39f0f9929a2deab8d694ec2971b68371', '1', '1123598815738675205', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('3c14f69368bdf15ff69bb08c68e383f8', '1', '1123598815738675255', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('3c6ad843c14b541a1b3601bce745eb06', '1', '16', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('3c8f81cebe11b9e6355dcd03805c5337', '1', '1123598815738675252', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('3c9dbfb1e9ab0301480e26d79660d7f0', '1', '1123598815738675214', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('3f9dcc5dfb250778e7f73899e406bd8b', '1', '1123598815738675256', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('4676b1ca7644edcb58870dafd20e035e', '1', '1123598815738675212', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('4737a15ddc892bbad4581cfe3fb7b9a3', '1', '1123598815738675229', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('4953d508ad5051993e8ad5a49ea679c8', '1', '1123598815738675242', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('49b638eac9a682b2878e24ca2f3e5ef4', '1', '1123598815738675247', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('54950a841af721ba942d5ea71894e7db', '1', '1123598815738675236', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('5547b24734972776ef1b008b7b769687', '1', '1123598815738675245', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('5deb7d6902a38a5a8c4433348c9e48e6', '1', '1123598815738675207', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('621e5e2fa17a2bbcb66bcb4f9785cedf', '1', '1164733389668962253', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('623deb9bfc1f10b2421abf4bec28fc57', '1', '1164733399669962302', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('69697118fd8e300a47d438961fe5eb39', '1', '1164733399668962205', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('6c6e7bd5f22152a8bbf90d7695bca932', '1', '1123598815738675264', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('6da6e29bf1e42cfd3a527285560606ab', '1', '1123598815738675219', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('6ea2e6e5c6eaf8edbda984d200f36e29', '1', '1123598815738675225', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('6f81d5249a81b4ac2edeeab90dd3d420', '1', '1123598815738675254', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('729af729c98b82b1c7a4bf4fcc97ff90', '1', '1164733399669962301', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('732dc4d52674abccb316554d66c1ef3f', '1', '1123598815738675250', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('76323ded885af95b7e8cf0ab80cfc9e6', '1', '1123598815738675263', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('79b3906f3435adee297965db1e809bfa', '1', '1123598815738675231', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('7fe92faad670d4ce1a7a1872f2ade12b', '1', '1123598815738675218', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('809d4c5c285bce30c02d3722e057819c', '1', '1123598815738675267', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('80aa2251c15eab7236aeac7bfdfe87e3', '1', '1123598815738675203', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('84dcb58110ab295542c01ae1453cbb52', '1', '1164733399668962202', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('86364662db25d99cd1ff8959ec5a1791', '1', '13', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('87abaceb9945b6a55589c3bff0097ad0', '1', '1123598815738675253', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('8f2700d8e01710c594264e6cd86b69d5', '1', '1164733399668962204', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('9028a737bb8410c74b07ad6c23add449', '1', '1123598815738675211', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('917c88215346b93a86772b61f6cb7d4d', '2', '1123598815738675205', '2021-08-22 14:19:56');
INSERT INTO `role_to_menu` VALUES ('933e92f85be2d51bba42cbd97773a088', '1', '1123598815738675209', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('947c22d9f45f7d6b185f1b7170f28f1e', '1', '1164733399668962201', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('954f6834a042e9d65d3c0a86c23edca3', '1', '1123598815738675240', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('95c9e725e32b177613ca72fe0a169e2a', '1', '1123598815738675216', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('97be9adb78771f752d9becdff11d0061', '1', '1123598815738675251', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('990e110ae3e400fdc6618ee03353bad7', '1', '1123598815738675265', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('9c779166048d12159d1cd8ed9493d6d3', '1', '1123598815738675230', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('9e8efd603c7f68ac41824a8bd314cbb9', '1', '1164733389668962251', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('9ea4e5a2daebe8a786ba7883cc5f61ee', '1', '1123598815738675210', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('a1a10f9b370239f951fc1720ced840ee', '1', '1123598815738675238', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('a32124d1313c244d8a67178f345dfc7a', '1', '1123598815738675246', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('a7522c414d8a80d5d50429be53c16af9', '1', '1123598815738675266', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('a90c696d28c2ef4a8cdd00369aed5094', '1', '1123598815738675222', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('af936bef7a00a7680d0718f1f6e1e3c7', '2', '1123598815738675202', '2021-08-22 14:19:56');
INSERT INTO `role_to_menu` VALUES ('b55c8116ac7a268fd2e02b23b2717154', '1', '1164733399668962207', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('b81f69bf80277d841b15749db4db5162', '1', '1123598815738675269', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('b94af225710a6c248f42a6d0919adbed', '1', '1123598815738675220', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('bcd3dcd338d1eabb6f032b6495c495c7', '1', '1123598815738675224', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('bfc252117e328f2a894ca10d4e54ce43', '1', '1164733399668962206', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('c1e1afc7478eef286ed30e4dfb8905e3', '1', '1123598815738675208', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('c7f774e3e362ae8679584de50b765634', '1', '1123598815738675268', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('ca613d05497ffe946fbcbc54688dba0f', '1', '12', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('d15287ff48b6b74c1d4129da772b2432', '1', '1123598815738675221', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('df8b2ac47581ea2cc9e39ac4f22e9e63', '1', '1123598815738675259', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('e1fa7a758087c4d29f72a09286d0b8b0', '1', '2', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('e24c8aad8f0f7b3ed0035516b1b37efa', '1', '1123598815738675237', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('e4a7d3bd3864cb96b1602bac33134bd0', '1', '1164733389668962255', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('e521731b2b568b83f901dc92146335eb', '1', '1123598815738675234', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('e92cc32cc6c434121cd59d2f69067e9c', '1', '1123598815738675243', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('efaabf8068136c4b5f264b93f533582e', '1', '1123598815738675235', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('f0260ccd18bfa67d5e04d0244e1e77b4', '1', '11', '2021-09-02 18:50:54');
INSERT INTO `role_to_menu` VALUES ('f1a8c90644a03fca95ad3defb663a089', '1', '1123598815738675232', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('f7fb935244e23570d7542d5a3f1733eb', '2', '1123598815738675208', '2021-08-22 14:19:56');
INSERT INTO `role_to_menu` VALUES ('fdb17bb9f6d2fb6e33ea94fddf6ce4a2', '1', '1123598815738675226', '2021-09-02 18:50:55');
INSERT INTO `role_to_menu` VALUES ('fde7a4e8ee4db194ae687a3b55901894', '1', '1123598815738675258', '2021-09-02 18:50:55');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `schedule_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `object_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `open_hour` int(0) NULL DEFAULT NULL,
  `open_minute` int(0) NULL DEFAULT NULL,
  `close_hour` int(0) NULL DEFAULT NULL,
  `close_minute` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`schedule_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES ('1', '1', NULL, NULL, NULL, NULL, '2020-11-25 06:50:28', '2020-11-25 06:50:28');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `school_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `area_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `floor` int(0) NULL DEFAULT NULL,
  `stall` int(0) NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `background_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `goods_num` int(0) NULL DEFAULT 0,
  `notice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `least_price` decimal(10, 2) NULL DEFAULT NULL,
  `delivery_price` decimal(10, 2) NULL DEFAULT NULL,
  `score` double(4, 2) NULL DEFAULT 0.00,
  `orders_all` int(0) NULL DEFAULT 0,
  `orders_month` int(0) NULL DEFAULT 0,
  `orders_week` int(0) NULL DEFAULT 0,
  `orders_day` int(0) NULL DEFAULT 0,
  `income_all` decimal(10, 2) NULL DEFAULT 0.00,
  `income_month` decimal(10, 2) NULL DEFAULT 0.00,
  `income_week` decimal(10, 2) NULL DEFAULT 0.00,
  `income_day` decimal(10, 2) NULL DEFAULT 0.00,
  `account_balance` decimal(10, 2) NULL DEFAULT 0.00,
  `predestine` bit(1) NULL DEFAULT b'0',
  `pick` bit(1) NULL DEFAULT b'0',
  `delivery` bit(1) NULL DEFAULT b'0',
  `rank` int(0) NULL DEFAULT 0,
  `open` bit(1) NULL DEFAULT b'0',
  `valid` bit(1) NULL DEFAULT b'0',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`  (
  `category_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `school_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `shop_num` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_menu
-- ----------------------------
DROP TABLE IF EXISTS `shop_menu`;
CREATE TABLE `shop_menu`  (
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `goods_num` int(0) NULL DEFAULT 0,
  `sort` int(0) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_to_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_to_category`;
CREATE TABLE `shop_to_category`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `category_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户唯一表示码',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账号（小程序用户设为空）',
  `union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `school_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户密码（小程序用户为空）',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加密盐值',
  `status` int(0) NULL DEFAULT 2 COMMENT '-1-注销、0-正常、1-审核、2-冻结',
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `gender` int(0) NULL DEFAULT 0,
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `language` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL COMMENT '0-用户、1-商家、2-配送员',
  `login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `login_at` timestamp(0) NULL DEFAULT NULL COMMENT '登录时间',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', NULL, '1', 'admin', NULL, NULL, 'TimeChaser', '👣', 'https://thirdwx.qlogo.cn/mmopen/vi_32/FrAfaQn9DMaicrITAMbT7Bl9aZ8pKtOYIB0HCkRDdPozBY7ATetpUXw0RG31QIg6ahaXON71sYsia4fHt0hsL7mw/132', 'e78ed7ed59ab2fc38de6e1a10c0d07cd', 'cec9598a15229af3e1f2dd65573a5948', 0, '13188411111', '1872101334@qq.com', 2, '', NULL, NULL, NULL, NULL, NULL, '127.0.0.1', '2021-09-15 17:21:21', '2021-07-15 03:49:08', NULL, NULL, NULL, b'0');
INSERT INTO `user` VALUES ('2', '', '1', 'TimeChaser', 'ovIv5ws4nmKaGGQxKxVPt675WiUQ', NULL, 'test', '👣', 'https://thirdwx.qlogo.cn/mmopen/vi_32/FrAfaQn9DMaicrITAMbT7Bl9aZ8pKtOYIB0HCkRDdPozBY7ATetpUXw0RG31QIg6ahaXON71sYsia4fHt0hsL7mw/132', '27a63cd2bec6ac23dcd035d50e9043c9', '2d3967602793f4dd5df093a6549a251d', 0, '13388412222', 'zhangwei@irvingsoft.top', 2, '', NULL, NULL, NULL, NULL, NULL, '127.0.0.1', '2021-08-27 18:19:41', '2020-11-18 05:14:20', NULL, NULL, NULL, b'0');
INSERT INTO `user` VALUES ('3', '', '1', 'test', NULL, NULL, 'test', ':)SODO-2', NULL, '20d0a6c3deead479feb313a1e71f4a48', '491aa755c73cdeddedc7439329a42479', 2, '13972370000', 'irvingsoft@foxmail.com', 0, '', NULL, NULL, NULL, NULL, NULL, '127.0.0.1', '2021-08-27 18:19:41', '2021-08-27 19:25:22', NULL, '1', NULL, b'0');

-- ----------------------------
-- Table structure for user_to_role
-- ----------------------------
DROP TABLE IF EXISTS `user_to_role`;
CREATE TABLE `user_to_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_to_role
-- ----------------------------
INSERT INTO `user_to_role` VALUES ('42d4d292d6ad9a07710c5730498c0e73', '2', '1', '2021-09-16 15:01:17');
INSERT INTO `user_to_role` VALUES ('452963e7bcdab3aaa481dd6d969f86d0', 'asdakljdlkamsdklni2jq', '2', '2021-09-02 11:58:41');
INSERT INTO `user_to_role` VALUES ('7390ff1da300f3951e24a0b5c9335f46', 'ed92024f933e42c8908e1f112ed80620', '2', '2021-09-02 11:58:32');
INSERT INTO `user_to_role` VALUES ('7fb4ef979f9160d37683a29225fa55dc', '3', '1', '2021-09-16 15:01:24');
INSERT INTO `user_to_role` VALUES ('9d613f1a070151c5efd63bc59e715210', '96495827a745404093d33c143813ddd7', '2', '2021-08-27 18:11:25');
INSERT INTO `user_to_role` VALUES ('d313e9d2dd6d11c0bdcd0ccad91ca34e', '1', '1', '2021-09-16 15:01:10');
INSERT INTO `user_to_role` VALUES ('df8f1317254a691e55c93f809ebc8dc3', '1630057030747aebd3df730a4496fbfa94219cd61e781', '2', '2021-08-27 17:37:11');

SET FOREIGN_KEY_CHECKS = 1;
