CREATE TABLE IF NOT EXISTS `DISTRIBUTE` (
  `room_id` VARCHAR(255) NOT NULL COMMENT '뿌린 방',
  `token` CHAR(3) NOT NULL COMMENT '뿌리기 토큰',
  `user_id` VARCHAR(255) NOT NULL COMMENT '뿌린 유저',
  `total_value` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '뿌리기 총 금액',
  `seq_count` INT(11) NULL COMMENT '뿌리기 분배 수',
  `create_at` DATETIME NOT NULL COMMENT '뿌린 시각',
  `is_end` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '뿌리기 종료 여부',
  `end_at` DATETIME NULL COMMENT '뿌리기 종료 시각',
  PRIMARY KEY (`room_id`, `token`))
COMMENT = '뿌리기';
