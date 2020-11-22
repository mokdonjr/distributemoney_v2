package com.seungchan.distributemoney_v2.distribute.data;

import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.distribute.data.key.ObjectKeyDistribute;

import java.util.Date;

public class ObjectDataDistribute extends AbsObjectData implements IObjectDataDistribute {

    // key
    private ObjectKeyDistribute objectKeyDistribute = new ObjectKeyDistribute();

    // 뿌린 유저
    private String userId;
    // 뿌릴 인원 수
    private Integer seqCount = 1;
    // 뿌린 총 금액
    private Long totalValue = 0L;
    // 뿌린 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 뿌리기 종료 여부
    private Boolean isEnd = false;
    // 뿌리기 종료된 시각
    private Date endAt;

    // delegate
    public String getRoomId() {
        return objectKeyDistribute.getRoomId();
    }
    public void setRoomId(String roomId) {
        objectKeyDistribute.setRoomId(roomId);
    }
    public String getToken() {
        return objectKeyDistribute.getToken();
    }
    public void setToken(String token) {
        objectKeyDistribute.setToken(token);
    }

    // getter/setter
    public ObjectKeyDistribute getObjectKeyDistribute() {
        return objectKeyDistribute;
    }
    public void setObjectKeyDistribute(ObjectKeyDistribute objectKeyDistribute) {
        this.objectKeyDistribute = objectKeyDistribute;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Integer getSeqCount() {
        return seqCount;
    }
    public void setSeqCount(Integer seqCount) {
        this.seqCount = seqCount;
    }
    public Long getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Boolean getIsEnd() {
        return isEnd;
    }
    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }
    public Date getEndAt() {
        return endAt;
    }
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    // implements
    @Override
    public ObjectKeyDistribute getObjectKey() {
        return objectKeyDistribute;
    }
}
