package com.seungchan.distributemoney_v2.user.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyUser extends AbsObjectKey implements IObjectKeyUser {
    protected String userId;

    public ObjectKeyUser() {
    }

    public ObjectKeyUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
