package com.seungchan.distributemoney_v2.distribute.service;

import com.seungchan.distributemoney_v2.common.config.BusinessConfig;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.common.util.LogUtil;
import com.seungchan.distributemoney_v2.common.util.RandomUtil;
import com.seungchan.distributemoney_v2.distribute.data.ObjectDataDistribute;
import com.seungchan.distributemoney_v2.distribute.data.key.ObjectKeyDistribute;
import com.seungchan.distributemoney_v2.distribute.mapper.ObjectDataDistributeMapper;
import com.seungchan.distributemoney_v2.distribute.param.ParamDistribute;
import com.seungchan.distributemoney_v2.distributeSeq.param.ParamDistributeSeq;
import com.seungchan.distributemoney_v2.distributeSeq.service.ObjectServiceDistributeSeq;
import com.seungchan.distributemoney_v2.room.service.ObjectServiceRoom;
import com.seungchan.distributemoney_v2.roomUser.data.ObjectDataRoomUser;
import com.seungchan.distributemoney_v2.roomUser.service.ObjectServiceRoomUser;
import com.seungchan.distributemoney_v2.user.service.ObjectServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ObjectServiceDistribute extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataDistributeMapper objectDataDistributeMapper;
    @Autowired
    private ObjectServiceDistributeSeq objectServiceDistributeSeq;
    @Autowired
    private ObjectServiceUser objectServiceUser;
    @Autowired
    private ObjectServiceRoom objectServiceRoom;
    @Autowired
    private ObjectServiceRoomUser objectServiceRoomUser;
    @Resource
    private ObjectServiceDistribute objectServiceDistribute;

    @Override
    public ObjectDataDistribute selectData(IObjectKey objectKey) {
        ObjectKeyDistribute key = (ObjectKeyDistribute) objectKey;
        return objectDataDistributeMapper.selectObjectDataDistribute(key.getRoomId(), key.getToken());
    }

    public ObjectDataDistribute selectData(String roomId, String token) {
        ObjectKeyDistribute key = new ObjectKeyDistribute();
        key.setRoomId(roomId);
        key.setToken(token);
        return selectData(key);
    }

    @Override
    public ObjectDataDistribute selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyDistribute key = (ObjectKeyDistribute) objectKey;
        return objectDataDistributeMapper.selectObjectDataDistributeForUpdate(key.getRoomId(), key.getToken());
    }

    public ObjectDataDistribute selectDataWithLock(String roomId, String token) {
        ObjectKeyDistribute key = new ObjectKeyDistribute();
        key.setRoomId(roomId);
        key.setToken(token);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataDistribute insertData(IObjectData objectData) {
        ObjectDataDistribute data = (ObjectDataDistribute) objectData;
        objectDataDistributeMapper.insertObjectDataDistribute(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataDistribute data = (ObjectDataDistribute) objectData;
        objectDataDistributeMapper.updateObjectDataDistribute(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyDistribute key = (ObjectKeyDistribute) objectKey;
        objectDataDistributeMapper.deleteObjectDataDistribute(key.getRoomId(), key.getToken());
    }

    public void deleteDate(String roomId, String token) {
        ObjectKeyDistribute key = new ObjectKeyDistribute();
        key.setRoomId(roomId);
        key.setToken(token);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamDistribute paramDistribute = (ParamDistribute) param;

        // TODO : 방 활성상태 체크
        if (paramDistribute.getRoomId() == null)
            throw new BusinessException("방을 입력해주세요.");

        if (paramDistribute.getUserId() == null)
            throw new BusinessException("뿌리기 유저 정보가 입력되지 않았습니다");

        // TODO : 1500명까지?
        if (paramDistribute.getSeqCount() == null)
            throw new BusinessException("몇 명 까지 분배할지 명 수를 입력해주세요.");

        if (paramDistribute.getSeqCount() <= 0)
            throw new BusinessException("최소 1명 이상에게만 뿌릴 수 있습니다.");

        if (paramDistribute.getTotalValue() == null)
            throw new BusinessException("뿌리기 금액을 입력해주세요.");

        if (paramDistribute.getTotalValue() <= 0)
            throw new BusinessException("0원 이상을 뿌릴 수 있습니다.");

        if (paramDistribute.getTotalValue() < paramDistribute.getSeqCount())
            throw new BusinessException("받을 인원보다 큰 값의 금액을 입력할 수 있습니다.");

        var userData = objectServiceUser.selectData(paramDistribute.getUserId());
        if (userData == null)
            throw new BusinessException("존재하지 않는 페이머니 계정으로 뿌리기를 할 수 없습니다.");

        if (!userData.isAvailableAddBalance(-paramDistribute.getTotalValue()))
            throw new BusinessException("잔액이 부족해 뿌리기를 할 수 없습니다.");

        if (userData.getIsBlock())
            throw new BusinessException("정지된 페이머니 계정으로 뿌리기를 할 수 없습니다.");

        if (userData.getIsDelete())
            throw new BusinessException("해지된 페이머니 계정으로 뿌리기를 할 수 없습니다.");

        var roomData = objectServiceRoom.selectData(paramDistribute.getRoomId());
        if (roomData == null)
            throw new BusinessException("존재하지는 않는 방에 뿌리기를 할 수 없습니다.");

        if (roomData.getIsDelete())
            throw new BusinessException("삭제된 방에 뿌리기를 할 수 없습니다.");

        var roomUserData = objectServiceRoomUser.selectData(paramDistribute.getRoomId(), paramDistribute.getUserId());
        if (roomUserData == null)
            throw new BusinessException("참가하지 않은 방에 뿌리기를 할 수 없습니다.");

        if (roomUserData.getIsDelete())
            throw new BusinessException("이미 나온 방에 뿌리기를 할 수 없습니다.");

        List<ObjectDataRoomUser> roomUserDataList = objectServiceRoomUser.selectDataListByRoomId(paramDistribute.getRoomId());
        if (roomUserDataList.size() < 2)
            throw new BusinessException("두 명 이상의 방에만 뿌리기를 할 수 있습니다.");
    }

    /**
     * 토큰 생성기
     * 뿌리기 요청건에 대한 고유 token을 발급
     * (token은 3자리 문자열로 구성)
     *
     * @return
     */
    public String createToken() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(Character.valueOf((char) RandomUtil.between(Character.MIN_VALUE, Character.MAX_VALUE)));
        }
        return sb.toString();
    }

    @Override
    public IObjectData createObjectInstanceDefault(IParamData param) {
        ParamDistribute paramDistribute = (ParamDistribute) param;
        var data = new ObjectDataDistribute();
        data.setRoomId(paramDistribute.getRoomId());
        data.setUserId(paramDistribute.getUserId());
        data.setTotalValue(paramDistribute.getTotalValue());
        data.setSeqCount(paramDistribute.getSeqCount());

        // 뿌리기 요청건에 대한 고유 token을 발급 (token은 3자리 문자열로 구성)
        data.setToken(createToken());
        return data;
    }

    /**
     * 머니 뿌리기 분배하기
     * 소수의 인원이 많은 금액을 차지하고,
     * 대부분의 인원이 적인 금액을 차지하여 소수가 큰 관심을 받을 수 있도록 재미요소를 두자
     *
     * @param n 분배해야할 개수
     * @param totalValue 총 금액
     * @return
     */
    public static List<Long> getDistributedValues(int n, long totalValue) {
        if (n <= 0)
            return new ArrayList<>();

        if (n > totalValue)
            return new ArrayList<>();

        if (n == 1) {
            List<Long> seqs = new ArrayList<>();
            seqs.add(totalValue);
            return seqs;
        }

        // 연산 줄이기용
        if (n == totalValue) {
            List<Long> seqs = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                seqs.add(1L);
            }
            return seqs;
        }

        // 지수함수 x 범위
        double minX = 1.0d;
        double maxX = 10.0d;
        double interval = (maxX - minX) / (n + 1);

        // 가중치 분배
        double weightSum = 0.0d;
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double weight = Math.pow(2.0d, interval * (i + 1));
            weights.add(weight);
            weightSum += weight;
        }

        // 가중치에 따른 금액 분배
        long valueSum = 0;
        List<Long> seqs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double weight = weights.get(i);
            double valueDouble = (weight / weightSum) * totalValue;
            if (valueDouble < 1.0d) {
                valueDouble = 1.0d;
            }
            long value = (long) (valueDouble);
            seqs.add(value);
            valueSum += value;
        }

        // 분배 금액 총합과 뿌린 금액 오차는 최상위 분배자에게 처리
        long diff = totalValue - valueSum;
        int bestSeq = seqs.size() - 1;
        long origin = seqs.get(bestSeq);
        seqs.set(bestSeq, origin + diff);

        // 섞기~
        Collections.shuffle(seqs);

        return seqs;
    }

    @Override
    protected void afterCreateObject(IParamData param, IObjectData object) throws BusinessException {
        ParamDistribute paramDistribute = (ParamDistribute) param;
        ObjectDataDistribute objectDataDistribute = (ObjectDataDistribute) object;

        // 잔액 출금
        var userData = objectServiceUser.selectDataWithLock(objectDataDistribute.getUserId());
        userData.addBalance(-paramDistribute.getTotalValue());
        objectServiceUser.updateObjectDefault(userData);

        // 뿌릴 금액을 인원수에 맞게 분배하여 저장
        var seqs = getDistributedValues(objectDataDistribute.getSeqCount(), objectDataDistribute.getTotalValue());
        for (int i = 0; i < seqs.size(); i++) {
            ParamDistributeSeq paramDistributeSeq = new ParamDistributeSeq();
            paramDistributeSeq.setRoomId(objectDataDistribute.getRoomId());
            paramDistributeSeq.setUserId(objectDataDistribute.getUserId());
            paramDistributeSeq.setToken(objectDataDistribute.getToken());
            paramDistributeSeq.setSeq(i);
            paramDistributeSeq.setValue(seqs.get(i));
            objectServiceDistributeSeq.createObjectDefault(paramDistributeSeq);
        }
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataDistribute beforeObjectDataDistribute = (ObjectDataDistribute) before;
        if (beforeObjectDataDistribute.getIsEnd())
            throw new BusinessException("이미 종료된 뿌리기 입니다.");
        if (DateUtil.getCurrentDate().after(DateUtil.getDateAfterMinutes(beforeObjectDataDistribute.getCreateAt(), 10)))
            throw new BusinessException("10 분이 지나 이미 종료된 뿌리기 입니다.");

        ObjectDataDistribute objectDataDistribute = (ObjectDataDistribute) object;
        if (DateUtil.getCurrentDate().after(DateUtil.getDateAfterMinutes(objectDataDistribute.getCreateAt(), 10)))
            throw new BusinessException("10 분이 지나 이미 종료된 뿌리기 입니다.");
    }

    // 여러 인스턴스가 같이 돈다. 밀릴 수 있음.
    @Scheduled(fixedDelay = 500)
    public void checkDistributeEndSchedule() throws BusinessException {
        if (BusinessConfig.isTest())
            return;
        LogUtil.runWithStopWatch("[뿌리기 정산 스케쥴]", () -> {
            objectServiceDistribute.checkDistributeEnd();
        });
    }

    /**
     * 10분
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void checkDistributeEnd() {
        Date beforeTenMinuts = DateUtil.getDateAfterMinutes(-10);
        var distributeToEnd = objectDataDistributeMapper.selectObjectDataDistributeToEnd(beforeTenMinuts);
        if (distributeToEnd == null)
            return;

        distributeToEnd.setIsEnd(true);
        distributeToEnd.setEndAt(DateUtil.getCurrentDate());
        objectServiceDistribute.updateData(distributeToEnd);

        var userLocked = objectServiceUser.selectDataWithLock(distributeToEnd.getUserId());
        if (userLocked == null)
            // 정산 취소..
            return;

        // 돈 돌려주기
        userLocked.addBalance(distributeToEnd.getTotalValue());
        objectServiceUser.updateData(userLocked);
    }

    /**
     * 새 트랜잭션으로 뿌리기 종료 처리하기
     * @param distribute
     * @throws BusinessException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateDistributeEndWithNewTransaction(ObjectDataDistribute distribute) throws BusinessException {
        distribute.setEndAt(DateUtil.getCurrentDate());
        distribute.setIsEnd(true);
        objectServiceDistribute.updateObjectDefault(distribute);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDistributeEnd(ObjectDataDistribute distribute) throws BusinessException {
        distribute.setEndAt(DateUtil.getCurrentDate());
        distribute.setIsEnd(true);
        objectServiceDistribute.updateObjectDefault(distribute);
    }
}
