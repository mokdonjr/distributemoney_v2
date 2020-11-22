package com.seungchan.distributemoney_v2.user.data;

import com.seungchan.distributemoney_v2.TestBaseWithoutContext;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import com.seungchan.distributemoney_v2.user.data.key.ObjectKeyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ObjectDataUserTest extends TestBaseWithoutContext {

    private ObjectDataUser getUserDataDefault() {
        var user = new ObjectDataUser();
        user.setObjectKeyUser(new ObjectKeyUser("-1"));
        user.setBalance(0L);
        user.setCreateAt(DateUtil.getCurrentDate());
        return user;
    }

    /**
     * 입금 값 검증
     */
    @Test
    public void addBalancePositive() {

        // 입금
        long originBalance = 10L;
        long addBalance = 3L;
        var objectDataUser = getUserDataDefault();
        objectDataUser.setBalance(originBalance);
        objectDataUser.addBalance(addBalance);

        Assertions.assertAll(
                () -> Assertions.assertEquals(originBalance + addBalance, objectDataUser.getBalance()),
                () -> Assertions.assertNotNull(objectDataUser.getLastUpdateAt()),
                () -> Assertions.assertNotNull(objectDataUser.getModBalance())
        );
    }

    /**
     * 입금 누적 검증
     */
    @Test
    public void addBalanceCumulative() {
        // num 회 누적 입금
        int num = 5;
        long originBalance = 10L;
        long addBalance = 3L;

        var objectDataUser = getUserDataDefault();
        objectDataUser.setBalance(originBalance);
        for (int i = 0; i < num; i++) {
            objectDataUser.addBalance(addBalance);
        }

        Assertions.assertEquals(originBalance + addBalance * num, objectDataUser.getBalance());
    }

    /**
     * 출금 값 검증
     */
    @Test
    public void addBalanceNegative() {

        // 출금
        long originBalance = 10L;
        long addBalance = -3L;
        var objectDataUser = getUserDataDefault();
        objectDataUser.setBalance(originBalance);
        objectDataUser.addBalance(addBalance);

        Assertions.assertAll(
                () -> Assertions.assertEquals(originBalance + addBalance, objectDataUser.getBalance()),
                () -> Assertions.assertNotNull(objectDataUser.getLastUpdateAt()),
                () -> Assertions.assertNotNull(objectDataUser.getModBalance())
        );
    }

    private static Stream<Arguments> parametersForTestIsAvailableAddBalance() {
        return Stream.of(
                Arguments.of(10, 1, true),
                Arguments.of(10, -1, true),
                Arguments.of(10, -10, true),
                Arguments.of(10, -11, false)
        );
    }

    /**
     * 입/출금 가능 여부 체크
     */
    @ParameterizedTest
    @MethodSource(value = "parametersForTestIsAvailableAddBalance")
    public void testIsAvailableAddBalance(long originBalance, long addBalance, boolean expected) {
        var objectDataUser = getUserDataDefault();
        objectDataUser.setBalance(originBalance);
        Assertions.assertEquals(expected, objectDataUser.isAvailableAddBalance(addBalance));
    }
}