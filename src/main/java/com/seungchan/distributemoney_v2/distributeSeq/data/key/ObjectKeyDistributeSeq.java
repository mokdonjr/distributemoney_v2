package com.seungchan.distributemoney_v2.distributeSeq.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyDistributeSeq extends AbsObjectKey implements IObjectKeyDistributeSeq {

    private String roomId;
    private String token;
    private Integer seq;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
