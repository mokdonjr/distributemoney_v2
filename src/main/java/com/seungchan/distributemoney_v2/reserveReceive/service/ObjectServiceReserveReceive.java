package com.seungchan.distributemoney_v2.reserveReceive.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.reserveReceive.data.ObjectDataReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.data.key.ObjectKeyReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.mapper.ObjectDataReserveReceiveMapper;
import com.seungchan.distributemoney_v2.reserveReceive.param.ParamReserveReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectServiceReserveReceive extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataReserveReceiveMapper objectDataReserveReceiveMapper;

    @Override
    public ObjectDataReserveReceive selectData(IObjectKey objectKey) {
        ObjectKeyReserveReceive key = (ObjectKeyReserveReceive) objectKey;
        return objectDataReserveReceiveMapper.selectObjectDataReserveReceive(key.getReserveReceiveId());
    }

    public ObjectDataReserveReceive selectData(long reserveReceiveId) {
        var key = new ObjectKeyReserveReceive();
        key.setReserveReceiveId(reserveReceiveId);
        return selectData(key);
    }

    @Override
    public ObjectDataReserveReceive selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyReserveReceive key = (ObjectKeyReserveReceive) objectKey;
        return objectDataReserveReceiveMapper.selectObjectDataReserveReceive(key.getReserveReceiveId());
    }

    public ObjectDataReserveReceive selectDataWithLock(long reserveReceiveId) {
        var key = new ObjectKeyReserveReceive();
        key.setReserveReceiveId(reserveReceiveId);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataReserveReceive insertData(IObjectData objectData) {
        ObjectDataReserveReceive data = (ObjectDataReserveReceive) objectData;
        objectDataReserveReceiveMapper.insertObjectDataReserveReceive(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataReserveReceive data = (ObjectDataReserveReceive) objectData;
        objectDataReserveReceiveMapper.updateObjectDataReserveReceive(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyReserveReceive key = (ObjectKeyReserveReceive) objectKey;
        objectDataReserveReceiveMapper.deleteObjectDataReserveReceive(key.getReserveReceiveId());
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamReserveReceive paramReserveReceive = (ParamReserveReceive) param;

        if (paramReserveReceive.getRoomId() == null)
            throw new BusinessException("수령 요청할 방을 입력해주세요.");

        if (paramReserveReceive.getUserId() == null)
            throw new BusinessException("수령 요청할 유저를 입력해주세요.");

        if (paramReserveReceive.getToken() == null)
            throw new BusinessException("수령 요청할 뿌려진 머니 토큰을 입력해주세요.");
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataReserveReceive beforeObjectDataReserveReceive = (ObjectDataReserveReceive) before;
        if (beforeObjectDataReserveReceive.getIsEnd())
            throw new BusinessException("이미 수행이 완료된 예약건입니다.");

        ObjectDataReserveReceive objectDataReserveReceive = (ObjectDataReserveReceive) object;
    }

    @Override
    public ObjectDataReserveReceive createObjectInstanceDefault(IParamData param) {
        ParamReserveReceive paramReserveReceive = (ParamReserveReceive) param;
        var data = new ObjectDataReserveReceive();
        data.setRoomId(paramReserveReceive.getRoomId());
        data.setUserId(paramReserveReceive.getUserId());
        data.setToken(paramReserveReceive.getToken());
        return data;
    }
}
