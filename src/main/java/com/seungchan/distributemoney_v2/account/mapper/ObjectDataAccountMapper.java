package com.seungchan.distributemoney_v2.account.mapper;

import com.seungchan.distributemoney_v2.account.data.ObjectDataAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ObjectDataAccountMapper {

    ObjectDataAccount selectObjectDataAccount(@Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
    ObjectDataAccount selectObjectDataAccountForUpdate(@Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
    int insertObjectDataAccount(@Param("objectDataAccount") ObjectDataAccount objectDataAccount);
    int upsertObjectDataAccount(@Param("objectDataAccount") ObjectDataAccount objectDataAccount);
    int insertObjectDataAccountList(@Param("objectDataAccountList") List<ObjectDataAccount> objectDataAccountList);
    int upsertObjectDataAccountList(@Param("objectDataAccountList") List<ObjectDataAccount> objectDataAccountList);
    int updateObjectDataAccount(@Param("objectDataAccount") ObjectDataAccount objectDataAccount);
    int deleteObjectDataAccount(@Param("bankId") int bankId, @Param("bankAccountNumber") String bankAccountNumber);
}
