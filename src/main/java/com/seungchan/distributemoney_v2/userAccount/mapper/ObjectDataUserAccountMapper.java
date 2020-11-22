package com.seungchan.distributemoney_v2.userAccount.mapper;

import com.seungchan.distributemoney_v2.userAccount.data.ObjectDataUserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataUserAccountMapper {

    ObjectDataUserAccount selectObjectDataUserAccount(@Param("userId") String userId, @Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
    ObjectDataUserAccount selectObjectDataUserAccountForUpdate(@Param("userId") String userId, @Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
    // TODO : idx-user 인덱스 추가
    List<ObjectDataUserAccount> selectObjectDataUserAccountByUserId(@Param("userId") String userId);
    int insertObjectDataUserAccount(@Param("objectDataUserAccount") ObjectDataUserAccount objectDataUserAccount);
    int upsertObjectDataUserAccount(@Param("objectDataUserAccount") ObjectDataUserAccount objectDataUserAccount);
    int insertObjectDataUserAccountList(@Param("objectDataUserAccountList") List<ObjectDataUserAccount> objectDataUserAccountList);
    int upsertObjectDataUserAccountList(@Param("objectDataUserAccountList") List<ObjectDataUserAccount> objectDataUserAccountList);
    int updateObjectDataUserAccount(@Param("objectDataUserAccount") ObjectDataUserAccount objectDataUserAccount);
    int deleteObjectDataUserAccount(@Param("userId") String userId, @Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
}
