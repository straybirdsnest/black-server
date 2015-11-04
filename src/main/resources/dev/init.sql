DROP DATABASE IF EXISTS BLACK_SERVER;
CREATE DATABASE BLACK_SERVER;
USE BLACK_SERVER;

CREATE TABLE T_COLLEGE (
  `id`       INT PRIMARY KEY AUTO_INCREMENT,
  `name`     VARCHAR(30),
  `name_ext` VARCHAR(30),
  `logo`     MEDIUMBLOB,
  `location` VARCHAR(200)
);

CREATE TABLE T_ACADEMY (
  `id`         INT PRIMARY KEY AUTO_INCREMENT,
  `name`       VARCHAR(30),
  `college_id` INT,
  `logo`       MEDIUMBLOB
);

CREATE TABLE T_PAGE (
  `id` INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE T_GROUP (
  `id`      INT PRIMARY KEY AUTO_INCREMENT,
  `name`    VARCHAR(50),
  `intro`   VARCHAR(200),
  `logo`    MEDIUMBLOB,
  `page_id` INT,
  FOREIGN KEY (`page_id`) REFERENCES T_PAGE (id)
);

CREATE TABLE T_USER (
  `id`            INT PRIMARY KEY AUTO_INCREMENT,
  `phone`         VARCHAR(20),
  `email`         VARCHAR(100),
  `nickname`      VARCHAR(20),
  `realname`      VARCHAR(20),
  `idcard`        VARCHAR(18),
  `enabled`       BOOLEAN,
  `gender`        ENUM('MALE', 'FEMALE', 'SECRET'),
  `college_id`    INT,
  `academy_id`    INT,
  `avatar`        MEDIUMBLOB,
  `birthday`      DATE,
  `reg_time`      TIMESTAMP,
  `reg_ip`        VARCHAR(39),
  `reg_longitude` DOUBLE PRECISION(9, 6),
  `reg_latitude`  DOUBLE PRECISION(9, 6),
  `group_id`      INT,
  FOREIGN KEY (`college_id`) REFERENCES T_COLLEGE (id),
  FOREIGN KEY (`academy_id`) REFERENCES T_ACADEMY (id),
  FOREIGN KEY (`group_id`) REFERENCES T_GROUP (id)
);

CREATE TABLE T_ACTIVITY (
  `id`          INT PRIMARY KEY AUTO_INCREMENT,
  `start_time`  DATETIME,
  `end_time`    DATETIME,
  `location`    VARCHAR(100),
  `promoter_id` INT,
  `content`     TEXT,
  `type`        ENUM('MATCH', 'BLACK'),
  `status`      ENUM('READY', 'RUNNING', 'STOPPED'),
  `remarks`     TEXT,
  `group_id`    INT,
  FOREIGN KEY (`promoter_id`) REFERENCES T_USER (id),
  FOREIGN KEY (`group_id`) REFERENCES T_GROUP (id)
);

CREATE TABLE T_FRIENDSHIP (
  `user_a` INT,
  `user_b` INT,
  FOREIGN KEY (`user_a`) REFERENCES T_USER (id),
  FOREIGN KEY (`user_b`) REFERENCES T_USER (id)
);

CREATE TABLE T_SUBSCRIPTION (
  `broadcaster_id` INT,
  `subscriber_id`  INT,
  FOREIGN KEY (`broadcaster_id`) REFERENCES T_USER (id),
  FOREIGN KEY (`subscriber_id`) REFERENCES T_USER (id)
);

CREATE TABLE T_MEMBERSHIP (
  `group_id`  INT,
  `member_id` INT,
  `type`      ENUM('member', 'op', 'speaker'),
  FOREIGN KEY (`group_id`) REFERENCES T_GROUP (id),
  FOREIGN KEY (`member_id`) REFERENCES T_USER (id)
);

CREATE TABLE T_MESSAGE (
  `id`             BIGINT PRIMARY KEY AUTO_INCREMENT,
  `content`        VARCHAR(1000),
  `sender_id`      INT,
  `sender_time`    TIMESTAMP,
  `visibility`     INT,
  `visibility_ext` VARCHAR(200),
  `extenstion`     VARCHAR(200),
  FOREIGN KEY (`sender_id`) REFERENCES T_USER (id)
);

CREATE TABLE T_NETBAR (
  `id`       INT PRIMARY KEY AUTO_INCREMENT,
  `name`     VARCHAR(30),
  `logo`     MEDIUMBLOB,
  `location` VARCHAR(200)
);

# 插入数据


