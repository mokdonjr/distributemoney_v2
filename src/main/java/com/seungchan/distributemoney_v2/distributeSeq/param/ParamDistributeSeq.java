package com.seungchan.distributemoney_v2.distributeSeq.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamDistributeSeq extends AbsParamData {

    private String roomId;
    private String userId;
    private String token;
    private Integer seq;
    private Long value;

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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
