package com.seungchan.distributemoney_v2.room.data;

import com.seungchan.distributemoney_v2.common.data.AbsObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.room.data.key.ObjectKeyRoom;

import java.util.Date;

public class ObjectDataRoom extends AbsObjectData implements IObjectDataRoom {

    // key
    private ObjectKeyRoom objectKeyRoom = new ObjectKeyRoom();

    // 표시할 채팅방 이름
    private String roomName;
    // 채팅방 최대 참여가능한 인원 수
    private Integer maxUserCount = 1500;
    // 채팅방 생성 시각
    private Date createAt = DateUtil.getCurrentDate();
    // 채팅방 폭파 여부
    private Boolean isDelete = false;
    // 채팅방 폭파 시각
    private Date deleteAt;

    // delegate
    public String getRoomId() {
        return objectKeyRoom.getRoomId();
    }
    public void setRoomId(String roomId) {
        objectKeyRoom.setRoomId(roomId);
    }

    // getter/setter
    public ObjectKeyRoom getObjectKeyRoom() {
        return objectKeyRoom;
    }
    public void setObjectKeyRoom(ObjectKeyRoom objectKeyRoom) {
        this.objectKeyRoom = objectKeyRoom;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
    public Integer getMaxUserCount() {
        return maxUserCount;
    }
    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    // implements
    @Override
    public IObjectKey getObjectKey() {
        return objectKeyRoom;
    }
}
