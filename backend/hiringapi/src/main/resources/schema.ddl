CREATE SCHEMA if not exists hrms;

SET DATABASE SQL SYNTAX MYS TRUE;
--SET MODE MySQL;
--USE hrms;

CREATE TABLE if not exists candidate (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  resumelink VARCHAR(45) NOT NULL,
  referral BOOLEAN DEFAULT FALSE,
  referrer VARCHAR(45) NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE if not exists position (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(25) NOT NULL,
  skills VARCHAR(45) NULL,
  experienceRange VARCHAR(10) NULL,
  hiringManager VARCHAR(30) NULL,
  priority SMALLINT(1) NULL
);

CREATE TABLE if not exists applicant (
  position_id INT NOT NULL,
  candidate_id INT NOT NULL,
  state varchar(15) NOT NULL,
  applied_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  processed_on timestamp NULL DEFAULT NULL,
  processed_by varchar(45) DEFAULT NULL,
  CONSTRAINT applicantuniq UNIQUE  (position_id,candidate_id),
  CONSTRAINT candidateFK1 FOREIGN KEY (candidate_id) REFERENCES candidate (id),
  CONSTRAINT positionFK1 FOREIGN KEY (position_id) REFERENCES position (id)
);

  
CREATE TABLE if not exists interview (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  position_id INT NOT NULL,
  candidate_id INT NOT NULL,
  state varchar(15) NOT NULL,
  slot timestamp NULL DEFAULT NULL,
  panel varchar(100) DEFAULT NULL,
  scheduled_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  scheduled_by varchar(45) DEFAULT NULL,
  CONSTRAINT candidateFK2 FOREIGN KEY (candidate_id) REFERENCES candidate (id),
  CONSTRAINT positionFK2 FOREIGN KEY (position_id) REFERENCES position (id)
);