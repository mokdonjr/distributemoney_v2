package com.seungchan.distributemoney_v2.userAccount.param;

import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamUserAccount extends AbsParamData {

    private String userId;
    private BankType bankType;
    private String bankAccountNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
