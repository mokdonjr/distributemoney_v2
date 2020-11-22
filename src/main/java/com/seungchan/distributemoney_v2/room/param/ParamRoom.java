package com.seungchan.distributemoney_v2.room.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamRoom extends AbsParamData {

    private String roomId;
    private String roomName;
    private Integer maxUserCount;

    private ParamRoom(Builder builder) {
        this.roomId = builder.roomId;
        this.roomName = builder.roomName;
        this.maxUserCount = builder.maxUserCount;
    }

    public static Builder newParamRoom() {
        return new Builder();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }


    public static final class Builder {
        private String roomId;
        private String roomName;
        private Integer maxUserCount;

        private Builder() {
        }

        public ParamRoom build() {
            return new ParamRoom(this);
        }

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder maxUserCount(Integer maxUserCount) {
            this.maxUserCount = maxUserCount;
            return this;
        }
    }
}
