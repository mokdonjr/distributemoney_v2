package com.seungchan.distributemoney_v2.userAccount.data;

import com.seungchan.distributemoney_v2.account.data.ObjectDataAccount;
import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.userAccount.data.key.ObjectKeyUserAccount;

import java.util.Date;
import java.util.List;

/**
 * 계정에 연동된 계좌 데이터
 */
public class ObjectDataUserAccount extends AbsObjectData implements IObjectDataUserAccount {

    // key
    private ObjectKeyUserAccount objectKeyUserAccount = new ObjectKeyUserAccount();

    // 계정 연동 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 계정 연동 정지 여부
    protected Boolean isBlock = false;
    // 계정 연동 정지 시각
    protected Date blockAt;
    // 계정 연동 해지 여부
    private Boolean isDelete = false;
    // 계정 연동 해지 시각
    private Date deleteAt;

    // 계정에 연결된 은행계좌 리스트
    private List<ObjectDataAccount> objectDataAccountList;

    // delegate
    public String getUserId() {
        return objectKeyUserAccount.getUserId();
    }
    public void setUserId(String userId) {
        objectKeyUserAccount.setUserId(userId);
    }
    public Integer getBankId() {
        return objectKeyUserAccount.getBankId();
    }
    public void setBankId(Integer bankId) {
        objectKeyUserAccount.setBankId(bankId);
    }
    public String getBankAccountNumber() {
        return objectKeyUserAccount.getBankAccountNumber();
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        objectKeyUserAccount.setBankAccountNumber(bankAccountNumber);
    }

    // getter/setter
    public ObjectKeyUserAccount getObjectKeyUserAccount() {
        return objectKeyUserAccount;
    }
    public void setObjectKeyUserAccount(ObjectKeyUserAccount objectKeyUserAccount) {
        this.objectKeyUserAccount = objectKeyUserAccount;
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
    public List<ObjectDataAccount> getObjectDataAccountList() {
        return objectDataAccountList;
    }
    public void setObjectDataAccountList(List<ObjectDataAccount> objectDataAccountList) {
        this.objectDataAccountList = objectDataAccountList;
    }

    // implements
    @Override
    public ObjectKeyUserAccount getObjectKey() {
        return objectKeyUserAccount;
    }
}
