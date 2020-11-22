package com.seungchan.distributemoney_v2.account.service;

import com.seungchan.distributemoney_v2.account.data.ObjectDataAccount;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceAccountTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceAccount objectServiceAccount;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceAccount;
    }

    @Override
    protected IObjectData getDefaultObjectData() {
        var data = new ObjectDataAccount();
        data.setBankId(defaultBankId);
        data.setBankAccountNumber(defaultBankAccountNumber);
        data.setCreateAt(DateUtil.getCurrentDate());
        return data;
    }
}