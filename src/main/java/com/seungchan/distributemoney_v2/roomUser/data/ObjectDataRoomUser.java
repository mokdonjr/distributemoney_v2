package com.seungchan.distributemoney_v2.roomUser.data;

import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.roomUser.data.key.ObjectKeyRoomUser;

import java.util.Date;

public class ObjectDataRoomUser extends AbsObjectData {

    // key
    private ObjectKeyRoomUser objectKeyRoomUser = new ObjectKeyRoomUser();
    // 채팅방내 계정 이름
    private String userName;
    // 채팅방 참여 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 채팅방 나감 여부
    private Boolean isDelete = false;
    // 채팅방 나간 시각
    private Date deleteAt;

    // delegate
    public String getRoomId() {
        return objectKeyRoomUser.getRoomId();
    }
    public void setRoomId(String roomId) {
        objectKeyRoomUser.setRoomId(roomId);
    }
    public String getUserId() {
        return objectKeyRoomUser.getUserId();
    }
    public void setUserId(String userId) {
        objectKeyRoomUser.setUserId(userId);
    }

    // getter/setter
    public ObjectKeyRoomUser getObjectKeyRoomUser() {
        return objectKeyRoomUser;
    }
    public void setObjectKeyRoomUser(ObjectKeyRoomUser objectKeyRoomUser) {
        this.objectKeyRoomUser = objectKeyRoomUser;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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
    public IObjectKey getObjectKey() {
        return objectKeyRoomUser;
    }
}
