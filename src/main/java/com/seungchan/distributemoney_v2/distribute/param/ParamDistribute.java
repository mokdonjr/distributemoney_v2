package com.seungchan.distributemoney_v2.distribute.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamDistribute extends AbsParamData {
    private String roomId;
    private String userId;
    private Long totalValue;
    private Integer seqCount;

    private ParamDistribute(Builder builder) {
        this.roomId = builder.roomId;
        this.userId = builder.userId;
        this.totalValue = builder.totalValue;
        this.seqCount = builder.seqCount;
    }

    public static Builder newParamDistribute() {
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

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(Integer seqCount) {
        this.seqCount = seqCount;
    }


    public static final class Builder {
        private String roomId;
        private String userId;
        private Long totalValue;
        private Integer seqCount;

        private Builder() {
        }

        public ParamDistribute build() {
            return new ParamDistribute(this);
        }

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder totalValue(Long totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public Builder seqCount(Integer seqCount) {
            this.seqCount = seqCount;
            return this;
        }
    }
}
