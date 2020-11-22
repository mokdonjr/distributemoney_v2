package com.seungchan.distributemoney_v2.common.util;

import java.util.Date;

public class NullUtil {

    public static byte getOrDefault(Byte v, byte defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static byte getOrDefault(Byte v) {
        return getOrDefault(v, (byte) 0);
    }

    public static short getOrDefault(Short v, short defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static short getOrDefault(Short v) {
        return getOrDefault(v, (short) 0);
    }

    public static int getOrDefault(Integer v, int defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static int getOrDefault(Integer v) {
        return getOrDefault(v, 0);
    }

    public static long getOrDefault(Long v, long defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static long getOrDefault(Long v) {
        return getOrDefault(v, 0L);
    }

    public static float getOrDefault(Float v, float defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static float getOrDefault(Float v) {
        return getOrDefault(v, 0.0f);
    }

    public static double getOrDefault(Double v, double defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static double getOrDefault(Double v) {
        return getOrDefault(v, 0.0d);
    }

    public static boolean getOrDefault(Boolean v, boolean defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static boolean getOrDefault(Boolean v) {
        return getOrDefault(v, Boolean.FALSE);
    }

    public static char getOrDefault(Character v, char defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static char getOrDefault(Character v) {
        return getOrDefault(v, Character.MIN_VALUE);
    }

    public static String getOrDefault(String v, String defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static String getOrDefault(String v) {
        return getOrDefault(v, "");
    }

    public static Date getOrDefault(Date v, Date defaultValue) {
        return v == null ? defaultValue : v;
    }
    public static Date getOrDefault(Date v) {
        return getOrDefault(v, new Date(0));
    }
}
