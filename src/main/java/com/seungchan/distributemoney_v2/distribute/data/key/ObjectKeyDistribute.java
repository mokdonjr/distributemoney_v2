package com.seungchan.distributemoney_v2.distribute.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyDistribute extends AbsObjectKey implements IObjectKeyDistribute {

    private String roomId;
    private String token;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
