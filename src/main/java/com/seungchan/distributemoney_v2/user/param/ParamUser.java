package com.seungchan.distributemoney_v2.user.param;

import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamUser extends AbsParamData {

    private String userId;
    private String userName;
    private Long balance;

    private ParamUser(Builder builder) {
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.balance = builder.balance;
    }

    public static Builder newParamUser() {
        return new Builder();
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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }


    public static final class Builder {
        private String userId;
        private String userName;
        private Long balance;

        private Builder() {
        }

        public ParamUser build() {
            return new ParamUser(this);
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder balance(Long balance) {
            this.balance = balance;
            return this;
        }
    }
}
