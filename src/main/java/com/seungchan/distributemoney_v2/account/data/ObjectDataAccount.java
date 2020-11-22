package com.seungchan.distributemoney_v2.account.data;

import com.seungchan.distributemoney_v2.account.data.key.ObjectKeyAccount;
import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.common.util.NullUtil;

import java.util.Date;

/**
 * 은행 계좌
 */
public class ObjectDataAccount extends AbsObjectData implements IObjectDataAccount {

    // key
    private ObjectKeyAccount objectKeyAccount = new ObjectKeyAccount();

    // 계좌 잔액
    protected Long balance = 0L;
    // 가장 최근 잔액 변경 시각
    protected Date lastUpdateAt = DateUtil.getCurrentDate();
    // 가장 최근 변화량
    protected Long modBalance = 0L;
    // 은행 계좌 등록 시각
    protected Date createAt = DateUtil.getCurrentDate();
    // 은행 계좌 이용 불가 여부
    protected Boolean isBlock = false;
    // 은행 계좌 이용 불가 시작 시각
    protected Date blockAt;
    // 은행 계좌 등록 해제 여부
    protected Boolean isDelete = false;
    // 은행 계좌 등록 해제 시각
    protected Date deleteAt;

    // delegate
    public Integer getBankId() {
        return objectKeyAccount.getBankId();
    }
    public void setBankId(Integer bankId) {
        objectKeyAccount.setBankId(bankId);
    }
    public String getBankAccountNumber() {
        return objectKeyAccount.getBankAccountNumber();
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        objectKeyAccount.setBankAccountNumber(bankAccountNumber);
    }

    // getter/setter
    public ObjectKeyAccount getObjectKeyAccount() {
        return objectKeyAccount;
    }
    public void setObjectKeyAccount(ObjectKeyAccount objectKeyAccount) {
        this.objectKeyAccount = objectKeyAccount;
    }
    @Override
    public Long getBalance() {
        return balance;
    }
    @Override
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    @Override
    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }
    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
    @Override
    public Long getModBalance() {
        return modBalance;
    }
    public void setModBalance(Long modBalance) {
        this.modBalance = modBalance;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Boolean getIsBlock() {
        return isBlock;
    }
    public void setIsBlock(Boolean isBlock) {
        this.isBlock = isBlock;
    }
    public Date getBlockAt() {
        return blockAt;
    }
    public void setBlockAt(Date blockAt) {
        this.blockAt = blockAt;
    }
    public Boolean getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    public Date getDeleteAt() {
        return deleteAt;
    }
    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    // implements
    @Override
    public ObjectKeyAccount getObjectKey() {
        return objectKeyAccount;
    }
    @Override
    public void addBalance(Long value) {
        this.modBalance = value;
        this.balance = NullUtil.getOrDefault(this.balance) + value;
        this.lastUpdateAt = DateUtil.getCurrentDate();
    }
    @Override
    public boolean isAvailableAddBalance(Long value) {
        if (NullUtil.getOrDefault(isDelete))
            return false;
        if (NullUtil.getOrDefault(isBlock))
            return false;
        return NullUtil.getOrDefault(this.balance) + value >= 0;
    }
}
