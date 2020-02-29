CREATE TABLE `hrms`.`candidate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `resumelink` VARCHAR(45) NOT NULL,
  `referral` BOOLEAN DEFAULT FALSE,
  `referrer` VARCHAR(45) NULL,
  `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `cid` (`id` ASC) );

  CREATE TABLE `hrms`.`position` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NULL,
  `skills` VARCHAR(45) NULL,
  `experienceRange` VARCHAR(10) NULL,
  `hiringManager` VARCHAR(30) NULL,
  `priority` SMALLINT(1) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `hrms`.`applicant` (
  `position_id` int NOT NULL,
  `candidate_id` int NOT NULL,
  `state` varchar(15) NOT NULL,
  `applied_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `processed_on` timestamp NULL DEFAULT NULL,
  `processed_by` varchar(45) DEFAULT NULL,
  UNIQUE KEY `applicant` (`position_id`,`candidate_id`),
  KEY `candidateFK1_idx` (`candidate_id`),
  KEY `positionFK1_idx` (`position_id`),
  CONSTRAINT `candidateFK1` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `positionFK1` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB

CREATE TABLE `hrms`.`interview` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `position_id` int NOT NULL,
  `candidate_id` int NOT NULL,
  `state` varchar(15) NOT NULL,
  `slot` timestamp NULL DEFAULT NULL,
  `panel` varchar(100) DEFAULT NULL,
  `scheduled_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `scheduled_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `candidateFK2_idx` (`candidate_id`),
  KEY `positionFK2_idx` (`position_id`),
  CONSTRAINT `candidateFK2` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `positionFK2` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB