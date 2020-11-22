package com.seungchan.distributemoney_v2.common.exception;

/**
 * 어플리케이션 비지니스로직 내 최상위 예외
 */
public class BusinessException extends Exception {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
