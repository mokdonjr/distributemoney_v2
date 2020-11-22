package com.seungchan.distributemoney_v2.account.param;

import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import com.seungchan.distributemoney_v2.common.param.AbsParamData;

public class ParamAccount extends AbsParamData {
    private BankType bankType;
    private String bankAccountNumber;

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
