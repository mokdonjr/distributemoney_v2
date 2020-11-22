package com.seungchan.distributemoney_v2.reserveReceive.mapper;

import com.seungchan.distributemoney_v2.reserveReceive.data.ObjectDataReserveReceive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataReserveReceiveMapper {

    ObjectDataReserveReceive selectObjectDataReserveReceive(@Param("reserveReceiveId") long reserveReceiveId);
    ObjectDataReserveReceive selectObjectDataReserveReceiveForUpdate(@Param("reserveReceiveId") long reserveReceiveId);
    ObjectDataReserveReceive selectObjectDataReserveReceiveToProcess();
    int insertObjectDataReserveReceive(@Param("objectDataReserveReceive") ObjectDataReserveReceive objectDataReserveReceive);
    int upsertObjectDataReserveReceive(@Param("objectDataReserveReceive") ObjectDataReserveReceive objectDataReserveReceive);
    int insertObjectDataReserveReceiveList(@Param("objectDataReserveReceiveList") List<ObjectDataReserveReceive> objectDataReserveReceiveList);
    int upsertObjectDataReserveReceiveList(@Param("objectDataReserveReceiveList") List<ObjectDataReserveReceive> objectDataReserveReceiveList);
    int updateObjectDataReserveReceive(@Param("objectDataReserveReceive") ObjectDataReserveReceive objectDataReserveReceive);
    int deleteObjectDataReserveReceive(@Param("reserveReceiveId") long reserveReceiveId);
}
