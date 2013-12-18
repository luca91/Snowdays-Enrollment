SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP DATABASE IF EXISTS `snowdays_enrollment` ;
CREATE DATABASE IF NOT EXISTS `snowdays_enrollment` DEFAULT CHARACTER SET utf8;
USE `snowdays_enrollment`;

DROP TABLE IF EXISTS `snowdays_enrollment`.`roles` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`roles` (
  `role_name` VARCHAR(15) NOT NULL,
  `user_email` VARCHAR(45) NOT NULL)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`users` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_surname` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(30) NOT NULL,
  `user_birthday` DATE NULL,
  `user_email` VARCHAR(45) NOT NULL UNIQUE,
  `user_role` VARCHAR(15) NOT NULL,
  `user_username` VARCHAR(20),
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`user_email` ASC),
  UNIQUE INDEX `id_UNIQUE` (`user_id` ASC))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`groups` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) NOT NULL,
  `group_referent_id` INT NOT NULL,
  `group_max_participants` INT NOT NULL,
  `group_country` VARCHAR(20) NOT NULL,
  `group_is_blocked` BOOLEAN NOT NULL,
  `group_actual_participants_number` INT NOT NULL,
  `group_first_participant_regstered_id` INT NOT NULL,
  PRIMARY KEY (`group_id`, `group_name`),
  INDEX `fk_groups_group_referent_idx` (`group_referent_id` ASC),
  INDEX `fk_groups_first_participant_registered_id_idx` (`group_first_participant_regstered_id` ASC),
  INDEX `fk_groups_name_idx` (`group_name` ASC),
  CONSTRAINT `fk_groups_group_referent`
    FOREIGN KEY (`group_referent_id`)
    REFERENCES `snowdays_enrollment`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_groups_first_participant_registered_id`
    FOREIGN KEY (`group_first_participant_regstered_id`)
    REFERENCES `snowdays_enrollment`.`participants` (`participant_id`)
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
  `rent_option_ski_boots` BOOLEAN NOT NULL,
  `rent_option_skis` BOOLEAN NOT NULL,
  `rent_option_snowboard_boots` BOOLEAN NOT NULL,
  `rent_option_snowboard` BOOLEAN NOT NULL,
  PRIMARY KEY (`rent_option_id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `snowdays_enrollment`.`participants` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`participants` (
  `participant_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `participant_name` VARCHAR(30) NOT NULL,
  `participant_surname` VARCHAR(30) NOT NULL,
  `participant_group_id` INT NOT NULL,
  `participant_university_id` INT NOT NULL,
  `participant_gender` CHAR(1) NOT NULL,
  `participant_friday_program` INT NOT NULL,
  `participant_saturday_program` INT NOT NULL,
  `participant_intolerance` VARCHAR(80) NULL,
  `participant_t_shirt_size` CHAR(1) NOT NULL,
  `participant_approved` BOOLEAN NOT NULL,
  `participant_rental_option_id` INT NOT NULL,
  `participant_registration_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`participant_id`, `participant_registration_time`),
  INDEX `fk_participants_friday_program_idx` (`participant_friday_program` ASC),
  INDEX `fk_participants_saturday_program_idx` (`participant_saturday_program` ASC),
  INDEX `fk_participants_group_idx` (`participant_group_id` ASC),
  INDEX `fk_participants_rental_option_idx` (`participant_rental_option_id` ASC),
  INDEX `fk_participants_registration_time_idx` (`participant_registration_time` ASC),
  CONSTRAINT `fk_participants_friday_program`
    FOREIGN KEY (`participant_friday_program`)
    REFERENCES `snowdays_enrollment`.`programs` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_participants_saturday_program`
    FOREIGN KEY (`participant_saturday_program`)
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

DROP TABLE IF EXISTS `snowdays_enrollment`.`group_registrations` ;
CREATE TABLE IF NOT EXISTS `snowdays_enrollment`.`group_registrations` (
  `group_registration_id` INT NOT NULL AUTO_INCREMENT,
  `group_registration_group_id` INT NOT NULL,
  `group_registration_time` TIMESTAMP NOT NULL,
  `group_registration_group_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`group_registration_id`),
  INDEX `fk_group_registrations_group_id_idx` (`group_registration_group_id` ASC),
  INDEX `fk_group_registrations_group_name_idx` (`group_registration_group_name` ASC),
  CONSTRAINT `fk_group_registrations_group_id`
    FOREIGN KEY (`group_registration_group_id`)
    REFERENCES `snowdays_enrollment`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_registrations_group_name`
    FOREIGN KEY (`group_registration_group_name`)
    REFERENCES `snowdays_enrollment`.`groups` (`group_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;