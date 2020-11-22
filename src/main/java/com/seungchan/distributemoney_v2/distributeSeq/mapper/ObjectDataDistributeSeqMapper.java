package com.seungchan.distributemoney_v2.distributeSeq.mapper;

import com.seungchan.distributemoney_v2.distributeSeq.data.ObjectDataDistributeSeq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataDistributeSeqMapper {

    ObjectDataDistributeSeq selectObjectDataDistributeSeq(@Param("roomId") String roomId, @Param("token") String token, @Param("seq") int seq);
    ObjectDataDistributeSeq selectObjectDataDistributeSeqForUpdate(@Param("roomId") String roomId, @Param("token") String token, @Param("seq") int seq);
    ObjectDataDistributeSeq selectObjectDataDistributeSeqToReceive(@Param("roomId") String roomId, @Param("token") String token);
    int selectCountDistributeSeqToReceive(@Param("roomId") String roomId, @Param("token") String token);
    List<ObjectDataDistributeSeq> selectObjectDataDistributeSeqAll(@Param("roomId") String roomId, @Param("token") String token);
    List<ObjectDataDistributeSeq> selectObjectDataDistributeSeqAllForUpdate(@Param("roomId") String roomId, @Param("token") String token);
    int insertObjectDataDistributeSeq(@Param("objectDataDistributeSeq") ObjectDataDistributeSeq objectDataDistributeSeq);
    int upsertObjectDataDistributeSeq(@Param("objectDataDistributeSeq") ObjectDataDistributeSeq objectDataDistributeSeq);
    int insertObjectDataDistributeSeqList(@Param("objectDataDistributeSeqList") List<ObjectDataDistributeSeq> objectDataDistributeSeqList);
    int upsertObjectDataDistributeSeqList(@Param("objectDataDistributeSeqList") List<ObjectDataDistributeSeq> objectDataDistributeSeqList);
    int updateObjectDataDistributeSeq(@Param("objectDataDistributeSeq") ObjectDataDistributeSeq objectDataDistributeSeq);
    int deleteObjectDataDistributeSeq(@Param("roomId") String roomId, @Param("token") String token, @Param("seq") int seq);
}
