DROP SCHEMA IF EXISTS otus;
CREATE SCHEMA otus;
USE otus;
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
  id   BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255),
  age  INT(3)     NOT NULL
);