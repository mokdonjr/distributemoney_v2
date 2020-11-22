package com.seungchan.distributemoney_v2.room.mapper;

import com.seungchan.distributemoney_v2.room.data.ObjectDataRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataRoomMapper {

    ObjectDataRoom selectObjectDataRoom(@Param("roomId") String roomId);
    ObjectDataRoom selectObjectDataRoomForUpdate(@Param("roomId") String roomId);
    int insertObjectDataRoom(@Param("objectDataRoom") ObjectDataRoom objectDataRoom);
    int upsertObjectDataRoom(@Param("objectDataRoom") ObjectDataRoom objectDataRoom);
    int insertObjectDataRoomList(@Param("objectDataRoomList") List<ObjectDataRoom> objectDataRoomList);
    int upsertObjectDataRoomList(@Param("objectDataRoomList") List<ObjectDataRoom> objectDataRoomList);
    int updateObjectDataRoom(@Param("objectDataRoom") ObjectDataRoom objectDataRoom);
    int deleteObjectDataRoom(@Param("roomId") String roomId);
}
