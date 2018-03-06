-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema shopdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shopdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shopdb` DEFAULT CHARACTER SET utf8 ;
USE `shopdb` ;

-- -----------------------------------------------------
-- Table `shopdb`.`product_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopdb`.`product_types` (
  `type_id` INT NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE INDEX `TypeNameUnique` (`type_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `shopdb`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopdb`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_brand` VARCHAR(20) NOT NULL,
  `product_name` VARCHAR(100) NOT NULL,
  `price` FLOAT NOT NULL,
  `type_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`product_id`),
  INDEX `type_id_idx` (`type_id` ASC),
  CONSTRAINT `type_id`
    FOREIGN KEY (`type_id`)
    REFERENCES `shopdb`.`product_types` (`type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `shopdb`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopdb`.`customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `second_name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `post_code` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `telephone` INT NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `telephone_UNIQUE` (`telephone` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopdb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopdb`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `customer_id_idx` (`customer_id` ASC),
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shopdb`.`customers` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopdb`.`order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopdb`.`order_items` (
  `order_items_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  INDEX `order_id_idx` (`order_id` ASC),
  INDEX `product_id_idx` (`product_id` ASC),
  PRIMARY KEY (`order_items_id`),
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopdb`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `shopdb`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
