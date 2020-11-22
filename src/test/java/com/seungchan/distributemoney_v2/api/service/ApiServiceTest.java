package com.seungchan.distributemoney_v2.api.service;

import com.seungchan.distributemoney_v2.TestBase;
import com.seungchan.distributemoney_v2.account.service.ObjectServiceAccount;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.distribute.service.ObjectServiceDistribute;
import com.seungchan.distributemoney_v2.distributeSeq.service.ObjectServiceDistributeSeq;
import com.seungchan.distributemoney_v2.room.service.ObjectServiceRoom;
import com.seungchan.distributemoney_v2.roomUser.service.ObjectServiceRoomUser;
import com.seungchan.distributemoney_v2.user.service.ObjectServiceUser;
import com.seungchan.distributemoney_v2.userAccount.service.ObjectServiceUserAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiServiceTest extends TestBase {

    @Autowired
    private ApiService apiService;
    @Autowired
    private ObjectServiceUser objectServiceUser;
    @Autowired
    private ObjectServiceUserAccount objectServiceUserAccount;
    @Autowired
    private ObjectServiceAccount objectServiceAccount;
    @Autowired
    private ObjectServiceRoom objectServiceRoom;
    @Autowired
    private ObjectServiceRoomUser objectServiceRoomUser;
    @Autowired
    private ObjectServiceDistribute objectServiceDistribute;
    @Autowired
    private ObjectServiceDistributeSeq objectServiceDistributeSeq;

    /**
     * 테스트 환경 세팅 검증
     * @throws BusinessException
     */
    @Test
    public void initUser() throws BusinessException {

        apiService.initUser(defaultUserId, defaultRoomId);

        // 유저 체크
        var userData = objectServiceUser.selectData(defaultUserId);
        Assertions.assertNotNull(userData);

        // 유저 뱅킹 연동 정보 체크
        var userAccountData = objectServiceUserAccount.selectDataByUserId(defaultUserId);
        Assertions.assertEquals(1, userAccountData.size());

        // 유저 연동 은행 정보 체크
        var accountData = objectServiceAccount.selectData(userAccountData.get(0).getBankId(), userAccountData.get(0).getBankAccountNumber());
        Assertions.assertNotNull(accountData);

        // 방 체크
        var roomData = objectServiceRoom.selectData(defaultRoomId);
        Assertions.assertNotNull(roomData);

        // 유저 방 참가 체크
        var roomUserData = objectServiceRoomUser.selectData(defaultRoomId, defaultUserId);
        Assertions.assertNotNull(roomUserData);

    }

    /**
     * 뿌리기 검증
     */
    @Test
    public void distribute() throws BusinessException {
        apiService.initUser(defaultUserId, defaultRoomId);
        var userData = objectServiceUser.selectData(defaultUserId);
        long balanceBefore = userData.getBalance();

        // 3명에게 1000원 뿌리기
        long totalValue = 1000L;
        var response = apiService.distribute(defaultUserId, defaultRoomId, totalValue, 3);

        // 뿌린후 잔액 체크
        userData = objectServiceUser.selectData(defaultUserId);
        Assertions.assertEquals(balanceBefore - totalValue, userData.getBalance());

        // 분배 체크
        var distributeData = objectServiceDistribute.selectData(defaultRoomId, response.getToken());
        long valueSum = 0;
        var seqList = objectServiceDistributeSeq.selectDataAllSeq(defaultRoomId, distributeData.getToken());
        for (var seq : seqList) {
            Assertions.assertEquals(defaultUserId, seq.getUserId());
            logger.info("value:{}", seq.getValue());
            valueSum += seq.getValue();
        }
        assertEquals(distributeData.getTotalValue(), valueSum);
    }

    /**
     * 뿌리기 상세 상태 조회 검증
     */
    @Test
    public void testDistributeStatus() throws BusinessException {
        // 3개 뿌리기
        apiService.initUser(defaultUserId, defaultRoomId);
        var response = apiService.distribute(defaultUserId, defaultRoomId, 1000L, 3);

        // 조회
        var distributeStatus = apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        assertEquals(1000L, distributeStatus.getTotalValue());
        assertEquals(0, distributeStatus.getReceivedList().size());
        assertEquals(0, distributeStatus.getReceiveValueSum());
        var distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());
        assertEquals(distribute.getCreateAt(), distributeStatus.getDistributeAt());

        // 1개 받기
        String receiveUserId = defaultUserId + 1;
        apiService.initUser(receiveUserId, defaultRoomId);
        var responseReceive = apiService.receive(receiveUserId, defaultRoomId, response.getToken());

        // 조회
        distributeStatus = apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        assertEquals(1000L, distributeStatus.getTotalValue());
        assertEquals(1, distributeStatus.getReceivedList().size());
        assertEquals(responseReceive.getValue(), distributeStatus.getReceiveValueSum());
        distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());
        assertEquals(distribute.getCreateAt(), distributeStatus.getDistributeAt());
        var receiverUser = objectServiceUser.selectData(receiveUserId);
        assertEquals(receiveUserId, distributeStatus.getReceivedList().get(0).getUserId());
        assertEquals(receiverUser.getUserName(), distributeStatus.getReceivedList().get(0).getUserName());
        assertEquals(responseReceive.getValue(), distributeStatus.getReceivedList().get(0).getReceiveValue());

        // 2개 받기
        String secondReceiveUserId = defaultUserId + 2;
        apiService.initUser(secondReceiveUserId, defaultRoomId);
        var secondResponseReceive = apiService.receive(secondReceiveUserId, defaultRoomId, response.getToken());

        // 조회
        distributeStatus = apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        assertEquals(1000L, distributeStatus.getTotalValue());
        assertEquals(2, distributeStatus.getReceivedList().size());
        assertEquals(responseReceive.getValue() + secondResponseReceive.getValue(), distributeStatus.getReceiveValueSum());
        distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());
        assertEquals(distribute.getCreateAt(), distributeStatus.getDistributeAt());
        receiverUser = objectServiceUser.selectData(receiveUserId);
        assertEquals(receiveUserId, distributeStatus.getReceivedList().get(0).getUserId());
        assertEquals(receiverUser.getUserName(), distributeStatus.getReceivedList().get(0).getUserName());
        assertEquals(responseReceive.getValue(), distributeStatus.getReceivedList().get(0).getReceiveValue());
        var secondReceiverUser = objectServiceUser.selectData(secondReceiveUserId);
        assertEquals(secondReceiveUserId, distributeStatus.getReceivedList().get(1).getUserId());
        assertEquals(secondReceiverUser.getUserName(), distributeStatus.getReceivedList().get(1).getUserName());
        assertEquals(secondResponseReceive.getValue(), distributeStatus.getReceivedList().get(1).getReceiveValue());

        // 3개 받기
        String thirdReceiveUserId = defaultUserId + 3;
        apiService.initUser(thirdReceiveUserId, defaultRoomId);
        var thirdResponseReceive = apiService.receive(thirdReceiveUserId, defaultRoomId, response.getToken());

        // 조회
        distributeStatus = apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        assertEquals(1000L, distributeStatus.getTotalValue());
        assertEquals(3, distributeStatus.getReceivedList().size());
        distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());
        assertEquals(distribute.getCreateAt(), distributeStatus.getDistributeAt());
        assertEquals(responseReceive.getValue() + secondResponseReceive.getValue() + thirdResponseReceive.getValue(), distributeStatus.getReceiveValueSum());
        receiverUser = objectServiceUser.selectData(receiveUserId);
        assertEquals(receiveUserId, distributeStatus.getReceivedList().get(0).getUserId());
        assertEquals(receiverUser.getUserName(), distributeStatus.getReceivedList().get(0).getUserName());
        assertEquals(responseReceive.getValue(), distributeStatus.getReceivedList().get(0).getReceiveValue());
        secondReceiverUser = objectServiceUser.selectData(secondReceiveUserId);
        assertEquals(secondReceiveUserId, distributeStatus.getReceivedList().get(1).getUserId());
        assertEquals(secondReceiverUser.getUserName(), distributeStatus.getReceivedList().get(1).getUserName());
        assertEquals(secondResponseReceive.getValue(), distributeStatus.getReceivedList().get(1).getReceiveValue());
        var thirdReceiverUser = objectServiceUser.selectData(thirdReceiveUserId);
        assertEquals(thirdReceiveUserId, distributeStatus.getReceivedList().get(2).getUserId());
        assertEquals(thirdReceiverUser.getUserName(), distributeStatus.getReceivedList().get(2).getUserName());
        assertEquals(thirdResponseReceive.getValue(), distributeStatus.getReceivedList().get(2).getReceiveValue());
    }

    /**
     * 뿌리기 후 7일뒤 조회 못하는지 검증
     * @throws BusinessException
     */
    @Test
    public void testDistributeStatusAfter7Days() throws BusinessException {
        // 3개 뿌리기
        apiService.initUser(defaultUserId, defaultRoomId);
        var response = apiService.distribute(defaultUserId, defaultRoomId, 1000L, 3);
        var distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());

        // 조회 ok
        var distributeStatus = apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        Assertions.assertNotNull(distributeStatus);

        // 7일뒤
        DateUtil.setTestDate(DateUtil.getDateAfterDays(distribute.getCreateAt(), 7));
        DateUtil.setTestDate(DateUtil.getDateAfterSeconds(DateUtil.getCurrentDate(), 1));

        // 조회 실패
        Assertions.assertThrows(BusinessException.class, () -> {
            apiService.distributeStatus(defaultUserId, defaultRoomId, response.getToken());
        });
    }

    /**
     * 선착순 수령 테스트
     * @throws BusinessException
     */
    @Test
    public void testReceive() throws BusinessException {
        // 3개 뿌리기
        apiService.initUser(defaultUserId, defaultRoomId);
        var response = apiService.distribute(defaultUserId, defaultRoomId, 1000L, 3);

        // 1개 받기
        String receiveUserId = defaultUserId + 1;
        apiService.initUser(receiveUserId, defaultRoomId);
        var receiveUserData = objectServiceUser.selectData(receiveUserId);
        long beforeBalance = receiveUserData.getBalance();
        var responseReceive = apiService.receive(receiveUserId, defaultRoomId, response.getToken());

        // 받기 후 잔액 체크
        receiveUserData = objectServiceUser.selectData(receiveUserId);
        Assertions.assertEquals(beforeBalance + responseReceive.getValue(), receiveUserData.getBalance());
    }

    /**
     * 선착순 수령시 받을 수 있는 인원보다 많은 인원이 수령 요청 테스트
     */
    @Test
    public void testReceiveMoreThanAvailable() throws BusinessException {
        // 3개 뿌리기
        int num = 3;
        apiService.initUser(defaultUserId, defaultRoomId);
        var response = apiService.distribute(defaultUserId, defaultRoomId, 1000L, 4);

        int testCount = num + 2;
        for (int i = 0; i < testCount; i++) {
            String receiveUserId = defaultUserId + i;
            apiService.initUser(receiveUserId, defaultRoomId);
            var receiveUserData = objectServiceUser.selectData(receiveUserId);
            long beforeBalance = receiveUserData.getBalance();

            logger.info("받기 테스트 {}/{}", i+1, testCount);
            if (i > num) {
                logger.info("선착순 이미 꽉참");
                // 선착순을 벗어나면 익셉션 발생
                Assertions.assertThrows(BusinessException.class, () -> {
                    apiService.receive(receiveUserId, defaultRoomId, response.getToken());
                });
                receiveUserData = objectServiceUser.selectData(receiveUserId);
                Assertions.assertEquals(beforeBalance, receiveUserData.getBalance());
            } else {
                // 수령 성공
                var receiveResponse = apiService.receive(receiveUserId, defaultRoomId, response.getToken());
                receiveUserData = objectServiceUser.selectData(receiveUserId);
                Assertions.assertEquals(beforeBalance + receiveResponse.getValue(), receiveUserData.getBalance());
            }
        }
    }

    /**
     * 뿌리기는 10분 안에 받기가 가능한지 테스트
     * @throws BusinessException
     */
    @Test
    public void testReceiveAfter10Minutes() throws BusinessException {
        // 뿌리기
        apiService.initUser(defaultUserId, defaultRoomId);
        var response = apiService.distribute(defaultUserId, defaultRoomId, 1000L, 1);
        var distribute = objectServiceDistribute.selectData(defaultRoomId, response.getToken());

        // 10분 뒤
        DateUtil.setTestDate(DateUtil.getDateAfterMinutes(distribute.getCreateAt(), 10));
        DateUtil.setTestDate(DateUtil.getDateAfterSeconds(DateUtil.getCurrentDate(), 1));

        // 받기
        String receiveUserId = defaultUserId + 1;
        apiService.initUser(receiveUserId, defaultRoomId);
        Assertions.assertThrows(BusinessException.class, () -> {
            apiService.receive(receiveUserId, defaultRoomId, response.getToken());
        });
    }

    @Test
    void reserveReceive() {
    }

    @Test
    void checkReceive() {
    }
}