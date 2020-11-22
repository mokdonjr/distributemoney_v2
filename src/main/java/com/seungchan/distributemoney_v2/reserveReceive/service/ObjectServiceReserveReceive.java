package com.seungchan.distributemoney_v2.reserveReceive.service;

import com.seungchan.distributemoney_v2.common.config.BusinessConfig;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.common.util.LogUtil;
import com.seungchan.distributemoney_v2.distribute.service.ObjectServiceDistribute;
import com.seungchan.distributemoney_v2.distributeSeq.data.ObjectDataDistributeSeq;
import com.seungchan.distributemoney_v2.distributeSeq.service.ObjectServiceDistributeSeq;
import com.seungchan.distributemoney_v2.reserveReceive.data.ObjectDataReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.data.key.ObjectKeyReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.mapper.ObjectDataReserveReceiveMapper;
import com.seungchan.distributemoney_v2.reserveReceive.param.ParamReserveReceive;
import com.seungchan.distributemoney_v2.user.service.ObjectServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ObjectServiceReserveReceive extends AbsObjectPersistenceService {

    @Autowired
    private ObjectServiceUser objectServiceUser;
    @Autowired
    private ObjectServiceDistribute objectServiceDistribute;
    @Autowired
    private ObjectServiceDistributeSeq objectServiceDistributeSeq;
    @Resource
    private ObjectServiceReserveReceive objectServiceReserveReceive;
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

    // 여러 인스턴스가 같이 돈다. 밀릴 수 있음.
    @Scheduled(fixedDelay = 500)
    public void checkReserveReceiveSchedule() throws BusinessException {
        if (BusinessConfig.isTest())
            return;
        LogUtil.runWithStopWatch("[수령 예약 처리 스케쥴]", () -> {
            objectServiceReserveReceive.checkReserveReceive();
        });
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void checkReserveReceive() throws BusinessException {
        var reserveReceiveToProcess = objectDataReserveReceiveMapper.selectObjectDataReserveReceiveToProcess();
        if (reserveReceiveToProcess == null)
            return;

        String userId = reserveReceiveToProcess.getUserId();

        // 뿌리기 유효성 검증
        // 10분이 지나 이미 없어진 뿌리기 인지 체크
        String roomId = reserveReceiveToProcess.getRoomId();
        String token = reserveReceiveToProcess.getToken();
        var distribute = objectServiceDistribute.selectDataWithLock(roomId, token);
        if (distribute == null) {
            // TODO : 소켓으로 이미 완료된 뿌리기라고 노티 보내기
            throw new BusinessException("너무 오래되었거나 존재하지 않는 뿌리기입니다.");
        } else {
            int seqCountToReceive = objectServiceDistributeSeq.selectCountToReceive(roomId, token);
            if (seqCountToReceive == 0) {
                // TODO : 소켓으로 이미 완료된 뿌리기라고 노티 보내기
                throw new BusinessException("더 이상 수령할 수 없는 뿌리기입니다.");
            } else {
                // 수령 가능한 seq
                ObjectDataDistributeSeq distributeSeqToReceive = null;
                int receivableCount = 0;

                // 한번 더 lock 걸고 조회
                var seqList = objectServiceDistributeSeq.selectDataAllSeqWithLock(roomId, token);
                for (var seq : seqList) {
                    if (!seq.getIsReceive()) {
                        // 미수령건
                        receivableCount++;

                        // 수령!
                        if (distributeSeqToReceive == null) {
                            distributeSeqToReceive = seq;
                        }
                    }
                }

                if (distributeSeqToReceive == null) {
                    // TODO : 소켓으로 선착순에 들지 못해 실패했다는 노티 송신!
                    throw new BusinessException("더 이상 수령할 수 없는 뿌리기입니다.");
                }
                // 수령 처리
                else {
                    // TODO : 소켓으로 수령 성공 노티 송신!
                    // 수령 처리
                    distributeSeqToReceive.setReceiveUserId(userId);
                    distributeSeqToReceive.setIsReceive(true);
                    distributeSeqToReceive.setReceiveAt(DateUtil.getCurrentDate());
                    objectServiceDistributeSeq.updateObjectDefault(distributeSeqToReceive);

                    var userLocked = objectServiceUser.selectDataWithLock(userId);
                    if (!userLocked.isAvailableAddBalance(distributeSeqToReceive.getValue()))
                        throw new BusinessException("잔액이 수령할 수 있는 상태가 아닙니다.");
                    userLocked.addBalance(distributeSeqToReceive.getValue());
                    objectServiceUser.updateObjectDefault(userLocked);
                }

                // 마지막 수령가능한 seq 였을경우 처리
                if (receivableCount == 1) {
                    // TODO : 더 이상 다른 유저가 요청 못하게 채팅방에 뿌리기 종료 노티 송신!
                    // 뿌리기 종료
                    distribute.setIsEnd(true);
                    distribute.setEndAt(DateUtil.getCurrentDate());
                    objectServiceDistribute.updateData(distribute);
                }
            }
        }

        reserveReceiveToProcess.setIsEnd(true);
        reserveReceiveToProcess.setEndAt(DateUtil.getCurrentDate());
        objectServiceReserveReceive.updateData(reserveReceiveToProcess);
    }
}
