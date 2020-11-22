package com.seungchan.distributemoney_v2.api.data;

public class ResponseDistributeReceived extends AbsResponse {

    // 사용자 계정
    private String userId;
    // 사용자 이름
    private String userName;
    // 수령한 금액
    private Long receiveValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getReceiveValue() {
        return receiveValue;
    }

    public void setReceiveValue(Long receiveValue) {
        this.receiveValue = receiveValue;
    }
}
