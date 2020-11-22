package com.seungchan.distributemoney_v2.reserveReceive.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyReserveReceive extends AbsObjectKey implements IObjectKeyReserveReceive {

    private Long reserveReceiveId; // auto increment

    public Long getReserveReceiveId() {
        return reserveReceiveId;
    }

    public void setReserveReceiveId(Long reserveReceiveId) {
        this.reserveReceiveId = reserveReceiveId;
    }
}
