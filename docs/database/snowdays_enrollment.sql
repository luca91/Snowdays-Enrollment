SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP DATABASE IF EXISTS `snowdays_enrollment` ;
CREATE DATABASE IF NOT EXISTS `snowdays_enrollment` DEFAULT CHARACTER SET utf8;
USE `snowdays_enrollment`;

DROP TABLE IF EXISTS `snowdays_enrollment`.`users` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_surname` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(30) NOT NULL,
  `user_birthday` DATE DEFAULT NULL,
  `user_email` VARCHAR(45) NOT NULL UNIQUE,
  `user_role` VARCHAR(15) NOT NULL,
  `user_username` VARCHAR(20) NOT NULL,
  `user_group` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`user_email` ASC),
  UNIQUE INDEX `username_UNIQUE` (`user_username` ASC),
  UNIQUE INDEX `id_UNIQUE` (`user_id` ASC),
  INDEX `fk_roles_user_username_idx` (`user_username` ASC))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`roles` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`roles` (
  `role_name` VARCHAR(15) NOT NULL,
  `user_username` VARCHAR(20) NOT NULL,
  `group_assigned` BOOLEAN NOT NULL DEFAULT FALSE,
   CONSTRAINT `fk_roles_user_username`
    FOREIGN KEY (`user_username`)
    REFERENCES `snowdays_enrollment`.`users` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `snowdays_enrollment`.`groups` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) NOT NULL UNIQUE,
  `group_referent_id` INT NOT NULL,
  `group_max_participants` INT NOT NULL DEFAULT 15,
  `group_country` VARCHAR(20) NOT NULL,
  `group_is_blocked` BOOLEAN DEFAULT TRUE,
  `group_actual_participants_number` INT DEFAULT 0,
  `group_badge_type` VARCHAR(15),
  `group_saturday` VARCHAR(15) DEFAULT NULL,
  `group_approved` BOOLEAN NOT NULL DEFAULT FALSE,
  `group_first_participant_registered_id` INT DEFAULT NULL,
  PRIMARY KEY (`group_id`, `group_name`),
  INDEX `fk_groups_group_referent_idx` (`group_referent_id` ASC),
  INDEX `fk_groups_name_idx` (`group_name` ASC),
  CONSTRAINT `fk_groups_group_referent`
    FOREIGN KEY (`group_referent_id`)
    REFERENCES `snowdays_enrollment`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`programs` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`programs` (
  `program_id` INT NOT NULL AUTO_INCREMENT,
  `program_day` VARCHAR(45) NOT NULL,
  `program_description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`program_id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`rental_options` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`rental_options` (
  `rent_option_id` INT NOT NULL AUTO_INCREMENT,
  `rent_option_description` VARCHAR(30),
  PRIMARY KEY (`rent_option_id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`participants` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`participants` (
  `participant_id` INT NOT NULL AUTO_INCREMENT,
  `participant_name` VARCHAR(30) NOT NULL,
  `participant_surname` VARCHAR(30) NOT NULL,
  `participant_group_id` INT NOT NULL,
  `participant_gender` VARCHAR(10) NOT NULL,
  `participant_friday_program` INT NOT NULL,
  `participant_intolerance` VARCHAR(80) NULL,
  `participant_t_shirt_size` VARCHAR(15) NOT NULL,
  `participant_approved` BOOLEAN NOT NULL DEFAULT FALSE,
  `participant_rental_option_id` INT NOT NULL,
  `participant_email` VARCHAR(45) DEFAULT NULL,
  `participant_birthday` DATE, 
  `participant_photo` VARCHAR(40) DEFAULT NULL,
  `participant_registration_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `participant_document` VARCHAR(40) DEFAULT NULL,
  `participant_birthplace` VARCHAR(30) DEFAULT NULL,
  `participant_address` VARCHAR(100) DEFAULT NULL,
  `participant_city` VARCHAR(30) DEFAULT NULL,
  `participant_country` VARCHAR(30) DEFAULT NULL,
  `participant_birth_country` VARCHAR(30) DEFAULT NULL,
  `participant_zip` VARCHAR(15) DEFAULT NULL,
  `participant_phone` VARCHAR(15) DEFAULT NULL,
  PRIMARY KEY (`participant_id`, `participant_registration_time`),
  INDEX `fk_participants_friday_program_idx` (`participant_friday_program` ASC),
  INDEX `fk_participants_group_idx` (`participant_group_id` ASC),
  INDEX `fk_participants_rental_option_idx` (`participant_rental_option_id` ASC),
  INDEX `fk_participants_registration_time_idx` (`participant_registration_time` ASC),
  CONSTRAINT `fk_participants_friday_program`
    FOREIGN KEY (`participant_friday_program`)
    REFERENCES `snowdays_enrollment`.`programs` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_participants_group`
    FOREIGN KEY (`participant_group_id`)
    REFERENCES `snowdays_enrollment`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_participants_rental_option`
    FOREIGN KEY (`participant_rental_option_id`)
    REFERENCES `snowdays_enrollment`.`rental_options` (`rent_option_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`registrations` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`registrations` (
  `registration_id` INT NOT NULL AUTO_INCREMENT, 
  `registration_participant_id` INT NOT NULL,
  `registration_participant_group_id` INT NOT NULL,
  `registration_participant_group_name` VARCHAR(45) NOT NULL,
  `registration_participant_registration_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`registration_id`),
  INDEX `fk_registration_participant_id_idx` (`registration_participant_id` ASC),
  INDEX `fk_registrations_participant_registration_time_idx` (`registration_participant_registration_time` ASC),
  INDEX `fk_registrations_participant_group_id_idx` (`registration_participant_group_id` ASC),
  INDEX `fk_registrations_participant_group_name_idx` (`registration_participant_group_name` ASC),
  CONSTRAINT `fk_registrations_participant_registration_time`
    FOREIGN KEY (`registration_participant_registration_time`)
    REFERENCES `snowdays_enrollment`.`participants` (`participant_registration_time`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_registrations_participant_id`
    FOREIGN KEY (`registration_participant_id`)
    REFERENCES `snowdays_enrollment`.`participants` (`participant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_registrations_participant_group_id`
    FOREIGN KEY (`registration_participant_group_id`)
    REFERENCES `snowdays_enrollment`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_registrations_participant_group_name`
    FOREIGN KEY (`registration_participant_group_name`)
    REFERENCES `snowdays_enrollment`.`groups` (`group_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `snowdays_enrollment`.`faculties` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`faculties` (
  `faculty_id` INT NOT NULL AUTO_INCREMENT,
  `faculty_name` VARCHAR(30) NOT NULL,
  `faculty_server_name` VARCHAR(40) NOT NULL,
   PRIMARY KEY (`faculty_id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`emails_internals` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`emails_internals` (
  `email` VARCHAR(60) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `status` VARCHAR(10),
  `group` VARCHAR(10) NOT NULL,
  `link` VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`settings`;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`settings` (
  `setting_name` VARCHAR(40) NOT NULL UNIQUE,
  `setting_value` VARCHAR(40) DEFAULT NULL)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`countries`;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`countries` (
  `country_name` VARCHAR(30) NOT NULL UNIQUE,
  `country_max_people` INT DEFAULT 0,
  `country_actual_people` INT DEFAULT 0)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;