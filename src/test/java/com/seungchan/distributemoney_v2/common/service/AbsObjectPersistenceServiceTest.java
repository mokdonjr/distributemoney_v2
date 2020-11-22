package com.seungchan.distributemoney_v2.common.service;

import com.seungchan.distributemoney_v2.TestBase;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbsObjectPersistenceServiceTest extends TestBase {

    protected abstract IObjectPersistenceService getObjectPersistenceService();

    protected abstract IObjectData getDefaultObjectData();

    @Test
    public void testInsertAndSelectAndDelete() {

        // nodata
        var data = getDefaultObjectData();
        var nodata = getObjectPersistenceService().selectData(data.getObjectKey());
        Assertions.assertNull(nodata);

        // insert
        getObjectPersistenceService().insertData(data);
        var inserted = getObjectPersistenceService().selectData(data.getObjectKey());
        Assertions.assertNotNull(inserted);

        // delete
        getObjectPersistenceService().deleteData(data.getObjectKey());
        var deleted = getObjectPersistenceService().selectData(data.getObjectKey());
        Assertions.assertNull(deleted);

    }
}