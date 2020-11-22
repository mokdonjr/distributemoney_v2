package com.seungchan.distributemoney_v2.room.service;

import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.room.data.ObjectDataRoom;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceRoomTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceRoom objectServiceRoom;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceRoom;
    }

    @Override
    protected ObjectDataRoom getDefaultObjectData() {
        var data = new ObjectDataRoom();
        data.setRoomId(defaultRoomId);
        data.setRoomName(defaultRoomName);
        return data;
    }
}