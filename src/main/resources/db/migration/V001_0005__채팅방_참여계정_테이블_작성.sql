CREATE TABLE IF NOT EXISTS `ROOM_USER` (
  `room_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `create_at` DATETIME NOT NULL COMMENT '참가 시각',
  `is_delete` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '나가기 여부',
  `delete_at` DATETIME NULL COMMENT '나간 시각',
  PRIMARY KEY (`room_id`, `user_id`))
COMMENT = '채팅방 참가 유저';
