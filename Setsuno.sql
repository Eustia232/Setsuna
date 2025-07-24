-- 如果不存在数据库 Setsuna，则创建
CREATE DATABASE IF NOT EXISTS `Setsuna` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 0;

-- 如果存在 user 表则先删除
DROP TABLE IF EXISTS `user`;

-- 创建 user 表
CREATE TABLE `user`
(
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`   VARCHAR(50) COMMENT '用户名',
    `password`   VARCHAR(100) NULL COMMENT '密码',
    `login_type` VARCHAR(20) DEFAULT 'local' COMMENT '登录方式',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_login_type_username` (`login_type`, `username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 插入若干数据
INSERT INTO `user` (`username`, `password`)
VALUES ('yukino', '{noop}123'),
       ('eustia', '{noop}123'),
       ('kotori', '{noop}123'),
       ('alan', '{noop}123'),
       ('eva', '{noop}123');

INSERT INTO `user` (`id`, `username`, `password`)
VALUES (66, 'saki', '{noop}123');

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info`
(
    `id`      INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` INT UNSIGNED NOT NULL COMMENT '用户ID，关联user表',
    `hobby`   VARCHAR(255) DEFAULT NULL COMMENT '用户爱好',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    CONSTRAINT `fk_user_info_user_id`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

-- 插入若干数据
INSERT INTO `user_info` (`user_id`, `hobby`)
VALUES (66, '篮球');
-- 补全剩下数据
INSERT INTO user_info (user_id, hobby, avatar)
SELECT id, NULL, NULL
FROM user
WHERE id NOT IN (SELECT user_id FROM user_info);


SET FOREIGN_KEY_CHECKS = 1;