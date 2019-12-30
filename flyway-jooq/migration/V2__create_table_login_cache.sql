create table login_cache(
  ID                BIGINT(20)  NOT NULL PRIMARY KEY AUTO_INCREMENT,
  USER_ID           BIGINT(20)  NOT NULL,
  USER_TOKEN        VARCHAR(50) NOT NULL,
  TIME_EXPIRY       BIGINT(20)  NOT NULL,
  TIME_LAST_LOGIN   BIGINT(20)  NOT NULL,
  USER_TOKEN_STATUS CHAR(1)     NOT NULL             DEFAULT 'V',

  INDEX index__user_id (USER_ID),
  INDEX index__user_token (USER_TOKEN)
)