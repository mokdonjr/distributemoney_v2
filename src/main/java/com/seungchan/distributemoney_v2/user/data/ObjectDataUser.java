package com.seungchan.distributemoney_v2.user.data;

import com.seungchan.distributemoney_v2.account.data.ObjectDataAccount;
import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.common.util.NullUtil;
import com.seungchan.distributemoney_v2.user.data.key.ObjectKeyUser;

import java.util.Date;

/**
 * 계정 데이터
 */
public class ObjectDataUser extends AbsObjectData implements IObjectDataUser {

    // key
    private ObjectKeyUser objectKeyUser = new ObjectKeyUser();

    // 계정 이름
    private String userName;
    // 페이머니 잔액
    protected Long balance = 0L;
    // 가장 최근 잔액 변경 시각
    protected Date lastUpdateAt = DateUtil.getCurrentDate();
    // 가장 최근 페이머니 변화량
    protected Long modBalance = 0L;
    // 계정 생성 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 계정 이용 불가 여부
    protected Boolean isBlock = false;
    // 계정 이용 불가 시작 시각
    protected Date blockAt;
    // 계정 삭제 여부
    private Boolean isDelete = false;
    // 계정 삭제 시각
    private Date deleteAt;
    // 디폴트 충전 계좌
    private ObjectDataAccount defaultChargeAccount;

    // delegate
    public String getUserId() {
        return objectKeyUser.getUserId();
    }
    public void setUserId(String userId) {
        objectKeyUser.setUserId(userId);
    }

    // getter/setter
    public ObjectKeyUser getObjectKeyUser() {
        return objectKeyUser;
    }
    public void setObjectKeyUser(ObjectKeyUser objectKeyUser) {
        this.objectKeyUser = objectKeyUser;
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
    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }
    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
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
    public ObjectDataAccount getDefaultChargeAccount() {
        return defaultChargeAccount;
    }
    public void setDefaultChargeAccount(ObjectDataAccount defaultChargeAccount) {
        this.defaultChargeAccount = defaultChargeAccount;
    }

    // implements
    @Override
    public ObjectKeyUser getObjectKey() {
        return objectKeyUser;
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
