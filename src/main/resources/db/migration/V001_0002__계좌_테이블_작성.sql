CREATE TABLE IF NOT EXISTS `ACCOUNT` (
  `bank_id` INT(11) NOT NULL COMMENT '은행 고유 식별자',
  `bank_account_number` VARCHAR(20) NOT NULL COMMENT '해당 은행 계좌번호',
  `balance` BIGINT(20) NOT NULL COMMENT '은행 계좌 잔액',
  `last_update_at` DATETIME NOT NULL COMMENT '가장 최근 잔액 변경 시각',
  `mod_balance` BIGINT(20) NOT NULL COMMENT '가장 최근 잔액 변화량',
  `create_at` DATETIME NOT NULL COMMENT '은행 계좌 등록 시각',
  `is_block` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '은행 계좌 정지 여부',
  `block_at` DATETIME NULL COMMENT '은행 계좌 정지 시각',
  `is_delete` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '은행 계좌 삭제 여부',
  `delete_at` DATETIME NULL COMMENT '은행 계좌 삭제 시각',
  PRIMARY KEY (`bank_id`, `bank_account_number`))
COMMENT = '계좌';
