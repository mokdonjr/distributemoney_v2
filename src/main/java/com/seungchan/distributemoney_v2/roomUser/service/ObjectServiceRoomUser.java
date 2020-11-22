package com.seungchan.distributemoney_v2.roomUser.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.roomUser.data.ObjectDataRoomUser;
import com.seungchan.distributemoney_v2.roomUser.data.key.ObjectKeyRoomUser;
import com.seungchan.distributemoney_v2.roomUser.mapper.ObjectDataRoomUserMapper;
import com.seungchan.distributemoney_v2.roomUser.param.ParamRoomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectServiceRoomUser extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataRoomUserMapper objectDataRoomUserMapper;

    @Override
    public ObjectDataRoomUser selectData(IObjectKey objectKey) {
        ObjectKeyRoomUser key = (ObjectKeyRoomUser) objectKey;
        return objectDataRoomUserMapper.selectObjectDataRoomUser(key.getRoomId(), key.getUserId());
    }

    public ObjectDataRoomUser selectData(String roomId, String userId) {
        ObjectKeyRoomUser key = new ObjectKeyRoomUser();
        key.setRoomId(roomId);
        key.setUserId(userId);
        return selectData(key);
    }

    public List<ObjectDataRoomUser> selectDataListByRoomId(String roomId) {
        return objectDataRoomUserMapper.selectObjectDataRoomUserListByRoomId(roomId);
    }

    @Override
    public ObjectDataRoomUser selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyRoomUser key = (ObjectKeyRoomUser) objectKey;
        return objectDataRoomUserMapper.selectObjectDataRoomUserForUpdate(key.getRoomId(), key.getUserId());
    }

    public ObjectDataRoomUser selectDataWithLock(String roomId, String userId) {
        ObjectKeyRoomUser key = new ObjectKeyRoomUser();
        key.setRoomId(roomId);
        key.setUserId(userId);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataRoomUser insertData(IObjectData objectData) {
        ObjectDataRoomUser data = (ObjectDataRoomUser) objectData;
        objectDataRoomUserMapper.insertObjectDataRoomUser(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataRoomUser data = (ObjectDataRoomUser) objectData;
        objectDataRoomUserMapper.updateObjectDataRoomUser(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyRoomUser key = (ObjectKeyRoomUser) objectKey;
        objectDataRoomUserMapper.deleteObjectDataRoomUser(key.getRoomId(), key.getUserId());
    }

    public void deleteData(String roomId, String userId) {
        ObjectKeyRoomUser key = new ObjectKeyRoomUser();
        key.setRoomId(roomId);
        key.setUserId(userId);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamRoomUser paramRoomUser = (ParamRoomUser) param;
        if (paramRoomUser.getRoomId() == null)
            throw new BusinessException("방 고유번호를 입력해주세요.");

        if (paramRoomUser.getUserId() == null)
            throw new BusinessException("방에 참가할 유저 고유번호를 입력해주세요.");

        if (paramRoomUser.getUserName() == null)
            paramRoomUser.setUserName("DEFAULT_USER_NAME");
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataRoomUser beforeObjectDataRoomUser = (ObjectDataRoomUser) before;
        if (beforeObjectDataRoomUser.getIsDelete())
            throw new BusinessException("방을 나간 유저 입니다.");

        ObjectDataRoomUser objectDataRoomUser = (ObjectDataRoomUser) object;
    }

    @Override
    public ObjectDataRoomUser createObjectInstanceDefault(IParamData param) {
        ParamRoomUser paramRoomUser = (ParamRoomUser) param;
        var data = new ObjectDataRoomUser();
        data.setRoomId(paramRoomUser.getRoomId());
        data.setUserId(paramRoomUser.getUserId());
        data.setUserName(paramRoomUser.getUserName());
        return data;
    }
}
