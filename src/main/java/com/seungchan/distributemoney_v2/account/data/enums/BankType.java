package com.seungchan.distributemoney_v2.account.data.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 은행 타입
 */
public enum  BankType {
    KAKAO_BANK(1001),
    K_BANK(1002),
    NONGHYUP(1003),
    WOORI(1004),
    SHINHAN(1005),
    HANA(1006),
    ;

    // 은행 고유번호
    private int bankId;

    BankType(int bankId) {
        this.bankId = bankId;
    }

    public int getBankId() {
        return bankId;
    }

    private static Map<Integer, BankType> typeMap = new HashMap<>();
    static {
        for (BankType t : BankType.values()) {
            typeMap.put(t.bankId, t);
        }
    }

    public static BankType valueOf(int bankId) {
        return typeMap.get(bankId);
    }
}
