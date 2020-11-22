package com.seungchan.distributemoney_v2.common.data;

import java.util.Date;

/**
 * 잔액 누적 가능한 데이터
 */
public interface ICumulativeData {

    /**
     * 잔액 조회
     * @return
     */
    Long getBalance();

    /**
     * 잔액 세팅
     */
    void setBalance(Long balance);

    /**
     * 입/출금
     * @param value 입금 금액 (+) / 출금 금액 (-)
     */
    void addBalance(Long value);

    /**
     * 입/출금 가능 여부
     * @param value 입금 금액 (+) / 출금 금액 (-)
     * @return
     */
    boolean isAvailableAddBalance(Long value);

    /**
     * 가장 최근 잔액 변경 시각
     * @return
     */
    Date getLastUpdateAt();

    /**
     * 가장 최근 입/출금액 조회
     * @return
     */
    Long getModBalance();
}
