package com.seungchan.distributemoney_v2.roomUser.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamRoomUser extends AbsParamData {

    private String roomId;
    private String userId;
    private String userName;

    private ParamRoomUser(Builder builder) {
        this.roomId = builder.roomId;
        this.userId = builder.userId;
        this.userName = builder.userName;
    }

    public static Builder newParamRoomUser() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static final class Builder {
        private String roomId;
        private String userId;
        private String userName;

        private Builder() {
        }

        public ParamRoomUser build() {
            return new ParamRoomUser(this);
        }

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
    }
}
