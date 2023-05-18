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
  `userId` VARCHAR(40),
  `userPass` VARCHAR(100) NOT NULL,
  `userName` VARCHAR(40) NOT NULL,
  `userSex` INT NOT NULL,
  `exerciseType` VARCHAR(20) NOT NULL,
  `startKcal` INT NOT NULL,
  `remainKcal` INT NOT NULL,
  `userImg` VARCHAR(300) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`userImg`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `userImg` (
	`fileName` VARCHAR(300) PRIMARY KEY,
    `type` VARCHAR(100) NOT NULL,
    `imageData` LONGBLOB NOT NULL
);


-- -----------------------------------------------------
-- Table `mydb`.`friend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `friend` ;

CREATE TABLE IF NOT EXISTS `friend` (
  `userId` VARCHAR(40) NOT NULL,
  `friendId` VARCHAR(40) NULL,
  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  FOREIGN KEY (`friendId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board` ;

CREATE TABLE IF NOT EXISTS `board` (
  `boardId` INT AUTO_INCREMENT,
  `userId` VARCHAR(40) NOT NULL,
  `boardTitle` VARCHAR(100) NOT NULL,
  `boardWriter` VARCHAR(100) NOT NULL,
  `boardContent` VARCHAR(1000) NOT NULL,
  `createTime` DATE NOT NULL,
  `updateTime` DATE NULL,
  `viewCnt` INT NOT NULL DEFAULT 0,
  `boardImg` VARCHAR(300) NULL,
  PRIMARY KEY (`boardId`),
  FOREIGN KEY (`userId`)
  REFERENCES `mydb`.`user` (`userId`)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review` ;

CREATE TABLE IF NOT EXISTS `review` (
  `reviewId` INT AUTO_INCREMENT,
  `userId` VARCHAR(40) NOT NULL,
  `boardId` INT NOT NULL,
  `writer` VARCHAR(40) NOT NULL,
  `reviewContent` VARCHAR(300) NOT NULL,
  `createTime` DATE NOT NULL,
  `updateTime` DATE NULL,
  PRIMARY KEY (`reviewId`),
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
  `mealId` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(40) NOT NULL,
  `menuName` VARCHAR(50) NOT NULL,
  `menuKcal` DOUBLE NOT NULL,
  `carbo` DOUBLE NULL,
  `protein` DOUBLE NULL,
  `fat` DOUBLE NULL,
  `eatingTime` DATE NOT NULL,
  `mealImg` VARCHAR(100) NULL,
  PRIMARY KEY (`mealId`),
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`likeBoad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `likeBoard` ;

CREATE TABLE IF NOT EXISTS `likeBoard` (
  `likeId` INT AUTO_INCREMENT,
  `userId` VARCHAR(40) NOT NULL,
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
  `foodName` VARCHAR(50) NULL,
  `servingSize` DOUBLE NULL,
  `kcal` DOUBLE NULL,
  `carbo` DOUBLE NULL,
  `protein` DOUBLE NULL,
  `fat` DOUBLE NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB;


select * from foodAPI;
select * from user;
select * from userImg;
select * from friend;

CREATE TABLE test (
	`date` DATE
);

INSERT INTO test 
VALUES (now());

INSERT INTO test
VALUES ("2018-05-18");

SELECT * FROM test;



-- 남자/여자 유산소/근력 맨몸/기구 루틴
insert into user(userId, userPass, userName, userSex, exerciseType, startKcal, remainKcal)
VALUES("test33", "test123", "testUser", 1, "남자 유산소 맨몸 루틴", 3000, 2000), 
("ddd123", "test123", "testUser", 1, "남자 유산소 맨몸 루틴", 3000, 2000);

insert into board(boardId, userId, boardTitle, boardWriter, boardContent, createTime)
VALUES (0, "test", "testTitle", "testWriter", "testContent", now());

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
