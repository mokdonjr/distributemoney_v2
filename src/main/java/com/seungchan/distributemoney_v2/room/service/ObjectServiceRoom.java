package com.seungchan.distributemoney_v2.room.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.room.data.IObjectDataRoom;
import com.seungchan.distributemoney_v2.room.data.ObjectDataRoom;
import com.seungchan.distributemoney_v2.room.data.key.ObjectKeyRoom;
import com.seungchan.distributemoney_v2.room.mapper.ObjectDataRoomMapper;
import com.seungchan.distributemoney_v2.room.param.ParamRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectServiceRoom extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataRoomMapper objectDataRoomMapper;

    @Override
    public ObjectDataRoom selectData(IObjectKey objectKey) {
        ObjectKeyRoom key = (ObjectKeyRoom) objectKey;
        return objectDataRoomMapper.selectObjectDataRoom(key.getRoomId());
    }

    public ObjectDataRoom selectData(String roomId) {
        ObjectKeyRoom key = new ObjectKeyRoom();
        key.setRoomId(roomId);
        return selectData(key);
    }

    @Override
    public ObjectDataRoom selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyRoom key = (ObjectKeyRoom) objectKey;
        return objectDataRoomMapper.selectObjectDataRoomForUpdate(key.getRoomId());
    }

    public ObjectDataRoom selectDataWithLock(String roomId) {
        ObjectKeyRoom key = new ObjectKeyRoom();
        key.setRoomId(roomId);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataRoom insertData(IObjectData objectData) {
        ObjectDataRoom data = (ObjectDataRoom) objectData;
        objectDataRoomMapper.insertObjectDataRoom(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataRoom data = (ObjectDataRoom) objectData;
        objectDataRoomMapper.updateObjectDataRoom(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyRoom key = (ObjectKeyRoom) objectKey;
        objectDataRoomMapper.deleteObjectDataRoom(key.getRoomId());
    }

    public void deleteData(String roomId) {
        ObjectKeyRoom key = new ObjectKeyRoom();
        key.setRoomId(roomId);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamRoom paramRoom = (ParamRoom) param;

        // TODO : 방 이름 글자수 체크
        if (paramRoom.getRoomName() == null)
            paramRoom.setRoomName("DEFAULT_NAME");

        // TODO : 방 입장 허용 최대수 넘는지 체크
        if (paramRoom.getMaxUserCount() == null)
            paramRoom.setMaxUserCount(1500);
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataRoom beforeObjectDataRoom = (ObjectDataRoom) before;
        if (beforeObjectDataRoom.getIsDelete())
            throw new BusinessException("이미 삭제된 방입니다");

        ObjectDataRoom objectDataRoom = (ObjectDataRoom) object;
    }

    @Override
    public IObjectDataRoom createObjectInstanceDefault(IParamData param) {
        ParamRoom paramRoom = (ParamRoom) param;
        ObjectDataRoom data = new ObjectDataRoom();
        data.setRoomId(paramRoom.getRoomId());
        data.setRoomName(paramRoom.getRoomName());
        data.setMaxUserCount(paramRoom.getMaxUserCount());
        return data;
    }
}
