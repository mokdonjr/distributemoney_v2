CREATE TABLE IF NOT EXISTS `USER` (
  `user_id` VARCHAR(255) NOT NULL COMMENT '계정 고유 식별자',
  `user_name` VARCHAR(45) NOT NULL COMMENT '계정 이름',
  `balance` BIGINT(20) NOT NULL COMMENT '페이머니 잔액',
  `last_update_at` DATETIME NOT NULL COMMENT '가장 최근 페이머니 잔액 변경 시각',
  `mod_balance` BIGINT(20) NOT NULL COMMENT '가장 최근 페이머니 잔액 변화량',
  `create_at` DATETIME NOT NULL COMMENT '계정 생성 시각',
  `is_block` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '계정 정지 여부',
  `block_at` DATETIME NULL COMMENT '계정 정지 시각',
  `is_delete` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '계정 삭제 여부',
  `delete_at` DATETIME NULL COMMENT '계정 삭제 시각',
  PRIMARY KEY (`user_id`))
COMMENT = '계정';