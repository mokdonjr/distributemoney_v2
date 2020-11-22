package com.seungchan.distributemoney_v2.api.data;

/**
 * 머니 수령 처리 완료 결과 응답
 */
public class ResponseCheckReceive extends AbsResponse {
    // 예약 처리 여부
    private boolean isEnd;
    // 수령 성공 여부
    private boolean isSuccess;
    // 수령 성공시 획득한 돈
    private long value;

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
