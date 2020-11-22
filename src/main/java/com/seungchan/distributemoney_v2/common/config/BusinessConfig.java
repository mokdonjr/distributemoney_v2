package com.seungchan.distributemoney_v2.common.config;

public class BusinessConfig {

    private static boolean isTest;

    public static boolean isTest() {
        return isTest;
    }

    public static void setIsTest(boolean isTest) {
        BusinessConfig.isTest = isTest;
    }
}
