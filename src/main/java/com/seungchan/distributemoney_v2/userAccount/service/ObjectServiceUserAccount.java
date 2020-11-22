package com.seungchan.distributemoney_v2.userAccount.service;

import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import com.seungchan.distributemoney_v2.account.param.ParamAccount;
import com.seungchan.distributemoney_v2.account.service.ObjectServiceAccount;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.userAccount.data.ObjectDataUserAccount;
import com.seungchan.distributemoney_v2.userAccount.data.key.ObjectKeyUserAccount;
import com.seungchan.distributemoney_v2.userAccount.mapper.ObjectDataUserAccountMapper;
import com.seungchan.distributemoney_v2.userAccount.param.ParamUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 계정 계좌 연동 서비스
 */
@Service
public class ObjectServiceUserAccount extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataUserAccountMapper objectDataUserAccountMapper;
    @Autowired
    private ObjectServiceAccount objectServiceAccount;

    @Override
    public ObjectDataUserAccount selectData(IObjectKey objectKey) {
        ObjectKeyUserAccount key = (ObjectKeyUserAccount) objectKey;
        return objectDataUserAccountMapper.selectObjectDataUserAccount(key.getUserId(), key.getBankId(), key.getBankAccountNumber());
    }

    public ObjectDataUserAccount selectData(String userId, int bankId, String bankAccountNumber) {
        ObjectKeyUserAccount key = new ObjectKeyUserAccount();
        key.setUserId(userId);
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        return selectData(key);
    }

    public List<ObjectDataUserAccount> selectDataByUserId(String userId) {
        return objectDataUserAccountMapper.selectObjectDataUserAccountByUserId(userId);
    }

    @Override
    public ObjectDataUserAccount selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyUserAccount key = (ObjectKeyUserAccount) objectKey;
        return objectDataUserAccountMapper.selectObjectDataUserAccountForUpdate(key.getUserId(), key.getBankId(), key.getBankAccountNumber());
    }

    public ObjectDataUserAccount selectDataWithLock(String userId, int bankId, String bankAccountNumber) {
        ObjectKeyUserAccount key = new ObjectKeyUserAccount();
        key.setUserId(userId);
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataUserAccount insertData(IObjectData objectData) {
        ObjectDataUserAccount data = (ObjectDataUserAccount) objectData;
        objectDataUserAccountMapper.insertObjectDataUserAccount(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataUserAccount data = (ObjectDataUserAccount) objectData;
        objectDataUserAccountMapper.updateObjectDataUserAccount(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyUserAccount key = (ObjectKeyUserAccount) objectKey;
        objectDataUserAccountMapper.deleteObjectDataUserAccount(key.getUserId(), key.getBankId(), key.getBankAccountNumber());
    }

    public void deleteData(String userId, int bankId, String bankAccountNumber) {
        ObjectKeyUserAccount key = new ObjectKeyUserAccount();
        key.setUserId(userId);
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamUserAccount paramUserAccount = (ParamUserAccount) param;

        if (paramUserAccount.getUserId() == null)
            throw new BusinessException("유저 고유번호가 입력되지 않았습니다.");

        if (paramUserAccount.getBankType() == null)
            throw new BusinessException("은행 식별자가 입력되지 않았습니다.");

        if (paramUserAccount.getBankAccountNumber() == null)
            throw new BusinessException("은행 계좌번호가 입력되지 않았습니다.");
    }

    @Override
    public ObjectDataUserAccount createObjectInstanceDefault(IParamData param) {
        ParamUserAccount paramUserAccount = (ParamUserAccount) param;
        var data = new ObjectDataUserAccount();
        data.setUserId(paramUserAccount.getUserId());
        data.setBankId(paramUserAccount.getBankType().getBankId());
        data.setBankAccountNumber(paramUserAccount.getBankAccountNumber());
        return data;
    }

    @Override
    protected void afterCreateObject(IParamData param, IObjectData object) throws BusinessException {
        ObjectDataUserAccount objectDataUserAccount = (ObjectDataUserAccount) object;

        // 계정 연동 후 자동으로 계정 정보를 저장
        ParamAccount paramAccount = new ParamAccount();
        paramAccount.setBankAccountNumber(objectDataUserAccount.getBankAccountNumber());
        paramAccount.setBankType(BankType.valueOf(objectDataUserAccount.getBankId()));
        objectServiceAccount.createObjectDefault(paramAccount);
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataUserAccount beforeObjectUserAccount = (ObjectDataUserAccount) before;
        if (beforeObjectUserAccount.getIsBlock())
            throw new BusinessException("연동이 정지된 계좌입니다.");

        if (beforeObjectUserAccount.getIsDelete())
            throw new BusinessException("연동이 해제된 계좌입니다.");

        ObjectDataUserAccount objectDataUserAccount = (ObjectDataUserAccount) object;
    }
}
