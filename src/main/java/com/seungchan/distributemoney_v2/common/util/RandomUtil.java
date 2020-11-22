package com.seungchan.distributemoney_v2.common.util;

import java.security.SecureRandom;

public class RandomUtil {
    private final static SecureRandom random = new SecureRandom();

    /**
     * 0 부터 n 까지 범위내 랜덤값 뽑기
     * @param n
     * @return
     */
    public static int nextInt(int n) {
        return random.nextInt(n);
    }
    public static long nextLong() {
        return random.nextLong();
    }
    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * start 부터 end 까지 범위내 랜덤값 뽑기
     * @param start
     * @param end
     * @return
     */
    public static int between(int start, int end) {
        if (start == end)
            return start;
        return start + nextInt(1 + end - start);
    }
    public static long between(long start, long end) {
        if (start == end)
            return start;
        long mod = 1L + end - start;
        return Math.abs(nextLong() % mod) + start;
    }
}
