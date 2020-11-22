package com.seungchan.distributemoney_v2.api.data;

import java.util.Date;
import java.util.List;

/**
 * 뿌리기건의 현재 상태
 */
public class ResponseDistributeStatus extends AbsResponse {

    // 뿌린 시각
    private Date distributeAt;
    // 뿌린 금액
    private Long totalValue;
    // 받기 완료된 금액
    private Long receiveValueSum;
    // 받기 완료된 정보
    private List<ResponseDistributeReceived> receivedList;

    public Date getDistributeAt() {
        return distributeAt;
    }

    public void setDistributeAt(Date distributeAt) {
        this.distributeAt = distributeAt;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public Long getReceiveValueSum() {
        return receiveValueSum;
    }

    public void setReceiveValueSum(Long receiveValueSum) {
        this.receiveValueSum = receiveValueSum;
    }

    public List<ResponseDistributeReceived> getReceivedList() {
        return receivedList;
    }

    public void setReceivedList(List<ResponseDistributeReceived> receivedList) {
        this.receivedList = receivedList;
    }
}
