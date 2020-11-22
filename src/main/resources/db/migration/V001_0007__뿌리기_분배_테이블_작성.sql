CREATE TABLE IF NOT EXISTS `DISTRIBUTE_SEQ` (
  `room_id` VARCHAR(255) NOT NULL COMMENT '뿌린 방',
  `token` CHAR(3) NOT NULL COMMENT '뿌리기 토큰',
  `user_id` VARCHAR(255) NOT NULL COMMENT '뿌린 유저',
  `seq` INT(11) NOT NULL COMMENT '뿌리기 분배 구분자',
  `value` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '랜덤 분배된 금액',
  `receive_user_id` VARCHAR(255) NULL COMMENT '수령한 유저',
  `is_receive` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '수령 여부',
  `receive_at` DATETIME NULL COMMENT '수령 시각',
  PRIMARY KEY (`room_id`, `token`, `seq`))
COMMENT = '뿌리기 분배';
