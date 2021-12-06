SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema auth_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `auth_db`;
CREATE SCHEMA IF NOT EXISTS `auth_db` DEFAULT CHARACTER SET utf8;
USE `auth_db`;
-- -----------------------------------------------------
-- Table 'auth_db'.'user_'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auth_db`.`user_`;
CREATE TABLE IF NOT EXISTS `user_`
(
    `id`        BIGINT AUTO_INCREMENT,
    `username`  VARCHAR(50)  NOT NULL COLLATE utf8mb4_0900_as_cs,
    `password_` VARCHAR(100) NOT NULL,
    `status_`   VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
    `role_`     VARCHAR(30)  NOT NULL DEFAULT 'USER',
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE,
    UNIQUE INDEX `username_uq` (`username` ASC) VISIBLE
);

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;