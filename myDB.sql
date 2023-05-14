-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- DROP SCHEMA IF EXISTS mydb;

-----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `userId` VARCHAR(20),
  `userPass` VARCHAR(50) NOT NULL,
  `userName` VARCHAR(20) NOT NULL,
  `userSex` INT NOT NULL,
  `userHeight` INT NULL,
  `userWeight` INT NULL,
  `startKcal` INT NOT NULL,
  `remainKcal` INT NOT NULL,
  `targetWeight` INT NULL,
  `userImg` VARCHAR(50) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`friend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `friend` ;

CREATE TABLE IF NOT EXISTS `friend` (
  `userId` VARCHAR(20) NOT NULL,
  `friendId` VARCHAR(20) NULL,
  PRIMARY KEY (`userId`),
  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  FOREIGN KEY (`friendId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board` ;

CREATE TABLE IF NOT EXISTS `board` (
  `boardId` INT AUTO_INCREMENT,
  `userId` VARCHAR(20) NOT NULL,
  `boardContent` VARCHAR(300) NOT NULL,
  `createTime` DATE NOT NULL,
  `updateTime` DATE NULL,
  `boardImg` VARCHAR(50) NULL,
  `viewCnt` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`boardId`),
  INDEX `userId_idx` (`userId` ASC) VISIBLE,
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review` ;

CREATE TABLE IF NOT EXISTS `review` (
  `reviewId` INT AUTO_INCREMENT,
  `userId` VARCHAR(20) NOT NULL,
  `boardId` INT NOT NULL,
  `reviewContent` VARCHAR(100) NOT NULL,
  `createTime` DATE NOT NULL,
  `updateTime` DATE NULL,
  PRIMARY KEY (`reviewId`),
  INDEX `userId_idx` (`userId` ASC) VISIBLE,
  INDEX `boardId_idx` (`boardId` ASC) VISIBLE,
    FOREIGN KEY (`userId`)
    REFERENCES `user` (`userId`)
    ON DELETE CASCADE,
    FOREIGN KEY (`boardId`)
    REFERENCES `board` (`boardId`)
    ON DELETE CASCADE
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`mealplan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mealplan` ;

CREATE TABLE IF NOT EXISTS `mealplan` (
  `userId` VARCHAR(20) NOT NULL,
  `menuName` VARCHAR(45) NOT NULL,
  `menuKcal` INT NOT NULL,
  `carbo` INT NULL,
  `protein` INT NULL,
  `fat` INT NULL,
  `timeToEat` DATE NOT NULL,
  `mealImg` VARCHAR(50) NULL,
  PRIMARY KEY (`userId`),
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`likeBoad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `likeBoad` ;

CREATE TABLE IF NOT EXISTS `likeBoad` (
  `likeId` INT AUTO_INCREMENT,
  `userId` VARCHAR(20) NOT NULL,
  `boardId` INT NOT NULL,
  PRIMARY KEY (`likeId`),
  INDEX `userId_idx` (`userId` ASC) VISIBLE,
  INDEX `boardId_idx` (`boardId` ASC) VISIBLE,
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
    FOREIGN KEY (`boardId`)
    REFERENCES `mydb`.`board` (`boardId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`foodAPI`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foodAPI` ;

CREATE TABLE IF NOT EXISTS `foodAPI` (
  `NUM` INT NOT NULL,
  `foodName` VARCHAR(45) NULL,
  `servingSize` DOUBLE NULL,
  `kcal` DOUBLE NULL,
  `carbo` DOUBLE NULL,
  `protein` DOUBLE NULL,
  `fat` DOUBLE NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB;


select * from foodAPI
WHERE num > 90000;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
