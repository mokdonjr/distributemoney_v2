CREATE TABLE IF NOT EXISTS `USER_ACCOUNT` (
  `user_id` VARCHAR(255) NOT NULL COMMENT '계정 고유 식별자',
  `bank_id` INT(11) NOT NULL COMMENT '은행 고유 식별자',
  `bank_account_number` CHAR(20) NOT NULL COMMENT '해당 은행 계좌번호',
  `create_at` DATETIME NOT NULL COMMENT '계좌 연동 시각',
  `is_block` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '계좌 연동 정지 여부',
  `block_at` DATETIME NULL COMMENT '계좌 연동 정지 시각',
  `is_delete` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '계좌 연동 해지 여부',
  `delete_at` DATETIME NULL COMMENT '계좌 연동 해지 시각',
  PRIMARY KEY (`user_id`, `bank_id`, `bank_account_number`))
COMMENT = '계좌 연동';
