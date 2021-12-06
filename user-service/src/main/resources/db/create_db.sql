SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
        
-- -----------------------------------------------------
-- Schema user_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `user_db`;
CREATE SCHEMA IF NOT EXISTS `user_db` DEFAULT CHARACTER SET utf8;
USE `user_db`;
-- -----------------------------------------------------
-- Table 'user_db'.'skill'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_db`.`skill`;
CREATE TABLE IF NOT EXISTS `skill`(
	`id` 	BIGINT,
    `name` 	VARCHAR(50) 	NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE
);
-- -----------------------------------------------------
-- Table 'user_db'.'user_'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_db`.`user_`;
CREATE TABLE IF NOT EXISTS `user_`(
	`id`			BIGINT,
	`first_name`	VARCHAR(50) 	NOT NULL,
    `last_name`		VARCHAR(50)		NOT NULL,
    `email`			VARCHAR(50) 	NOT NULL,
    `job_function`	VARCHAR(30)		NOT NULL,
    `is_employee` 	TINYINT			NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE
);
-- -----------------------------------------------------
-- Table 'user_db'.'user_details'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_db`.`user_details`;
CREATE TABLE IF NOT EXISTS `user_details`(
	`user_id`		BIGINT,
	`country`		VARCHAR(50),
    `city`			VARCHAR(50),
    `street`		VARCHAR(50),
    `house`			VARCHAR(30),
    `date_of_birth` DATETIME,
    CONSTRAINT `user_id_pk` PRIMARY KEY (`user_id`),
    UNIQUE INDEX `user_id_uq` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_details_user_fk` FOREIGN KEY (`user_id`)
		REFERENCES `user_db`.`user_` (`id`)
);
-- -----------------------------------------------------
-- Table 'user_db'.'user_skills'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_db`.`user_skills`;
CREATE TABLE IF NOT EXISTS `user_skills`(
	`user_id`		BIGINT			NOT NULL,
	`skill_id`		BIGINT			NOT NULL,
    `level` 		VARCHAR(30)		NOT NULL,
    CONSTRAINT `user_skill_id_pk` PRIMARY KEY (`user_id`, `skill_id`),
    INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `skill_id_fk_idx` (`skill_id` ASC) VISIBLE,
    CONSTRAINT `user_has_skill_user_fk` FOREIGN KEY (`user_id`)
		REFERENCES `user_db`.`user_` (`id`),
    CONSTRAINT `user_has_skill_skill_fk` FOREIGN KEY (`skill_id`)
		REFERENCES `user_db`.`skill` (`id`)
);


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;