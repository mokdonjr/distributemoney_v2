package com.seungchan.distributemoney_v2.distributeSeq.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.distribute.service.ObjectServiceDistribute;
import com.seungchan.distributemoney_v2.distributeSeq.data.ObjectDataDistributeSeq;
import com.seungchan.distributemoney_v2.distributeSeq.data.key.ObjectKeyDistributeSeq;
import com.seungchan.distributemoney_v2.distributeSeq.mapper.ObjectDataDistributeSeqMapper;
import com.seungchan.distributemoney_v2.distributeSeq.param.ParamDistributeSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectServiceDistributeSeq extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataDistributeSeqMapper objectDataDistributeSeqMapper;
    @Autowired
    private ObjectServiceDistribute objectServiceDistribute;

    @Override
    public ObjectDataDistributeSeq selectData(IObjectKey objectKey) {
        ObjectKeyDistributeSeq key = (ObjectKeyDistributeSeq) objectKey;
        return objectDataDistributeSeqMapper.selectObjectDataDistributeSeq(key.getRoomId(), key.getToken(), key.getSeq());
    }

    public ObjectDataDistributeSeq selectData(String roomId, String token, int seq) {
        ObjectKeyDistributeSeq key = new ObjectKeyDistributeSeq();
        key.setRoomId(roomId);
        key.setToken(token);
        key.setSeq(seq);
        return selectData(key);
    }

    public List<ObjectDataDistributeSeq> selectDataAllSeq(String roomId, String token) {
        return objectDataDistributeSeqMapper.selectObjectDataDistributeSeqAll(roomId, token);
    }

    @Override
    public ObjectDataDistributeSeq selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyDistributeSeq key = (ObjectKeyDistributeSeq) objectKey;
        return objectDataDistributeSeqMapper.selectObjectDataDistributeSeq(key.getRoomId(), key.getToken(), key.getSeq());
    }

    public ObjectDataDistributeSeq selectDataWithLock(String roomId, String token, int seq) {
        ObjectKeyDistributeSeq key = new ObjectKeyDistributeSeq();
        key.setRoomId(roomId);
        key.setToken(token);
        key.setSeq(seq);
        return selectDataWithLock(key);
    }

    public List<ObjectDataDistributeSeq> selectDataAllSeqWithLock(String roomId, String token) {
        return objectDataDistributeSeqMapper.selectObjectDataDistributeSeqAllForUpdate(roomId, token);
    }

    public ObjectDataDistributeSeq selectDataToReceiveWithLock(String roomId, String token) {
        return objectDataDistributeSeqMapper.selectObjectDataDistributeSeqToReceive(roomId, token);
    }

    public int selectCountToReceive(String roomId, String token) {
        return objectDataDistributeSeqMapper.selectCountDistributeSeqToReceive(roomId, token);
    }

    @Override
    public ObjectDataDistributeSeq insertData(IObjectData objectData) {
        ObjectDataDistributeSeq data = (ObjectDataDistributeSeq) objectData;
        objectDataDistributeSeqMapper.insertObjectDataDistributeSeq(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataDistributeSeq data = (ObjectDataDistributeSeq) objectData;
        objectDataDistributeSeqMapper.updateObjectDataDistributeSeq(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyDistributeSeq key = (ObjectKeyDistributeSeq) objectKey;
        objectDataDistributeSeqMapper.deleteObjectDataDistributeSeq(key.getRoomId(), key.getToken(), key.getSeq());
    }

    public void deleteDate(String roomId, String token, int seq) {
        ObjectKeyDistributeSeq key = new ObjectKeyDistributeSeq();
        key.setRoomId(roomId);
        key.setToken(token);
        key.setSeq(seq);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamDistributeSeq paramDistributeSeq = (ParamDistributeSeq) param;

        // TODO : 방 활성상태 체크
        if (paramDistributeSeq.getRoomId() == null)
            throw new BusinessException("방을 입력해주세요.");

        if (paramDistributeSeq.getUserId() == null)
            throw new BusinessException("뿌리기 유저 정보가 입력되지 않았습니다");

        if (paramDistributeSeq.getToken() == null)
            throw new BusinessException("토큰이 없습니다.");

        if (paramDistributeSeq.getSeq() == null)
            throw new BusinessException("몇번째 분배인지 알 수 없습니다.");

        if (paramDistributeSeq.getValue() <= 0)
            throw new BusinessException("0원 이상을 뿌릴 수 있습니다.");
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataDistributeSeq beforeObjectDataDistributeSeq = (ObjectDataDistributeSeq) before;
        var distributeData = objectServiceDistribute.selectData(beforeObjectDataDistributeSeq.getRoomId(), beforeObjectDataDistributeSeq.getToken());
        if (distributeData.getIsEnd())
            throw new BusinessException("이미 종료된 뿌리기를 수령할 수 없습니다.");

        if (DateUtil.getCurrentDate().after(DateUtil.getDateAfterMinutes(distributeData.getCreateAt(), 10)))
            throw new BusinessException("10 분이 지나 이미 종료된 뿌리기 입니다.");

        if (beforeObjectDataDistributeSeq.getIsReceive())
            throw new BusinessException("이미 다른 사람이 수령했습니다");

        ObjectDataDistributeSeq objectDataDistributeSeq = (ObjectDataDistributeSeq) object;
        if (objectDataDistributeSeq.getReceiveUserId() != null) {
            if (objectDataDistributeSeq.getReceiveUserId().equals(objectDataDistributeSeq.getUserId()))
                throw new BusinessException("본인이 뿌린 머니를 스스로 수령할 수 없습니다.");
        }

    }

    @Override
    public ObjectDataDistributeSeq createObjectInstanceDefault(IParamData param) {
        ParamDistributeSeq paramDistributeSeq = (ParamDistributeSeq) param;
        var data = new ObjectDataDistributeSeq();
        data.setRoomId(paramDistributeSeq.getRoomId());
        data.setUserId(paramDistributeSeq.getUserId());
        data.setToken(paramDistributeSeq.getToken());
        data.setSeq(paramDistributeSeq.getSeq());
        data.setValue(paramDistributeSeq.getValue());
        return data;
    }
}
