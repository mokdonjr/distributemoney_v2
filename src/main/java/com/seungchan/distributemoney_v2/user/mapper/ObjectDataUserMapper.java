package com.seungchan.distributemoney_v2.user.mapper;

import com.seungchan.distributemoney_v2.user.data.ObjectDataUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataUserMapper {

    ObjectDataUser selectObjectDataUser(@Param("userId") String userId);
    ObjectDataUser selectObjectDataUserForUpdate(@Param("userId") String userId);
    int insertObjectDataUser(@Param("objectDataUser") ObjectDataUser objectDataUser);
    int upsertObjectDataUser(@Param("objectDataUser") ObjectDataUser objectDataUser);
    int insertObjectDataUserList(@Param("objectDataUserList") List<ObjectDataUser> objectDataUserList);
    int upsertObjectDataUserList(@Param("objectDataUserList") List<ObjectDataUser> objectDataUserList);
    int updateObjectDataUser(@Param("objectDataUser") ObjectDataUser objectDataUser);
    int deleteObjectDataUser(@Param("userId") String userId);
}
