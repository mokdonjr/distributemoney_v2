package com.seungchan.distributemoney_v2.reserveReceive.data;

import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.reserveReceive.data.key.ObjectKeyReserveReceive;

import java.util.Date;

public class ObjectDataReserveReceive extends AbsObjectData implements IObjectDataReserveReceive {

    // key
    private ObjectKeyReserveReceive objectKeyReserveReceive = new ObjectKeyReserveReceive();

    // 예약자 정보
    private String roomId;
    private String userId;
    private String token;
    // 예약 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 예약 처리 여부
    private Boolean isEnd = false;
    // 예약 처리 시각
    private Date endAt;

    // delegate
    public Long getReserveReceiveId() {
        return objectKeyReserveReceive.getReserveReceiveId();
    }
    public void setReserveReceiveId(Long reserveReceiveId) {
        objectKeyReserveReceive.setReserveReceiveId(reserveReceiveId);
    }

    // getter/setter
    public ObjectKeyReserveReceive getObjectKeyReserveReceive() {
        return objectKeyReserveReceive;
    }
    public void setObjectKeyReserveReceive(ObjectKeyReserveReceive objectKeyReserveReceive) {
        this.objectKeyReserveReceive = objectKeyReserveReceive;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
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
    public ObjectKeyReserveReceive getObjectKey() {
        return objectKeyReserveReceive;
    }
}
