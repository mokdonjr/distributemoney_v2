package com.seungchan.distributemoney_v2.account.service;

import com.seungchan.distributemoney_v2.account.data.ObjectDataAccount;
import com.seungchan.distributemoney_v2.account.data.key.ObjectKeyAccount;
import com.seungchan.distributemoney_v2.account.mapper.ObjectDataAccountMapper;
import com.seungchan.distributemoney_v2.account.param.ParamAccount;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectServiceAccount extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataAccountMapper objectDataAccountMapper;

    @Override
    public ObjectDataAccount selectData(IObjectKey objectKey) {
        ObjectKeyAccount key = (ObjectKeyAccount) objectKey;
        return objectDataAccountMapper.selectObjectDataAccount(key.getBankId(), key.getBankAccountNumber());
    }

    public ObjectDataAccount selectData(int bankId, String bankAccountNumber) {
        ObjectKeyAccount key = new ObjectKeyAccount();
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        return selectData(key);
    }

    @Override
    public ObjectDataAccount selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyAccount key = (ObjectKeyAccount) objectKey;
        return objectDataAccountMapper.selectObjectDataAccountForUpdate(key.getBankId(), key.getBankAccountNumber());
    }

    public ObjectDataAccount selectDataWithLock(int bankId, String bankAccountNumber) {
        ObjectKeyAccount key = new ObjectKeyAccount();
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataAccount insertData(IObjectData objectData) {
        ObjectDataAccount data = (ObjectDataAccount) objectData;
        objectDataAccountMapper.insertObjectDataAccount(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataAccount data = (ObjectDataAccount) objectData;
        objectDataAccountMapper.updateObjectDataAccount(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyAccount key = (ObjectKeyAccount) objectKey;
        objectDataAccountMapper.deleteObjectDataAccount(key.getBankId(), key.getBankAccountNumber());
    }

    public void deleteData(int bankId, String bankAccountNumber) {
        ObjectKeyAccount key = new ObjectKeyAccount();
        key.setBankId(bankId);
        key.setBankAccountNumber(bankAccountNumber);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamAccount paramAccount = (ParamAccount) param;

        if (paramAccount.getBankType() == null)
            throw new BusinessException("은행 정보가 없습니다.");

        if (paramAccount.getBankAccountNumber() == null)
            throw new BusinessException("계좌번호 정보를 알 수 없습니다.");
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataAccount beforeObjectDataAccount = (ObjectDataAccount) before;
        if (beforeObjectDataAccount.getIsBlock())
            throw new BusinessException("정지된 은행 계좌입니다.");

        if (beforeObjectDataAccount.getIsDelete())
            throw new BusinessException("해지된 은행 계좌입니다.");

        ObjectDataAccount objectDataAccount = (ObjectDataAccount) object;
        if (objectDataAccount.getBalance() == null)
            throw new BusinessException("은행 계좌의 잔액을 알 수 없어 갱신이 불가능합니다.");

        if (objectDataAccount.getBalance() < 0)
            throw new BusinessException("은행 계좌에 잔액이 부족합니다.");
    }

    @Override
    public ObjectDataAccount createObjectInstanceDefault(IParamData param) {
        ParamAccount paramAccount = (ParamAccount) param;
        var data = new ObjectDataAccount();
        data.setBankId(paramAccount.getBankType().getBankId());
        data.setBankAccountNumber(paramAccount.getBankAccountNumber());
        return data;
    }
}
