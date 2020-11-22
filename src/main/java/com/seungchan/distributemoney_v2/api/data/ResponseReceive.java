package com.seungchan.distributemoney_v2.api.data;

public class ResponseReceive extends AbsResponse {
    // 수령 성공한 금액
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
