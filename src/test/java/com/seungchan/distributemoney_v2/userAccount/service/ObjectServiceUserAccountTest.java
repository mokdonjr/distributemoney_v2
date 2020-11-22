package com.seungchan.distributemoney_v2.userAccount.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.userAccount.data.ObjectDataUserAccount;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceUserAccountTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceUserAccount objectServiceUserAccount;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceUserAccount;
    }

    @Override
    protected IObjectData getDefaultObjectData() {
        var data = new ObjectDataUserAccount();
        data.setUserId(defaultUserId);
        data.setBankId(defaultBankId);
        data.setBankAccountNumber(defaultBankAccountNumber);
        data.setCreateAt(DateUtil.getCurrentDate());
        return data;
    }

}