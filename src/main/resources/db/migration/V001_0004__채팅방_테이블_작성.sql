CREATE TABLE IF NOT EXISTS `ROOM` (
  `room_id` VARCHAR(255) NOT NULL,
  `room_name` VARCHAR(45) NOT NULL COMMENT '방 이름',
  `max_user_count` INT(11) NOT NULL COMMENT '방 최대 참가가능한 인원수',
  `create_at` DATETIME NOT NULL COMMENT '방 생성 시각',
  `is_delete` TINYINT(1) NOT NULL,
  `delete_at` DATETIME NULL,
  PRIMARY KEY (`room_id`))
COMMENT = '채팅방';