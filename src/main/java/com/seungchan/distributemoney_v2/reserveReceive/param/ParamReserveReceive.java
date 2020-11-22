package com.seungchan.distributemoney_v2.reserveReceive.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamReserveReceive extends AbsParamData {

    private String roomId;
    private String userId;
    private String token;

    private ParamReserveReceive(Builder builder) {
        this.roomId = builder.roomId;
        this.userId = builder.userId;
        this.token = builder.token;
    }

    public static Builder newParamReserveReceive() {
        return new Builder();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static final class Builder {
        private String roomId;
        private String userId;
        private String token;

        private Builder() {
        }

        public ParamReserveReceive build() {
            return new ParamReserveReceive(this);
        }

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }
    }
}
