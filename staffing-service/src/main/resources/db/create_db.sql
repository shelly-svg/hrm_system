SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema staffing_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `staffing_db`;
CREATE SCHEMA IF NOT EXISTS `staffing_db` DEFAULT CHARACTER SET utf8;
USE `staffing_db`;
-- -----------------------------------------------------
-- Table 'staffing_db'.'project'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`project`;
CREATE TABLE IF NOT EXISTS `project`
(
    `id`         BIGINT AUTO_INCREMENT,
    `name`       VARCHAR(50) NOT NULL,
    `contact_id` BIGINT      NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'project_skills'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`project_skills`;
CREATE TABLE IF NOT EXISTS `project_skills`
(
    `project_id` BIGINT NOT NULL,
    `skill_id`   BIGINT NOT NULL,
    CONSTRAINT `project_skill_id_pk` PRIMARY KEY (`project_id`, `skill_id`),
    INDEX `project_id_fk_idx` (`project_id` ASC) VISIBLE,
    INDEX `skill_id_fk_idx` (`skill_id` ASC) VISIBLE,
    CONSTRAINT `project_skills_project_fk` FOREIGN KEY (`project_id`)
        REFERENCES `staffing_db`.`project` (`id`)
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'position'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`position`;
CREATE TABLE IF NOT EXISTS `position`
(
    `id`            BIGINT AUTO_INCREMENT,
    `project_id`    BIGINT      NOT NULL,
    `job_function`  VARCHAR(50) NOT NULL,
    `work_format`   VARCHAR(50) NOT NULL,
    `english_level` VARCHAR(15) NOT NULL,
    `description`   TEXT        NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE,
    CONSTRAINT `position_project_fk` FOREIGN KEY (`project_id`)
        REFERENCES `staffing_db`.`project` (`id`)
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'opportunity'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`opportunity`;
CREATE TABLE IF NOT EXISTS `opportunity`
(
    `id`                     BIGINT AUTO_INCREMENT,
    `position_id`            BIGINT       NOT NULL,
    `candidate_id`           BIGINT       NOT NULL,
    `status`                 VARCHAR(30)  NOT NULL,
    `cv_file_name`           VARCHAR(100) NOT NULL,
    `cover_letter_file_name` VARCHAR(100) NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE,
    CONSTRAINT `opportunity_position_fk` FOREIGN KEY (`position_id`)
        REFERENCES `staffing_db`.`position` (`id`)
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'position_skills'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`position_skills`;
CREATE TABLE IF NOT EXISTS `position_skills`
(
    `position_id` BIGINT      NOT NULL,
    `skill_id`    BIGINT      NOT NULL,
    `level`       VARCHAR(30) NOT NULL,
    CONSTRAINT `position_skill_id_pk` PRIMARY KEY (`position_id`, `skill_id`),
    INDEX `position_id_fk_idx` (`position_id` ASC) VISIBLE,
    INDEX `skill_id_fk_idx` (`skill_id` ASC) VISIBLE,
    CONSTRAINT `position_has_skill_position_fk` FOREIGN KEY (`position_id`)
        REFERENCES `staffing_db`.`position` (`id`)
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'interview'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`interview`;
CREATE TABLE IF NOT EXISTS `interview`
(
    `id`             BIGINT AUTO_INCREMENT,
    `opportunity_id` BIGINT       NOT NULL,
    `time`           DATETIME     NOT NULL,
    `details`        VARCHAR(200) NOT NULL,
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE,
    CONSTRAINT `interview_opportunity_fk` FOREIGN KEY (`opportunity_id`)
        REFERENCES `staffing_db`.`opportunity` (`id`)
);
-- -----------------------------------------------------
-- Table 'staffing_db'.'feedback'
-- -----------------------------------------------------
DROP TABLE IF EXISTS `staffing_db`.`feedback`;
CREATE TABLE IF NOT EXISTS `feedback`
(
    `id`                     BIGINT AUTO_INCREMENT,
    `interview_id`           BIGINT       NOT NULL,
    `soft_skills`            VARCHAR(400) NOT NULL,
    `hard_skills`            VARCHAR(400) NOT NULL,
    `strengths`              VARCHAR(255) NOT NULL,
    `weaknesses`             VARCHAR(255) NOT NULL,
    `additional_information` VARCHAR(400),
    CONSTRAINT `id_pk` PRIMARY KEY (`id`),
    UNIQUE INDEX `id_uq` (`id` ASC) VISIBLE,
    CONSTRAINT `feedback_interview_fk` FOREIGN KEY (`interview_id`)
        REFERENCES `staffing_db`.`interview` (`id`)
);

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;