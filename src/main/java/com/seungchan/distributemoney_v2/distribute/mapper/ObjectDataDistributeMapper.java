package com.seungchan.distributemoney_v2.distribute.mapper;

import com.seungchan.distributemoney_v2.distribute.data.ObjectDataDistribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ObjectDataDistributeMapper {

    ObjectDataDistribute selectObjectDataDistribute(@Param("roomId") String roomId, @Param("token") String token);
    ObjectDataDistribute selectObjectDataDistributeForUpdate(@Param("roomId") String roomId, @Param("token") String token);
    // TODO : idx-end 인덱스 추가!
    ObjectDataDistribute selectObjectDataDistributeToEnd(@Param("date") Date date);
    int insertObjectDataDistribute(@Param("objectDataDistribute") ObjectDataDistribute objectDataDistribute);
    int upsertObjectDataDistribute(@Param("objectDataDistribute") ObjectDataDistribute objectDataDistribute);
    int insertObjectDataDistributeList(@Param("objectDataDistributeList") List<ObjectDataDistribute> objectDataDistributeList);
    int upsertObjectDataDistributeList(@Param("objectDataDistributeList") List<ObjectDataDistribute> objectDataDistributeList);
    int updateObjectDataDistribute(@Param("objectDataDistribute") ObjectDataDistribute objectDataDistribute);
    int deleteObjectDataDistribute(@Param("roomId") String roomId, @Param("token") String token);
}
