package com.seungchan.distributemoney_v2.distributeSeq.service;

import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.distributeSeq.data.ObjectDataDistributeSeq;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectServiceDistributeSeqTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceDistributeSeq objectServiceDistributeSeq;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceDistributeSeq;
    }

    @Override
    protected ObjectDataDistributeSeq getDefaultObjectData() {
        var data = new ObjectDataDistributeSeq();
        data.setRoomId(defaultRoomId);
        data.setUserId(defaultUserId);
        data.setToken(defaultToken);
        data.setSeq(1);
        data.setValue(10L);
        return data;
    }
}