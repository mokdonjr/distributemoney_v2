package com.seungchan.distributemoney_v2.distributeSeq.data;

import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.distributeSeq.data.key.ObjectKeyDistributeSeq;

import java.util.Date;

public class ObjectDataDistributeSeq extends AbsObjectData implements IObjectDataDistributeSeq {

    // key
    private ObjectKeyDistributeSeq objectKeyDistributeSeq = new ObjectKeyDistributeSeq();

    // 분배한 유저
    private String userId;
    // 랜덤 분배된 금액
    private Long value = 0L;
    // 수령자
    private String receiveUserId;
    // 수령 완료 여부
    private Boolean isReceive = false;
    // 수령 시각
    private Date receiveAt;

    // delegate
    public String getRoomId() {
        return objectKeyDistributeSeq.getRoomId();
    }
    public void setRoomId(String roomId) {
        objectKeyDistributeSeq.setRoomId(roomId);
    }
    public String getToken() {
        return objectKeyDistributeSeq.getToken();
    }
    public void setToken(String token) {
        objectKeyDistributeSeq.setToken(token);
    }
    public Integer getSeq() {
        return objectKeyDistributeSeq.getSeq();
    }
    public void setSeq(Integer seq) {
        objectKeyDistributeSeq.setSeq(seq);
    }

    // getter/setter
    public ObjectKeyDistributeSeq getObjectKeyDistributeSeq() {
        return objectKeyDistributeSeq;
    }
    public void setObjectKeyDistributeSeq(ObjectKeyDistributeSeq objectKeyDistributeSeq) {
        this.objectKeyDistributeSeq = objectKeyDistributeSeq;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getValue() {
        return value;
    }
    public void setValue(Long value) {
        this.value = value;
    }
    public String getReceiveUserId() {
        return receiveUserId;
    }
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
    public Boolean getIsReceive() {
        return isReceive;
    }
    public void setIsReceive(Boolean isReceive) {
        this.isReceive = isReceive;
    }
    public Date getReceiveAt() {
        return receiveAt;
    }
    public void setReceiveAt(Date receiveAt) {
        this.receiveAt = receiveAt;
    }

    // implements
    @Override
    public ObjectKeyDistributeSeq getObjectKey() {
        return objectKeyDistributeSeq;
    }
}
