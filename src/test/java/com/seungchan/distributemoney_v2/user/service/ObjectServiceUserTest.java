package com.seungchan.distributemoney_v2.user.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.user.data.ObjectDataUser;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceUserTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceUser objectServiceUser;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceUser;
    }

    @Override
    protected IObjectData getDefaultObjectData() {
        var data = new ObjectDataUser();
        data.setUserId(defaultUserId);
        data.setCreateAt(DateUtil.getCurrentDate());
        data.setUserName(defaultUserName);
        return data;
    }
}