package com.seungchan.distributemoney_v2.api.controller;

import com.seungchan.distributemoney_v2.api.data.*;
import com.seungchan.distributemoney_v2.api.service.ApiService;
import com.seungchan.distributemoney_v2.common.BaseBean;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 머니 뿌리기 End Point
 */
@RestController
@Api(tags = "머니뿌리기")
public class ApiController extends BaseBean {

    @Autowired
    private ApiService apiService;

    /**
     * 테스트 환경 세팅
     * @param userId
     * @param roomId
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/user", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "테스트 세팅 (페이계정/계좌/채팅방/테스트유저 등 세팅)")
    public ResponseEntity<?> initUser(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId) throws BusinessException {
        LogUtil.runWithStopWatch("[/user]", () -> {
            apiService.initUser(userId, roomId);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 머니 뿌리기
     * @param userId
     * @param roomId
     * @param totalValue
     * @param receiveUserCount
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/distribute", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "뿌리기 API")
    public ResponseEntity<ResponseDistribute> distribute(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId
            , @RequestParam("totalValue") Long totalValue, @RequestParam("receiveUserCount") Integer receiveUserCount) throws BusinessException {

        var data = new ResponseDistribute[1];
        LogUtil.runWithStopWatch("[/distribute]", () -> {
            data[0] = apiService.distribute(userId, roomId, totalValue, receiveUserCount);
        });
        return new ResponseEntity<>(data[0], HttpStatus.OK);
    }

    /**
     * 뿌려진 머니 현재 상태 조회
     * @param userId
     * @param roomId
     * @param token
     * @return
     */
    @GetMapping(value = "/distribute/status", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "조회 API")
    public ResponseEntity<ResponseDistributeStatus> distributeStatus(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId
            , @RequestParam("token") String token) throws BusinessException {
        var data = new ResponseDistributeStatus[1];
        LogUtil.runWithStopWatch("[/distribute/status]", () -> {
            data[0] = apiService.distributeStatus(userId, roomId, token);
        });
        return new ResponseEntity<>(data[0], HttpStatus.OK);
    }

    /**
     * 뿌려진 머느 즉시 수령 및 즉시 응답 받기 (Naive)
     * @param userId
     * @param roomId
     * @param token
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/receive", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "받기 API")
    public ResponseEntity<ResponseReceive> receive(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId
            , @RequestParam("token") String token) throws BusinessException {
        var data = new ResponseReceive[1];
        LogUtil.runWithStopWatch("[/receive]", () -> {
            data[0] = apiService.receive(userId, roomId, token);
        });
        return new ResponseEntity<>(data[0], HttpStatus.OK);
    }

    /**
     * 뿌려진 머니 수령 예약 요청
     * @param userId
     * @param roomId
     * @param token
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/receive/reserve", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "조금 다른 방식의 받기 API (예약)")
    public ResponseEntity<ResponseReserveReceive> reserveReceive(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId
            , @RequestParam("token") String token) throws BusinessException {
        var data = new ResponseReserveReceive[1];
        LogUtil.runWithStopWatch("[/receive/reserve]", () -> {
            data[0] = apiService.reserveReceive(userId, roomId, token);
        });
        return new ResponseEntity<>(data[0], HttpStatus.OK);
    }

    /**
     * 수령 예약 결과 조회 (클라리언트 polling 방식)
     * FIXME : 실제로는 소켓으로 구현해야함
     * @param userId
     * @param roomId
     * @param token
     * @return
     */
    @GetMapping(value = "/receive/check", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "조금 다른 방식의 받기 API (예약 후 수령 되었는지 polling 으로 결과 조회. 소켓 구현해야함)")
    public ResponseEntity<ResponseCheckReceive> checkReceive(@RequestHeader("X-USER-ID") String userId, @RequestHeader("X-ROOM-ID") String roomId
            , @RequestParam("token") String token) throws BusinessException {
        var data = new ResponseCheckReceive[1];
        LogUtil.runWithStopWatch("[/receive/check]", () -> {
            data[0] = apiService.checkReceive(userId, roomId, token);
        });
        return new ResponseEntity<>(data[0], HttpStatus.OK);
    }
}
