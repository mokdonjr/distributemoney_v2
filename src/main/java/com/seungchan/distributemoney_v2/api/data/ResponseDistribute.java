package com.seungchan.distributemoney_v2.api.data;

/**
 * 머니 뿌리기 응답
 */
public class ResponseDistribute extends AbsResponse {
    // 뿌리기로 발급된 토큰 값 (3자리 문자열)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
