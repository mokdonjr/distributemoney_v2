package com.seungchan.distributemoney_v2.account.data.key;

public class ObjectKeyAccount implements IObjectKeyAccount {

    // 은행 고유번호 (BankType)
    private Integer bankId;
    // 은행 계좌번호
    private String bankAccountNumber;

    public ObjectKeyAccount() {
    }

    public ObjectKeyAccount(Integer bankId, String bankAccountNumber) {
        this.bankId = bankId;
        this.bankAccountNumber = bankAccountNumber;
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
