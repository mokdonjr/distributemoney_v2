package com.seungchan.distributemoney_v2.roomUser.mapper;

import com.seungchan.distributemoney_v2.roomUser.data.ObjectDataRoomUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataRoomUserMapper {

    ObjectDataRoomUser selectObjectDataRoomUser(@Param("roomId") String roomId, @Param("userId") String userId);
    ObjectDataRoomUser selectObjectDataRoomUserForUpdate(@Param("roomId") String roomId, @Param("userId") String userId);
    List<ObjectDataRoomUser> selectObjectDataRoomUserListByRoomId(@Param("roomId") String roomId);
    int insertObjectDataRoomUser(@Param("objectDataRoomUser") ObjectDataRoomUser objectDataRoomUser);
    int upsertObjectDataRoomUser(@Param("objectDataRoomUser") ObjectDataRoomUser objectDataRoomUser);
    int insertObjectDataRoomUserList(@Param("objectDataRoomUserList") List<ObjectDataRoomUser> objectDataRoomUserList);
    int upsertObjectDataRoomUserList(@Param("objectDataRoomUserList") List<ObjectDataRoomUser> objectDataRoomUserList);
    int updateObjectDataRoomUser(@Param("objectDataRoomUser") ObjectDataRoomUser objectDataRoomUser);
    int deleteObjectDataRoomUser(@Param("roomId") String roomId, @Param("userId") String userId);
}
