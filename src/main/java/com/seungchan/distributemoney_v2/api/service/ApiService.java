package com.seungchan.distributemoney_v2.api.service;

import com.seungchan.distributemoney_v2.api.data.*;
import com.seungchan.distributemoney_v2.common.BaseBean;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.distribute.data.ObjectDataDistribute;
import com.seungchan.distributemoney_v2.distribute.param.ParamDistribute;
import com.seungchan.distributemoney_v2.distribute.service.ObjectServiceDistribute;
import com.seungchan.distributemoney_v2.distributeSeq.service.ObjectServiceDistributeSeq;
import com.seungchan.distributemoney_v2.reserveReceive.data.ObjectDataReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.param.ParamReserveReceive;
import com.seungchan.distributemoney_v2.reserveReceive.service.ObjectServiceReserveReceive;
import com.seungchan.distributemoney_v2.room.param.ParamRoom;
import com.seungchan.distributemoney_v2.room.service.ObjectServiceRoom;
import com.seungchan.distributemoney_v2.roomUser.param.ParamRoomUser;
import com.seungchan.distributemoney_v2.roomUser.service.ObjectServiceRoomUser;
import com.seungchan.distributemoney_v2.user.data.ObjectDataUser;
import com.seungchan.distributemoney_v2.user.param.ParamUser;
import com.seungchan.distributemoney_v2.user.service.ObjectServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 머니 뿌리기 End Point 서비스
 */
@Service
public class ApiService extends BaseBean {

    @Autowired
    private ObjectServiceUser objectServiceUser;
    @Autowired
    private ObjectServiceRoom objectServiceRoom;
    @Autowired
    private ObjectServiceRoomUser objectServiceRoomUser;
    @Autowired
    private ObjectServiceDistribute objectServiceDistribute;
    @Autowired
    private ObjectServiceDistributeSeq objectServiceDistributeSeq;
    @Autowired
    private ObjectServiceReserveReceive objectServiceReserveReceive;

    /**
     * 테스트 계정 세팅
     * @param userId 테스트 계정
     * @param roomId 테스트 계정이 참여한 방
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void initUser(String userId, String roomId) throws BusinessException {
        var userLocked = objectServiceUser.selectDataWithLock(userId);
        if (userLocked == null) {
            // 카카오페이 계정 생성, 카카오뱅크 계좌 발급, 카카오뱅크 계좌 등록
            userLocked = (ObjectDataUser) objectServiceUser.createObjectDefault(ParamUser.newParamUser()
                    .userId(userId)
                    .userName("TEST_USER_NAME_" + userId)
                    .balance(100000L) // 10만원 지급~
                    .build());
        }

        // 테스트용 방 생성
        var roomLocked = objectServiceRoom.selectDataWithLock(roomId);
        if (roomLocked == null) {
            objectServiceRoom.createObjectDefault(ParamRoom.newParamRoom()
                    .roomId(roomId)
                    .roomName("TEST_ROOM_NAME_" + roomId)
                    .maxUserCount(1500) // 1500명 단톡방
                    .build());
            // 머니 뿌리기 테스트를 위해 테스트용 방에는 5명의 친구가 자동 참가한다
            for (int i = 0; i < 5; i++) {
                ObjectDataUser friend = (ObjectDataUser) objectServiceUser.createObjectDefault(ParamUser.newParamUser()
                        .userId(UUID.randomUUID().toString())
                        .balance(100000L)
                        .userName("TEST_FRIEND")
                        .build());
                objectServiceRoomUser.createObjectDefault(ParamRoomUser.newParamRoomUser()
                        .roomId(roomId)
                        .userId(friend.getUserId())
                        .userName(friend.getUserName())
                        .build());
            }
        }

        // 테스트용 방에 본인 참가
        var roomUserLocked = objectServiceRoomUser.selectDataWithLock(roomId, userId);
        if (roomUserLocked == null) {
            objectServiceRoomUser.createObjectDefault(ParamRoomUser.newParamRoomUser()
                    .roomId(roomId)
                    .userId(userId)
                    .userName(userLocked.getUserName())
                    .build());
        }

    }

    /**
     * 머니 뿌리기
     * @param userId 뿌릴 머니 계정
     * @param roomId 뿌릴 방
     * @param totalValue 뿌릴 금액
     * @param receiveUserCount 수령 가능한 유저 수
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseDistribute distribute(String userId, String roomId, Long totalValue, Integer receiveUserCount) throws BusinessException {
        ObjectDataDistribute objectData = (ObjectDataDistribute) objectServiceDistribute.createObjectDefault(ParamDistribute.newParamDistribute()
                .roomId(roomId)
                .userId(userId)
                .seqCount(receiveUserCount)
                .totalValue(totalValue)
                .build());
        var response = new ResponseDistribute();
        response.setToken(objectData.getToken());
        return response;
    }

    /**
     * 머니 뿌리기 상태 조회
     * @param userId
     * @param roomId
     * @param token
     * @return
     */
    public ResponseDistributeStatus distributeStatus(String userId, String roomId, String token) throws BusinessException {

        var distributeData = objectServiceDistribute.selectData(roomId, token);
        if (distributeData == null)
            throw new BusinessException("존재하지 않는 뿌리기를 조회할 수 없습니다.");

        if (!distributeData.getUserId().equals(userId))
            throw new BusinessException("뿌리기 본인만 상세 정보를 조회할 수 있습니다.");

        if (DateUtil.getCurrentDate().after(DateUtil.getDateAfterDays(distributeData.getCreateAt(), 7)))
            throw new BusinessException("7일전 뿌리기는 조회할 수 없습니다.");

        var response = new ResponseDistributeStatus();
        response.setDistributeAt(distributeData.getCreateAt());
        response.setTotalValue(distributeData.getTotalValue());

        // 수령한 금액 총 합
        long receivedSum = 0;

        // 수령자 정보
        var receivedList = new ArrayList<ResponseDistributeReceived>();
        var distributeSeqList = objectServiceDistributeSeq.selectDataAllSeq(roomId, token);
        for (var seq : distributeSeqList) {
            if (seq.getIsReceive()) {
                var received = new ResponseDistributeReceived();
                received.setUserId(seq.getReceiveUserId());

                // FIXME : join 해서 가져오기
                var userData = objectServiceUser.selectData(seq.getReceiveUserId());
                received.setUserName(userData.getUserName());
                received.setReceiveValue(seq.getValue());
                receivedList.add(received);

                receivedSum += seq.getValue();
            }
        }
        response.setReceiveValueSum(receivedSum);
        response.setReceivedList(receivedList);
        return response;
    }

