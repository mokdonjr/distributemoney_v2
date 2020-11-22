package com.seungchan.distributemoney_v2.roomUser.service;

import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.roomUser.data.ObjectDataRoomUser;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceRoomUserTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceRoomUser objectServiceRoomUser;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceRoomUser;
    }

    @Override
    protected ObjectDataRoomUser getDefaultObjectData() {
        var data = new ObjectDataRoomUser();
        data.setRoomId(defaultRoomId);
        data.setUserId(defaultUserId);
        data.setUserName(defaultUserName);
        return data;
    }
}