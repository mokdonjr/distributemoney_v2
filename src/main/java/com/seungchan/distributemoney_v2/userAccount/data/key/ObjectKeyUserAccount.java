package com.seungchan.distributemoney_v2.userAccount.data.key;

import com.seungchan.distributemoney_v2.common.data.key.AbsObjectKey;

public class ObjectKeyUserAccount extends AbsObjectKey implements IObjectKeyUserAccount {
    private String userId;
    private Integer bankId;
    private String bankAccountNumber;

    public ObjectKeyUserAccount() {
    }

    public ObjectKeyUserAccount(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
