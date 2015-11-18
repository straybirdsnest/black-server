DROP DATABASE IF EXISTS BLACK_SERVER;
CREATE DATABASE BLACK_SERVER;
USE BLACK_SERVER;

CREATE TABLE T_IMAGE (
  `id`    BIGINT PRIMARY KEY AUTO_INCREMENT,
  `data`  LONGBLOB,
  `flags` INT(32),
  `uid`   INT(32),
  `gid`   BIGINT(64),
  `hash`  VARCHAR(128),
  `used`  INT,
  `tags`  VARCHAR(30)
);

CREATE TABLE T_COLLEGE (
  `id`       INT PRIMARY KEY AUTO_INCREMENT,
  `name`     VARCHAR(30),
  `name_ext` VARCHAR(30),
  `logo_id`  BIGINT,
  `location` VARCHAR(200),
  FOREIGN KEY (`logo_id`) REFERENCES T_IMAGE (id)
);

CREATE TABLE T_ACADEMY (
  `id`         INT PRIMARY KEY AUTO_INCREMENT,
  `name`       VARCHAR(30),
  `college_id` INT,
  `logo_id`    BIGINT,
  FOREIGN KEY (`logo_id`) REFERENCES T_IMAGE (id)
);

CREATE TABLE T_GAME (
  `id`      INT PRIMARY KEY AUTO_INCREMENT,
  `name`    VARCHAR(30),
  `logo_id` BIGINT,
  FOREIGN KEY (`logo_id`) REFERENCES T_IMAGE (id)
);

CREATE TABLE T_PAGE (
  `id` INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE T_GROUP (
  `id`      BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name`    VARCHAR(50),
  `intro`   VARCHAR(200),
  `logo_id` BIGINT,
  `page_id` INT,
  FOREIGN KEY (`page_id`) REFERENCES T_PAGE (id),
  FOREIGN KEY (`logo_id`) REFERENCES T_IMAGE (id)
)
  AUTO_INCREMENT = 100;

CREATE TABLE T_USER (
  `id`                  INT PRIMARY KEY AUTO_INCREMENT,
  `username`            VARCHAR(20),
  `nickname`            VARCHAR(20),
  `enabled`             BOOLEAN,
  `email`               VARCHAR(100),
  `gender`              ENUM('MALE', 'FEMALE', 'SECRET'),
  `birthday`            DATE,
  `phone`               VARCHAR(20),
  `signature`           VARCHAR(255),
  `hometown`            VARCHAR(40),
  `highschool`          VARCHAR(40),
  `grade`               VARCHAR(20),
  `realname`            VARCHAR(20),
  `idcard`              VARCHAR(18),
  `reg_time`            TIMESTAMP,
  `reg_ip`              VARCHAR(39),
  `reg_longitude`       DOUBLE PRECISION(9, 6),
  `reg_latitude`        DOUBLE PRECISION(9, 6),
  `avatar_id`           BIGINT,
  `background_image_id` BIGINT,
  `college_id`          INT,
  `academy_id`          INT,
  FOREIGN KEY (`college_id`) REFERENCES T_COLLEGE (id),
  FOREIGN KEY (`academy_id`) REFERENCES T_ACADEMY (id),
  FOREIGN KEY (`background_image_id`) REFERENCES T_IMAGE (id)
)
  AUTO_INCREMENT = 100;

CREATE TABLE T_ACTIVITY (
  `id`                    INT PRIMARY KEY AUTO_INCREMENT,
  `cover_image_id`        BIGINT,
  `start_time`            DATETIME,
  `end_time`              DATETIME,
  `registration_deadline` DATETIME,
  `location`              VARCHAR(100),
  `promoter_id`           INT,
  `title`                 TEXT,
  `content`               TEXT,
  `game_id`               INT,
  `type`                  ENUM('MATCH', 'BLACK'),
  `status`                ENUM('READY', 'RUNNING', 'STOPPED'),
  `group_id`              BIGINT,
  FOREIGN KEY (`promoter_id`) REFERENCES T_USER (id),
  FOREIGN KEY (`group_id`) REFERENCES T_GROUP (id),
  FOREIGN KEY (`cover_image_id`) REFERENCES T_IMAGE (id),
  FOREIGN KEY (`game_id`) REFERENCES T_GAME (id)
);

CREATE TABLE T_ACTIVITY_IMAGE (
  `activity_id` INT,
  `image_id`    BIGINT,
  FOREIGN KEY (`activity_id`) REFERENCES T_ACTIVITY (id),
  FOREIGN KEY (`image_id`) REFERENCES T_IMAGE (id)
);

CREATE TABLE T_FRIENDSHIP (
  `id`           INT PRIMARY KEY AUTO_INCREMENT,
  `user_id`      INT,
  `friend_id`    INT,
  `friend_alias` VARCHAR(30),
  FOREIGN KEY (`user_id`) REFERENCES T_USER (id),
  FOREIGN KEY (`friend_id`) REFERENCES T_USER (id)
);

CREATE TABLE T_SUBSCRIPTION (
  `broadcaster_id` INT,
  `subscriber_id`  INT,
  FOREIGN KEY (`broadcaster_id`) REFERENCES T_USER (id),
  FOREIGN KEY (`subscriber_id`) REFERENCES T_USER (id)
);

CREATE TABLE T_MEMBERSHIP (
  `id`          INT PRIMARY KEY AUTO_INCREMENT,
  `group_id`    BIGINT,
  `member_id`   INT,
  `type`        ENUM('MEMBER', 'OP', 'SPEAKER'),
  `nickname`    VARCHAR(30),
  `group_alias` VARCHAR(50),
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
  `logo_id`  BIGINT,
  `location` VARCHAR(200),
  FOREIGN KEY (`logo_id`) REFERENCES T_IMAGE (id)
);

# 注意这份脚本不插入测试数据, 测试数据全部放到 test_data.yaml 文件当中