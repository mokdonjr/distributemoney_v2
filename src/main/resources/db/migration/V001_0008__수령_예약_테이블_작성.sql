CREATE TABLE IF NOT EXISTS `RESERVE_RECEIVE` (
  `reserve_receive_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '예약 고유번호',
  `room_id` VARCHAR(255) NOT NULL COMMENT '뿌린 방',
  `user_id` VARCHAR(255) NOT NULL COMMENT '뿌린 유저',
  `token` CHAR(3) NOT NULL COMMENT '뿌리기 토큰',
  `create_at` DATETIME NOT NULL COMMENT '예약 시각',
  `is_end` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '예약 처리 완료 여부',
  `end_at` DATETIME NULL COMMENT '예약 처리 완료된 시각',
  PRIMARY KEY (`reserve_receive_id`))
COMMENT = '수령 예약';
