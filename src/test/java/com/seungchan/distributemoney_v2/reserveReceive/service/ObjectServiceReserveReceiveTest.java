package com.seungchan.distributemoney_v2.reserveReceive.service;

import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.reserveReceive.data.ObjectDataReserveReceive;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceReserveReceiveTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceReserveReceive objectServiceReserveReceive;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceReserveReceive;
    }

    @Override
    protected ObjectDataReserveReceive getDefaultObjectData() {
        var data = new ObjectDataReserveReceive();
        data.setReserveReceiveId(-1L);
        data.setRoomId(defaultRoomId);
        data.setUserId(defaultUserId);
        data.setToken(defaultToken);
        return data;
    }
}