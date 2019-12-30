CREATE TABLE `user` (
  `ID`                BIGINT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `NAME`              VARCHAR(50) NOT NULL,
  `MOBILE`            VARCHAR(20)          DEFAULT NULL,
  `TIME_CREATED`      BIGINT(20)  NOT NULL,
  `TIME_UPDATED`      BIGINT(20)  NOT NULL,
  UNIQUE KEY `unique_index__user_mobile` (`MOBILE`)
);