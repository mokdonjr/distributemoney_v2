package com.seungchan.distributemoney_v2.common.util;

import com.seungchan.distributemoney_v2.TestBaseWithoutContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateUtilTest extends TestBaseWithoutContext {

    private static final Date testDate = DateUtil.stringToDate("2020-01-01 00:00:00");

    /**
     * 현재 시간 조회 검증
     */
    @Test
    public void testGetCurrentDate() {
        long expected = new Date().getTime();
        logger.info("expected:{}", expected);

        long actual = DateUtil.getCurrentDate().getTime();
        logger.info("actual:{}", actual);

        long diff = actual - expected;
        logger.info("diff:{}", diff);
        Assertions.assertTrue(diff < 1000, "현재 시간 오류!");
    }

    /**
     * N 밀리초뒤 연산 검증
     */
    @Test
    public void testGetDateAfterMillis() {

        long expected = testDate.getTime() + 3;
        logger.info("expected:{}", expected);

//        long actual = DateUtil.stringToDate("2020-01-01 00:00:00.003", "yyyy-MM-dd HH:mm:ss.SSS").getTime();
        long actual = DateUtil.getDateAfterMillis(testDate, 3).getTime();
        logger.info("actual:{}", actual);

        Assertions.assertEquals(expected, actual, "3밀리초 뒤 시간 오류!");
    }

    /**
     * N 초뒤 연산 검증
     */
    @Test
    public void testGetDateAfterSeconds() {

        long expected = testDate.getTime() + 3000;
        logger.info("expected:{}", expected);

//        long actual = DateUtil.stringToDate("2020-01-01 00:00:03").getTime();
        long actual = DateUtil.getDateAfterSeconds(testDate, 3).getTime();
        logger.info("actual:{}", actual);

        Assertions.assertEquals(expected, actual, "3초 뒤 시간 오류!");
    }

    @Test
    public void testGetDateAfterMinutes() {

        long expected = testDate.getTime() + 180000;
        logger.info("expected:{}", expected);

//        long actual = DateUtil.stringToDate("2020-01-01 00:03:00").getTime();
        long actual = DateUtil.getDateAfterMinutes(testDate, 3).getTime();
        logger.info("actual:{}", actual);

        Assertions.assertEquals(expected, actual, "3분 뒤 시간 오류!");
    }

    @Test
    public void testGetDateAfterHours() {

        long expected = testDate.getTime() + 10800000;
        logger.info("expected:{}", expected);

//        long actual = DateUtil.stringToDate("2020-01-01 03:00:00").getTime();
        long actual = DateUtil.getDateAfterHours(testDate, 3).getTime();
        logger.info("actual:{}", actual);

        Assertions.assertEquals(expected, actual, "3시간 뒤 시간 오류!");
    }

    @Test
    public void testGetDateAfterDays() {

        long expected = testDate.getTime() + 259200000;
        logger.info("expected:{}", expected);

//        long actual = DateUtil.stringToDate("2020-01-04 00:00:00").getTime();
        long actual = DateUtil.getDateAfterDays(testDate, 3).getTime();
        logger.info("actual:{}", actual);

        Assertions.assertEquals(expected, actual, "3일 뒤 시간 오류!");
    }
}