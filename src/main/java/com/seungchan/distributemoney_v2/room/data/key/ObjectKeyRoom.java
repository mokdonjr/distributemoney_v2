package com.seungchan.distributemoney_v2.room.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyRoom extends AbsObjectKey implements IObjectKeyRoom {

    private String roomId;

    public ObjectKeyRoom() {
    }

    public ObjectKeyRoom(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
