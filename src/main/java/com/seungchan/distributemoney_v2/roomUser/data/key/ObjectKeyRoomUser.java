package com.seungchan.distributemoney_v2.roomUser.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyRoomUser extends AbsObjectKey implements IObjectKeyRoomUser {

    private String roomId;
    private String userId;

    public ObjectKeyRoomUser() {
    }

    public ObjectKeyRoomUser(String roomId, String userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
