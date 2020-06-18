/*
 Navicat Premium Data Transfer

 Source Server         : sz-oracle
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : geosocial.ink:1521
 Source Schema         : FBPT

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 18/05/2020 15:54:57
*/


-- ----------------------------
-- Table structure for TEST_A
-- ----------------------------
DROP TABLE "FBPT"."TEST_A1";
CREATE TABLE "FBPT"."TEST_A1" (
  "COLUMN_1" VARCHAR2(100 BYTE),
  "COLUMN_2" VARCHAR2(100 BYTE) DEFAULT NULL,
  "COLUMN_3" TIMESTAMP(6) WITH TIME ZONE DEFAULT NULL
)
TABLESPACE "FBPT_DATA"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON TABLE "FBPT"."TEST_A1" IS '测试表，没用';