    /**
     * 뿌려진 머니 수령 요청 및 즉시 응답 받기
     * @param userId 수령 예약자 계정
     * @param roomId 뿌려진 방
     * @param token 뿌려진 머니 토큰
     * @return
     * @throws BusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseReceive receive(String userId, String roomId, String token) throws BusinessException {
        // 뿌리기에 먼저 lock 을 걸자
        var distributeLocked = objectServiceDistribute.selectDataWithLock(roomId, token);
        if (distributeLocked == null)
            throw new BusinessException("뿌린 머니를 찾을 수 없습니다.");

        if (distributeLocked.getIsEnd())
            throw new BusinessException("이미 종료된 뿌리기 입니다.");

        int receivableCount = objectServiceDistributeSeq.selectCountToReceive(roomId, token);
        if (receivableCount == 0) {
            throw new BusinessException("더 이상 수령할 분배 건이 존재하지 않습니다.");
        }

        var locked = objectServiceDistributeSeq.selectDataToReceiveWithLock(roomId, token);
        if (locked == null) {
            // 수령한 금액 없음
            throw new BusinessException("더 이상 수령할 수 없습니다.");
        }

        // 수령 처리
        locked.setReceiveUserId(userId);
        locked.setIsReceive(true);
        locked.setReceiveAt(DateUtil.getCurrentDate());
        objectServiceDistributeSeq.updateObjectDefault(locked);

        if (receivableCount == 1) {
            distributeLocked.setIsEnd(true);
            distributeLocked.setEndAt(DateUtil.getCurrentDate());
            objectServiceDistribute.updateData(distributeLocked);
        }

        var userLocked = objectServiceUser.selectDataWithLock(userId);
        if (!userLocked.isAvailableAddBalance(locked.getValue()))
            throw new BusinessException("잔액이 수령할 수 있는 상태가 아닙니다.");
        userLocked.addBalance(locked.getValue());
        objectServiceUser.updateObjectDefault(userLocked);

        var response = new ResponseReceive();
        response.setValue(locked.getValue());
        return response;
    }

    /**
     * 뿌려진 머니 수령 예약 요청
     * @param userId 수령 예약자 계정
     * @param roomId 뿌려진 방
     * @param token 뿌려진 머니 토큰
     * @return
     * @throws BusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseReserveReceive reserveReceive(String userId, String roomId, String token) throws BusinessException {
        // 유효한 토큰인지 체크
        var distribute = objectServiceDistribute.selectData(roomId, token);
        if (distribute == null)
            throw new BusinessException("존재하지 않는 머니에 수령 요청 할 수 없습니다.");

        if (distribute.getIsEnd())
            throw new BusinessException("이미 종료된 머니에 수령 요청 할 수 없습니다.");

        ObjectDataReserveReceive reserveReceive = (ObjectDataReserveReceive) objectServiceReserveReceive.createObjectDefault(ParamReserveReceive.newParamReserveReceive()
                .roomId(roomId)
                .userId(userId)
                .token(token)
                .build());

        return new ResponseReserveReceive();
    }

    /**
     * (단순 조회, NO TRANSACTION)
     *
     * 수령 예약 요청 결과 조회 (polling 방식)
     * FIXME : 실제로는 소켓으로 해야함
     * @param userId
     * @param roomId
     * @param token
     * @return
     */
    public ResponseCheckReceive checkReceive(String userId, String roomId, String token) {

        var response = new ResponseCheckReceive();
        response.setValue(0L);
        response.setSuccess(false);

        // 0보다 크면 기다려야함
        int receivableCount = 0;
        var seqList = objectServiceDistributeSeq.selectDataAllSeq(roomId, token); // 절대 lock 걸지말기
        for (var seq : seqList) {

            // 하나라도 내가 받았다면 성공~
            if (seq.getIsReceive() && seq.getReceiveUserId().equals(userId)) {
                response.setSuccess(true);
                response.setValue(seq.getValue());
                return response;
            }

            if (!seq.getIsReceive())
                receivableCount++;
        }

        if (receivableCount > 0)
            response.setEnd(false); // 다시 polling 해야함
        else
            response.setEnd(true); // 더 이상 polling 할 필요 없음

        return response;
    }
}
