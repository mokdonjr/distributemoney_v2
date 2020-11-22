package com.seungchan.distributemoney_v2.account.data;

import com.seungchan.distributemoney_v2.TestBaseWithoutContext;
import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import com.seungchan.distributemoney_v2.account.data.key.ObjectKeyAccount;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ObjectDataAccountTest extends TestBaseWithoutContext {

    private ObjectDataAccount getAccountDataDefault() {
        var account = new ObjectDataAccount();
        account.setObjectKeyAccount(new ObjectKeyAccount(BankType.KAKAO_BANK.getBankId(), "123-456-789"));
        account.setBalance(0L);
        account.setCreateAt(DateUtil.getCurrentDate());
        return account;
    }

    /**
     * 입금 값 검증
     */
    @Test
    public void addBalancePositive() {

        // 입금
        long originBalance = 10L;
        long addBalance = 3L;
        var objectDataAccount = getAccountDataDefault();
        objectDataAccount.setBalance(originBalance);
        objectDataAccount.addBalance(addBalance);

        Assertions.assertAll(
                () -> Assertions.assertEquals(originBalance + addBalance, objectDataAccount.getBalance()),
                () -> Assertions.assertNotNull(objectDataAccount.getLastUpdateAt()),
                () -> Assertions.assertNotNull(objectDataAccount.getModBalance())
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

        var objectDataAccount = getAccountDataDefault();
        objectDataAccount.setBalance(originBalance);
        for (int i = 0; i < num; i++) {
            objectDataAccount.addBalance(addBalance);
        }

        Assertions.assertEquals(originBalance + addBalance * num, objectDataAccount.getBalance());
    }

    /**
     * 출금 값 검증
     */
    @Test
    public void addBalanceNegative() {

        // 출금
        long originBalance = 10L;
        long addBalance = -3L;
        var objectDataAccount = getAccountDataDefault();
        objectDataAccount.setBalance(originBalance);
        objectDataAccount.addBalance(addBalance);

        Assertions.assertAll(
                () -> Assertions.assertEquals(originBalance + addBalance, objectDataAccount.getBalance()),
                () -> Assertions.assertNotNull(objectDataAccount.getLastUpdateAt()),
                () -> Assertions.assertNotNull(objectDataAccount.getModBalance())
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
        var objectDataAccount = getAccountDataDefault();
        objectDataAccount.setBalance(originBalance);
        Assertions.assertEquals(expected, objectDataAccount.isAvailableAddBalance(addBalance));
    }
}